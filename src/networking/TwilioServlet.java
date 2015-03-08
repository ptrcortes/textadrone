package networking;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utility.ApprovedSenderList;
import utility.StatusPack;

import com.twilio.sdk.verbs.Message;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;

import control.CommandInterpreter;

/**
 * This servlet modifies the default javax servlet to allow communication with
 * Twilio systems. The servlet interprets POSTs and generates XMLs to send back
 * to Twilio.
 *
 * @author Peter Cortes
 * @author Twilio
 */
public class TwilioServlet extends HttpServlet
{
	private static final long serialVersionUID = 7206213494388714136L;
	private static final String password = "cherry";

	ApprovedSenderList senders;

	/**
	 * This contructor simply initializes the list of approved command senders
	 */
	public TwilioServlet()
	{
		senders = new ApprovedSenderList();

		// senders.add("+14804153358", "Tom");
		// senders.add("+14802598397", "Kyler");
		senders.add("+17143305057", "Peter");
	}

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String fromNumber = request.getParameter("From");
		String knownSender = senders.get(fromNumber);
		String messageContent = request.getParameter("Body");

		// if sender not recognized and trying to authenticate
		if (knownSender == null && messageContent.toLowerCase().contains("authenticate"))
		{
			System.out.print(fromNumber + " trying to authenticate...");

			String[] tokens = messageContent.split(" ");

			if (tokens.length == 3 && tokens[2].equals(password))
			{
				senders.add(fromNumber, tokens[1]);
				System.out.println("authenticated successfully with name " + tokens[1]);

				respondToSMS(response, "authentication request approved");
			}

			else
				System.out.println("authentication failed");
		}

		// echo the sender's message
		else if (knownSender == null)
			System.out.println("from " + fromNumber + ": " + messageContent);

		// if the sender is recognized, echo message and respond with text
		else
		{
			System.out.println("from " + knownSender + ": " + messageContent);

			StatusPack droneResponse = CommandInterpreter.interpret(messageContent);
			if (droneResponse.status)
				respondToSMS(response, "command sent successfully:\n" + droneResponse.message);

			else
				respondToSMS(response, "invalid command or drone communication failure");
		}
	}

	/**
	 * This method is used to make the servlet logic cleaner.
	 * 
	 * @param response The server should respond with this servlet channel
	 * @param smsResponse The string to send the cell phone user
	 * @throws IOException
	 */
	private void respondToSMS(HttpServletResponse response, String smsResponse) throws IOException
	{
		TwiMLResponse twiml = new TwiMLResponse();
		Message sms = new Message(smsResponse);
		try
		{
			twiml.append(sms);
		}
		catch (TwiMLException e)
		{
			e.printStackTrace();
		}
		response.setContentType("application/xml");
		response.getWriter().print(twiml.toXML());
	}
}
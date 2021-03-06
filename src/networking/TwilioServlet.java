package networking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import utility.ApprovedSenderList;
import utility.DroneRequest;
import utility.StatusPack;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
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
	public static final String ACCOUNT_SID = "AC70dbddca19f9add5c0bce7c1bb61ff50";
	public static final String AUTH_TOKEN = "620bf1fc6865d6274b2df9fbe9b9a149";

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

				respondWithSMS(response, "authentication request approved");
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
			if (droneResponse.command == DroneRequest.takePicture || droneResponse.command == DroneRequest.detect)
				sendMMS(fromNumber, droneResponse.message);

			else if (droneResponse.status)
				respondWithSMS(response, "command sent successfully:\n" + droneResponse.message);

			else
				respondWithSMS(response, "invalid command or drone communication failure");
		}
	}

	/**
	 * This method is used to make the servlet logic cleaner.
	 * 
	 * @param response The server should respond with this servlet channel
	 * @param smsResponse The string to send the cell phone user
	 * @throws IOException
	 */
	private void respondWithSMS(HttpServletResponse response, String smsResponse) throws IOException
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

	/**
	 * This method is used to clean up the servlet code.
	 * 
	 * @param response
	 * @param smsResponse
	 * @param mediaURL
	 * @throws IOException
	 */
	private void sendMMS(String toNumber, String mediaURL)
	{
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
		Account account = client.getAccount();
		MessageFactory messageFactory = account.getMessageFactory();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("To", toNumber));
		params.add(new BasicNameValuePair("From", "+14242924689"));
		params.add(new BasicNameValuePair("Body", "drone inteligence"));
		params.add(new BasicNameValuePair("MediaUrl", mediaURL));

		try
		{
			messageFactory.create(params);
		}
		catch (TwilioRestException e)
		{
			e.printStackTrace();
		}
	}
}
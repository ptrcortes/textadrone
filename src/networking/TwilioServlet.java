package networking;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utility.ApprovedSenderList;

import com.twilio.sdk.verbs.Message;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;

public class TwilioServlet extends HttpServlet
{
	private static final long serialVersionUID = 7206213494388714136L;

	// service() responds to both GET and POST requests.
	// You can also use doGet() or doPost()
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		ApprovedSenderList callers = new ApprovedSenderList();
		callers.add("+14804153358", "Tom");
		callers.add("+14802598397", "Kyler");
		callers.add("+17143305057a", "Peter");
		String fromNumber = request.getParameter("From");
		String knownCaller = callers.get(fromNumber);
		String message;

		System.out.println("incoming message: " + request.getParameter("Body"));

		if (knownCaller != null)
		{
			message = "request recieved, " + knownCaller;

			TwiMLResponse twiml = new TwiMLResponse();
			Message sms = new Message(message);
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
}
/**
 * 
 */
package networking;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * This class initializes the Jetty server with a TwilioServlet handler to
 * process POSTs.
 *
 * @author Peter Cortes
 */
public class SMSReciever
{
	public SMSReciever()
	{
		// Create a basic jetty server object that will listen on port 8080.
		Server server = new Server(8080);

		// The ServletHandler is a dead simple way to create a context handler
		// that is backed by an instance of a Servlet.
		// This handler then needs to be registered with the Server object.
		ServletHandler handler = new ServletHandler();
		server.setHandler(handler);

		// Passing in the class for the Servlet allows jetty to instantiate an
		// instance of that Servlet and mount it on a given context path.

		// IMPORTANT:
		// This is a raw Servlet, not a Servlet that has been configured
		// through a web.xml @WebServlet annotation, or anything similar.
		handler.addServletWithMapping(TwilioServlet.class, "/*");

		// Start things up!
		try
		{
			server.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		// The use of server.join() the will make the current thread join and
		// wait until the server is done executing.
		// See
		// http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#join()
	}
}

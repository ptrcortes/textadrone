/**
 * 
 */
package control;

/**
 * This static class interprets commands recieved by the SMSReciever and
 * TwilioServlet and attemps to translate them into a commands that can be sent
 * to the object talking to the drone.
 *
 * @author Peter Cortes
 */
public class CommandInterpreter
{
	public static boolean interpret(String input)
	{
		return input.matches("meow");
	}
}

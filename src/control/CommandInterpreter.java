/**
 * 
 */
package control;

import utility.DroneRequest;

/**
 * This static class interprets commands received by the SMSReciever and
 * TwilioServlet and attempts to translate them into a commands that can be sent
 * to the object talking to the drone.
 *
 * @author Peter Cortes
 */
public class CommandInterpreter
{
	public static boolean interpret(String input)
	{
		for (DroneRequest d: DroneRequest.values())
			if (input.toLowerCase().contains(d.toString()))
				switch (d)
				{
					case circle:
						break;
					case flip:
						break;
					case picture:
						return true;
					case status:
						break;
					case video:
						break;
				}

		return false;
	}
}

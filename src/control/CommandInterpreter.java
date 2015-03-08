/**
 * 
 */
package control;

import textadrone.Shell;
import utility.DroneRequest;
import utility.StatusPack;

/**
 * This static class interprets commands received by the SMSReciever and
 * TwilioServlet and attempts to translate them into a commands that can be sent
 * to the object talking to the drone.
 *
 * @author Peter Cortes
 */
public class CommandInterpreter
{
	public static StatusPack interpret(String input)
	{
		for (DroneRequest d: DroneRequest.values())
			if (input.toLowerCase().contains(d.toString()))
				switch (d)
				{
					case doCircle:
						break;
					case doFlip:
						return runNode("flip.js");
					case takePicture:
						break;
					case reportStatus:
						return runNode("battery_status.js");
					case takeVideo:
						break;
				}

		return new StatusPack(false, "");
	}

	private static StatusPack runNode(String file)
	{
		return Shell.execute("nodejs ./nodejs/" + file);
	}
}

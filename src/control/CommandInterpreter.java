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
						return runNode("flip.js", d);
					case takePicture:
						return runPython("upload_send.py", d);
					case reportStatus:
						return runNode("battery_status.js", d);
					case detect:
						break;
					case configure:
						return runNode("configure_drone.js", d);
				}

		return new StatusPack(false, "");
	}

	private static StatusPack runNode(String file, DroneRequest d)
	{
		StatusPack sp = Shell.execute("nodejs ./nodejs/" + file);
		sp.command = d;
		return sp;
	}

	private static StatusPack runPython(String file, DroneRequest d)
	{
		StatusPack sp = Shell.execute("python ./src/unused/" + file);
		sp.message.replaceAll("\n", "");
		sp.message.replaceAll("\r", "");
		System.out.println("asdf" + sp.message);
		sp.command = d;
		return sp;
	}
}

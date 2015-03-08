/**
 * 
 */
package control;

import sample.DetectFaceDemo;
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
						return takePicture(d);
					case reportStatus:
						return runNode("battery_status.js", d);
					case detect:
						return runDetection(d);
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

	private static StatusPack runDetection(DroneRequest d)
	{
		StatusPack njsSP = Shell.execute("nodejs ./nodejs/detect.js");
		njsSP.command = d;
		new DetectFaceDemo().run();

		StatusPack pSP = Shell.execute("python ./nodejs/upload_send.py");
		pSP.message.replaceAll("\n", "");
		pSP.message.replaceAll("\r", "");
		njsSP.message = pSP.message;

		return njsSP;
	}

	private static StatusPack takePicture(DroneRequest d)
	{
		StatusPack njsSP = Shell.execute("nodejs ./nodejs/save_png_stream.js");
		njsSP.command = d;

		try
		{
			Thread.sleep(15000);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StatusPack pSP = Shell.execute("python ./nodejs/upload_send.py");
		pSP.message.replaceAll("\n", "");
		pSP.message.replaceAll("\r", "");
		njsSP.message = pSP.message;
		
		System.out.println(njsSP.message);
		
		System.out.println(njsSP.command.toString());

		return njsSP;
	}
}

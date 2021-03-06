/**
 * 
 */
package textadrone;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import utility.StatusPack;

/**
 * This class uses the system shell to start nodejs processes. We need this
 * because the javadrone API wasn't working, while nodejs was.
 *
 * @author Peter Cortes
 */
public class Shell
{
	private final static String confirmation = "successful";

	public static StatusPack execute(String command)
	{
		StringBuffer output = new StringBuffer();

		Process p;
		try
		{
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null)
				output.append(line + "\n");

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		System.out.println("> " + command + ": " + output);

		if (output.toString().contains(confirmation))
		{
			String text = output.toString().replace(confirmation, "");
			return new StatusPack(true, text);
		}

		return new StatusPack(false, output.toString());
	}
}

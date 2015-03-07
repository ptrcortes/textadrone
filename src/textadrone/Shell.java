/**
 * 
 */
package textadrone;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 *
 * @author Peter Cortes
 */
public class Shell
{
	public static void execute(String command)
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

		System.out.println(output);
//		return output.toString();

	}

}

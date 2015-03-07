/**
 * 
 */
package textadrone;

import java.io.FileReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 *
 *
 * @author Peter Cortes
 */
public class ScriptRunner
{
	public static void execute(String filename)
	{
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		try
		{
			FileReader reader = new FileReader(filename);
			engine.eval(reader);
			reader.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

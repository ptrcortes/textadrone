/**
 * 
 */
package utility;

/**
 *
 *
 * @author Peter Cortes
 */
public class StatusPack
{
	public boolean status;
	public String message;
	public DroneRequest command;

	public StatusPack(boolean b, String m)
	{
		status = b;
		message = m;
		command = null;
	}

	public StatusPack(boolean b, String m, DroneRequest d)
	{
		status = b;
		message = m;
		command = d;
	}
}

/**
 * 
 */
package textadrone;

import java.io.IOException;

import networking.SMSReciever;

/**
 *
 *
 * @author Peter Cortes
 */
public class TextADrone
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			Runtime.getRuntime().exec("ls");
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new SMSReciever();
		System.out.println();
	}
}

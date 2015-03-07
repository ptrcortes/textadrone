/**
 * 
 */
package textadrone;

import networking.SMSReciever;

/**
 * This main class connects the various parts of the system together.
 *
 * @author Peter Cortes
 */
public class TextADrone
{
	public static void main(String[] args)
	{
		new SMSReciever();
		System.out.println();
	}
}

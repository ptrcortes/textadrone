package com.codeminders.ardrone.examples;

import com.codeminders.ardrone.ARDrone;

public class TakeOffAndLand
{
	private static final long CONNECT_TIMEOUT = 3000;

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ARDrone drone;
		try
		{
			System.out.println("dfasd");
			// Create ARDrone object,
			// connect to drone and initialize it.
			drone = new ARDrone();
			drone.connect();
			drone.clearEmergencySignal();
			System.out.println("ewrqwe");

			// Wait until drone is ready
			drone.waitForReady(CONNECT_TIMEOUT);

			System.out.println("before trim");
			// do TRIM operation
			drone.trim();
			System.out.println("after trim");

			// Take off
			System.err.println("Taking off");
			drone.takeOff();

			// Fly a little :)
			Thread.sleep(5000);

			// Land
			System.err.println("Landing");
			drone.land();

			// Give it some time to land
//			Thread.sleep(2000);

			System.out.println("mfeo");
			// Disconnect from the done
			drone.disconnect();
			
			System.out.println("disconnected");

		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}
}

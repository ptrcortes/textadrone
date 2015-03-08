/**
 * 
 */
package utility;

/**
 *
 *
 * @author Peter Cortes
 */
public enum DroneRequest
{
	takePicture("take a picture"), takeVideo("record a video"), doCircle("do a circle"), doFlip("do a flip"), reportStatus("report status"), configure("configure");

	private final String description;

	private DroneRequest(final String s)
	{
		description = s;
	}

	@Override
	public String toString()
	{
		return description;
	}
}

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
	picture("take a picture"), video("record a video"), circle("do a circle"), flip("do a flip"), status("report status");

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

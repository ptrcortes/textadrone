/**
 * 
 */
package utility;

import java.util.HashMap;

/**
 * @author Peter Cortes
 */
public class ApprovedSenderList extends HashMap<String, String>
{
	private static final long serialVersionUID = -8703527850044083423L;

	public boolean add(String name, String number)
	{
		return super.put(name, number) != null;
	}
}

/**
 * @author scott
 *
 */
public class DataObjects {
	/**
	 * @param size
	 * @return
	 */
	public String charData(int size, String element) {
		if (size < 1) {
			return null;
		}
		element = element.substring(0, size);
		return element;
	}
	
	public int intData(int element) {
		return element;
	}
}
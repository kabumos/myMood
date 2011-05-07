/**
 * 
 */
package cream.mymood.util;

/**
 * @author Cream
 * @since 2011-04-16
 */
public class StringUtil {

	/**
	 * Check out whether the input string is empty string.
	 * <p>
	 * This implements will check original data. The value equals to calling
	 * <code>isEmpty(str, false)</code>.
	 * 
	 * @param str
	 * @return true - input string is null or empty.
	 */
	public static boolean isEmpty(String str) {
		return isEmpty(str, false);
	}

	/**
	 * Check out whether the input string is empty string.
	 * 
	 * @param str
	 *            the string need to check.
	 * @param isTrim
	 *            true - to trim the string first.
	 * @return
	 */
	public static boolean isEmpty(String str, boolean isTrim) {
		String inputStr = isTrim ? trim(str) : str;
		return inputStr == null ? true : inputStr.equals("");
	}

	/**
	 * Trim an input string. The only different with {@code String#trim()} is
	 * this method can supported <code>null</code> value. When input
	 * <code>null</code>, it will return <code>null</code>.
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		return str == null ? null : str.trim();
	}
}

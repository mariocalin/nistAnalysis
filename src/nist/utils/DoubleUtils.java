package nist.utils;

import java.text.DecimalFormat;

public class DoubleUtils {

	/**
	 * Format a double to a two decimal number
	 * 
	 * @param number
	 *            Nnumber to be formatted
	 * @return String for the formatted number
	 */
	public static String doubleToCommaString(double number) {
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
		String numberAsString = decimalFormat.format(number);
		return numberAsString;
	}

}

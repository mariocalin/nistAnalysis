package nist.functions;

import java.io.IOException;

/**
 * Interface that defines what a <i>NistDataResult</i> must have.
 * 
 * @author mario
 *
 */
public interface INistDataResult {

	/**
	 * Obtains a String representation of the result
	 * 
	 * @return String representation
	 */
	String toString();

	/**
	 * Creates a CSV file for the result
	 * 
	 * @param namefile
	 *            URI of the name in which the CSV will be appended
	 * @param removeFileIfExists
	 *            If the file is to be removed
	 * @throws IOException
	 *             Exception creating or writing to the file
	 */
	void toCSV(String namefile, boolean removeFileIfExists) throws IOException;

}

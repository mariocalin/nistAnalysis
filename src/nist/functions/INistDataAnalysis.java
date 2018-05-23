package nist.functions;

import nist.model.Result;

/**
 * Interface that defines what operations a Nist data analyzer must have
 * 
 * @author mario
 *
 */
public interface INistDataAnalysis {

	/**
	 * Creates a Result according to the data source file that has been previously
	 * provided and set with setSource (or similar)
	 * 
	 * @return A Result containing the results.
	 * 
	 * @throws NistException
	 *             Exception processing the information file.
	 */
	Result createResult() throws NistException;

	/**
	 * Set the data souce file
	 * 
	 * @param sourceFile
	 *            URI for the data file
	 */
	void setSource(String sourceFile);

}

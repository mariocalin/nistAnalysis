package nist;

import nist.functions.INistDataAnalysis;

import nist.functions.*;
import nist.model.Result;

/**
 * Main class - Entry point of the analyzer
 * 
 * @author mario
 *
 */
public class Main {

	/**
	 * Main function to be run
	 * 
	 * @param args
	 *            Arguments. Empty
	 * @throws Exception
	 *             Global exceptions
	 */
	public static void main(String[] args) throws Exception {
		// Creates an analyzer instance with the year to be analyzed
		INistDataAnalysis analyzer = new XMLNistParser(XMLNistParser.XMLFiles.FULL_YEAR_2017);

		// Creates a result
		Result result = analyzer.createResult();

		// Prints the result to a CSV file
		result.entriesResult().toCSV("entries-2017.csv", true);

		System.out.println("END OF PROGRAM");
	}

}

package nist;

import nist.functions.INistDataAnalysis;

import nist.functions.*;
import nist.model.Result;

public class Main {

	public static void main(String[] args) throws Exception {
		INistDataAnalysis analyzer = new XMLNistParser(XMLNistParser.XMLFiles.FULL_YEAR_2017);
		Result result = analyzer.createResult();
		result.entriesResult().toCSV("entries-2017.csv", true);
		System.out.println("OK");
	}

}

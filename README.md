# nistAnalysis
A simple developer tool to obtain the descriptive results for NIST vulnerabilities XML data feeds. 

## NIST database
Currently, one of the largest vulnerabilities database is the National Vulnerability Database (NVD). This database is a repository of the government of the United States of America, in which all the data is stored. The National Institute of Standars and Technology (NIST) is in charge of the management. This database provides data aaccording to the SCAP specification (Security Content Automation Protocol). This specification is a set of NIST specification to express and manipulate information related with the security failures and security configuration in a standarized way. SCAP relies on the CVE (Common Vulnerabilities and Exposures) vulnerabilities dictionaire, which is related to the CVSS (Common Vulnerability Scoring System) punctuation system that marks the severity of a vulnerability.

More info can be found in the following link: https://nvd.nist.gov/

## The tool
### Usage
This tool is developer-oriented, that is, no GUI nor Command-Line program is provided. The way to use this tool is to download or clone this repository and import it in your favourite java IDE and run it, changing the parameters you desire. Thus, this feature is to be added as a *future work*.

In order to run the tool, the Main.java file contains main function to be run with the IDE. 

### Documentation
The complete java documentation can be found in the doc folder inside this repository. However, in this section the most important aspects of the tool in terms of development are described.

#### src/nist/functions
There are three important in this package:
1. *INistDataAnalysis.java*: Interface that defines what operations a Nist data analyzer must have. This interface is created in order to provide parsers to different file formats (XML or JSON for example).
2. *INistDataResult.java*: Interface that defines what a *NistDataResult* must have. Every NistDataAnalyzer must create NistDataResults.
3. *XMLNistParser.java* which implements *INistDataAnalysis*. XML Parser for the XML Data Feed. It will parse the XML into the tool model.

#### src/nist/model
There are 3 main concepts in the model. 
* **Entry**: Represents a vulnerability entry. 
* **Category**: Representes a CWE vulnerability category
* **Result**: Represents a summary of a year in terms of vulnerabilities. It has two different *types* of Result: per category and per vulnerabilities. 

#### src/nist/utils
There is only one class that contains some utils in terms of readability. 

#### Main.java
This file is the entry point for the tool that contains the main function.

#### Usage sample
```java
public static void main(String[] args) throws Exception {
	// Creates an analyzer instance with the year to be analyzed
	INistDataAnalysis analyzer = new XMLNistParser(XMLNistParser.XMLFiles.FULL_YEAR_2017);

	// Creates a result
	Result result = analyzer.createResult();

	// Prints the entries result to a CSV file or String
	result.entriesResult().toCSV("entries-2017.csv", true);
	result.entriesResult().toString();

	// Prints the categories result to a CSV file or String
	result.categoriesResult().toCSV("categoies-2017.csv", true);
	result.categoriesResult().toString();

	System.out.println("END OF PROGRAM");
}
```
#### Diagram 
The next image will show a diagram of the execution of the tool.

![alt text](https://github.com/mariocalin/nistAnalysis/blob/master/images/diagram.png "Tool Diagram")


#### XML Data feeds
In order to run the tool and get results, it is mandatory to download the data feeds that NIST provides in their official website (https://nvd.nist.gov/vuln/data-feeds) and place them into the corresponding folder with the same exact name that is initialy defined. At the moment, only XML feeds are allowed and the folder is _XMLData_. 

##### *XML data feed structure*
Each XML file conains multiple elements called <entry>. This element represents a vulnerability registered in the database. Per each entry, some elements will be appended as children of the entry. Among other info elements (like ID or published date), the most interesting elements are:
* <vuln:vulnerable-configuration>. This element will contain in what configuration the vulnerability was found. NIST provides a list of configurations where they test the vulnerabilities.
* <vuln:vulnerable-software-list>. This element will contain the concrete products that are affected by the vulnerability.
* <vuln:cvss>. CVSS metrics for the vulnerability. Special intetrest in the final score given by the <cvss:score> element.
* <vuln:cwe>. Category of the vulnerability in the CVE dictionary.
	
##### *Parsing mechanism*
The parsing mechanism is very simple:
1. The XMLNistParser recibes a xml file path corresponding to the Nist XML year data feed. It loads the file and, by using the java lbiraries of javax.xml.parsers and org.w3c.dom, it creates a Result object containing the information needed in a object oriented way.
2. With the Result object, you can choose wether to print the entries results or the categories results (via CSV or console). The only difference between is the way of representing the information:
* EntryResult is focused on vulnerability entries, showing information about the entry code, the entry cvss score and the products affected by the vulnerability.
* CategoryResult is foucsed on cwe categories, showing information about the total number of vulnerablity entries that a category has and its average vulnerability cvss score.

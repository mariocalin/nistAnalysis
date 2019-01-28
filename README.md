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
The complete java documentation can be found in the doc folder, inside this repository. However, in this section the most important aspects of the tool in terms of development are described.

#### src/nist/functions

#### src/nist/model

#### src/nist/utils

#### Main.java
This file is the entry point for the tool that contains the main function.

#### XML Data feeds
In order to run the tool and get results, it is mandatory to download the data feeds that NIST provides in their official website (https://nvd.nist.gov/vuln/data-feeds) and place them into the corresponding folder with the same exact name that is initialy defined. At the moment, only XML feeds are allowed and the folder is _XMLData_. 

*XML data feed structure*
Each XML file conains multiple elements called <entry>. This element represents a vulnerability registered in the database. Per each entry, some elements will be appended as children of the entry. Among other info elements (like ID or published date), the most interesting elements are:
* <vuln:vulnerable-configuration>. This element will contain in what configuration the vulnerability was found. NIST provides a list of configurations where they test the vulnerabilities.
* <vuln:vulnerable-software-list>. This element will contain the concrete products that are affected by the vulnerability.
* <vuln:cvss>. CVSS metrics for the vulnerability. Special intetrest in the final score given by the <cvss:score> element.
* <vuln:cwe>. Category of the vulnerability in the CVE dictionary.

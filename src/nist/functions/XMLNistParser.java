package nist.functions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import nist.model.Category;
import nist.model.Entry;
import nist.model.Result;

/**
 * Class that implements INistDataAnalysis for an XML NIST data feed
 * 
 * @author mario
 *
 */
public class XMLNistParser implements INistDataAnalysis {

	private String XML_SOURCE;

	public XMLNistParser(String file) {
		this.XML_SOURCE = file;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Result createResult() throws NistException {
		LinkedList<Entry> entries = new LinkedList<Entry>();
		LinkedList<Category> categories = new LinkedList<Category>();

		try {
			Document doc = getDocument();

			NodeList entryNodes = doc.getDocumentElement().getElementsByTagName("entry");
			for (int i = 0; i < entryNodes.getLength(); i++) {
				Node node = entryNodes.item(i);
				Entry entry = new Entry();
				entry.setID(node.getAttributes().getNamedItem("id").getNodeValue());

				entry.setScore(obtainScore(node));
				entry.setVulnerableSoftware(obtainVulnerableSoftware(node));
				Category category = obtainCategory(node, categories);
				entry.setCategory(category);
				if (category != null)
					category.getEntries().add(entry);

				entries.add(entry);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new Result(entries, categories);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSource(String sourceFile) {
		this.XML_SOURCE = sourceFile;
	}

<<<<<<< HEAD
	/**
	 * Obtains an array of strings of vulnerable software from the vuln:product DOM
	 * node
	 * 
	 * @param node
	 *            vuln:product DOM Node
	 * @return Array of vulnerable software names
	 */
	private String[] obtainVulnerableSoftware(Node node) {
		ArrayList<String> sts = new ArrayList<String>();
		try {
			// Obtains the text of the node
			NodeList productnodes = ((Element) node).getElementsByTagName("vuln:product");
			for (int i = 0; i < productnodes.getLength(); i++) {
				Node subnode = productnodes.item(i);
				sts.add(subnode.getTextContent().substring(7, subnode.getTextContent().length()));
			}

		} catch (Exception ex) {
			// let it go
		}

		return sts.toArray(new String[sts.size()]);
	}

	/**
	 * Gets the CVSS score of the vuln:cvss DOM node
	 * 
	 * @param node
	 *            vuln:cvss DOM Node
	 * @return CVSS Vulnerability score
	 */
	private double obtainScore(Node node) {
		try {
			NodeList cvssNodes = ((Element) node).getElementsByTagName("vuln:cvss").item(0).getChildNodes();
			Node baseMetrics = searchNode(cvssNodes, "cvss:base_metrics");
			return Double
					.parseDouble(((Element) baseMetrics).getElementsByTagName("cvss:score").item(0).getTextContent());
		} catch (Exception ex) {
			return 0;
		}
	}

	/**
	 * Searches a node by its name which should be inside a node lists
	 * 
	 * @param nodes
	 *            List of nodes
	 * @param name
	 *            Node name
	 * @return node if found, null if not found
	 */
	private Node searchNode(NodeList nodes, String name) {
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeName().equals(name))
				return node;
		}
		return null;
	}

	/**
	 * Obtain the category of a vulnerability entry in the vuln:cew DOM Node and
	 * stores/references it in the list of already created categories. Can be null,
	 * which means that the vulnerability has no category assigned yet.
	 * 
	 * @param node
	 *            vuln:cew DOM Node
	 * @param categories
	 *            Category list
	 * @return Category instance
	 */
	private Category obtainCategory(Node node, LinkedList<Category> categories) {

		Element eElement = (Element) node;
		NodeList nodes = eElement.getElementsByTagName("vuln:cwe");
		if (nodes.getLength() == 0)
			return null;

		Category temporalCategory = new Category(nodes.item(0).getAttributes().getNamedItem("id").getNodeValue());

		if (categories.contains(temporalCategory))
			return categories.stream().filter(c -> c.equals(temporalCategory)).findFirst().get();
		else {
			categories.add(temporalCategory);
			return temporalCategory;
		}

	}

	/**
	 * Obtains a Document instance of the XML Source
	 * 
	 * @return Document
	 * @throws ParserConfigurationException
	 *             Parser configuration exception
	 * @throws SAXException
	 *             Sax Exception
	 * @throws IOException
	 *             Error processing the file
	 */
	private Document getDocument() throws ParserConfigurationException, SAXException, IOException {
		File file = new File(XML_SOURCE);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);
		return document;
	}

	@SuppressWarnings("unused")
	public static class XMLFiles {
		public static final String FULL_YEAR_2015 = "XMLData/nvdcve-2.0-2015.xml";
		public static final String FULL_YEAR_2016 = "XMLData/nvdcve-2.0-2016.xml";
		public static final String FULL_YEAR_2017 = "XMLData/nvdcve-2.0-2017.xml";
		private static final String ACTUAL_YEAR_2018 = "XMLData/nvdcve-2.0-recent.xml";
=======
	private String[] obtainVulnerableSoftware(Node node) {
		ArrayList<String> sts = new ArrayList<String>();
		try {
			NodeList productnodes = ((Element) node).getElementsByTagName("vuln:product");

			for (int i = 0; i < productnodes.getLength(); i++) {
				Node subnode = productnodes.item(i);
				sts.add(subnode.getTextContent().substring(7, subnode.getTextContent().length()));
			}

		} catch (Exception ex) {
			// let it go
		}

		return sts.toArray(new String[sts.size()]);
	}

	private double obtainScore(Node node) {
		try {
			NodeList cvssNodes = ((Element) node).getElementsByTagName("vuln:cvss").item(0).getChildNodes();
			Node baseMetrics = searchNode(cvssNodes, "cvss:base_metrics");
			return Double
					.parseDouble(((Element) baseMetrics).getElementsByTagName("cvss:score").item(0).getTextContent());
		} catch (Exception ex) {
			return 0;
		}
	}

	private Node searchNode(NodeList nodes, String name) {
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeName().equals(name))
				return node;
		}
		return null;
	}

	private Category obtainCategory(Node node, LinkedList<Category> categories) {

		Element eElement = (Element) node;
		NodeList nodes = eElement.getElementsByTagName("vuln:cwe");
		if (nodes.getLength() == 0)
			return null;

		Category temporalCategory = new Category(nodes.item(0).getAttributes().getNamedItem("id").getNodeValue());

		if (categories.contains(temporalCategory))
			return categories.stream().filter(c -> c.equals(temporalCategory)).findFirst().get();
		else {
			categories.add(temporalCategory);
			return temporalCategory;
		}

	}

	private Document getDocument() throws ParserConfigurationException, SAXException, IOException {
		File file = new File(XML_SOURCE);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);
		return document;
	}

	@SuppressWarnings("unused")
	public static class XMLFiles {
		public static final String FULL_YEAR_2015 = "XMLData/nvdcve-2.0-2015.xml";
		public static final String FULL_YEAR_2016 = "XMLData/nvdcve-2.0-2016.xml";
		public static final String FULL_YEAR_2017 = "XMLData/nvdcve-2.0-2017.xml";
		private static final String ACTUAL_YEAR_2018 = "XMLData/nvdcve-2.0-recent.xml";

>>>>>>> branch 'master' of https://github.com/mariocalin/nistAnalysis.git
	}

}

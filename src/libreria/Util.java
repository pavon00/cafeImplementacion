package libreria;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Util {

	public static boolean isValid(String buffer, String xPathExpression, String parentNode) {
		try {
			Document documento = convertStringToDocument(buffer);
			// Preparación de xpath
			XPath xPath = XPathFactory.newInstance().newXPath();

			// Consultas
			xPath.evaluate(xPathExpression, documento, XPathConstants.NODESET);
			return true;

		} catch (Exception e) {
			try {
				Document documento = convertStringToDocument(putPadre(parentNode, buffer));
				XPath xPath = XPathFactory.newInstance().newXPath();

				// Consultas
				xPath.evaluate(xPathExpression, documento, XPathConstants.NODESET);
				return true;
			} catch (Exception e1) {
				return false;
			}
		}
	}

	public static boolean writeFile(String buffer, String ruta, String xPathExpression, String parentNode) {
		try {
			if (isValid(buffer, xPathExpression, parentNode)) {
				// Carga del documento xml
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document documento = builder.parse(new File(ruta));

				XPath xPath = XPathFactory.newInstance().newXPath();
				XPathExpression exp = xPath.compile(xPathExpression);

				NodeList nl = (NodeList) exp.evaluate(documento, XPathConstants.NODESET);

				for (int i = 0; i < nl.getLength(); i++) {
					Node node = nl.item(i);
					StringWriter buf = new StringWriter();
					Transformer xform = TransformerFactory.newInstance().newTransformer();
					xform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
					xform.transform(new DOMSource(node), new StreamResult(buf));
					buffer = buffer + buf.toString();
				}
				writeFile(ruta, buffer);
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static void writeFile(String ruta, String texto) throws IOException {
		// attach a file to FileWriter
		FileWriter fw = new FileWriter("output.txt");

		// read character wise from string and write
		// into FileWriter
		for (int i = 0; i < texto.length(); i++)
			fw.write(texto.charAt(i));

		// close the file
		fw.close();
	}

	public static Document convertStringToDocument(String xmlStr)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		StringBuilder xmlStringBuilder = new StringBuilder();
		xmlStringBuilder.append("<?xml version = \"1.0\"?>" + xmlStr);
		ByteArrayInputStream input = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
		return builder.parse(input);
	}

	public static String convertDocumentToString(Document documento, String xPathExpression) throws ParserConfigurationException, SAXException,
			IOException, XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
		XPath xPath = XPathFactory.newInstance().newXPath();
		XPathExpression exp = xPath.compile(xPathExpression);
		NodeList nl = (NodeList) exp.evaluate(documento, XPathConstants.NODESET);

		String buffer = "";
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			StringWriter buf = new StringWriter();
			Transformer xform = TransformerFactory.newInstance().newTransformer();
			xform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			xform.transform(new DOMSource(node), new StreamResult(buf));
			buffer = buffer + buf.toString();
		}
		return buffer;
	}

	private static ArrayList<String> splitDocumentToStrings(Document documento, String xPathExpression)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException,
			TransformerFactoryConfigurationError, TransformerException {

		ArrayList<String> aux = new ArrayList<String>();
		XPath xPath = XPathFactory.newInstance().newXPath();
		XPathExpression exp = xPath.compile(xPathExpression);
		NodeList nl = (NodeList) exp.evaluate(documento, XPathConstants.NODESET);

		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			StringWriter buf = new StringWriter();
			Transformer xform = TransformerFactory.newInstance().newTransformer();
			xform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			xform.transform(new DOMSource(node), new StreamResult(buf));
			aux.add(buf.toString());
		}
		return aux;
	}

	public static ArrayList<String> splitXmlStringToElement(String string, String xPathExpression)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException,
			TransformerFactoryConfigurationError, TransformerException {

		return splitDocumentToStrings(convertStringToDocument(string), xPathExpression);

	}

	public static ArrayList<String> getXmlxPath(String string, String xPathExpression)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException,
			TransformerFactoryConfigurationError, TransformerException {

		return getXmlxPath(convertStringToDocument(string), xPathExpression);

	}


	private static ArrayList<String> getXmlxPath(Document documento, String xPathExpression)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException,
			TransformerFactoryConfigurationError, TransformerException {

		ArrayList<String> aux = new ArrayList<String>();
		XPath xPath = XPathFactory.newInstance().newXPath();
		XPathExpression exp = xPath.compile(xPathExpression);
		NodeList nl = (NodeList) exp.evaluate(documento, XPathConstants.NODESET);

		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			StringWriter buf = new StringWriter();
			Transformer xform = TransformerFactory.newInstance().newTransformer();
			xform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			xform.transform(new DOMSource(node), new StreamResult(buf));
			aux.add(buf.toString());
		}
		return aux;
	}

	public static String putPadre(String padre, String xml) {
		String aux = "<" + padre + ">" + xml + "</" + padre + ">";
		return aux;
	}
}

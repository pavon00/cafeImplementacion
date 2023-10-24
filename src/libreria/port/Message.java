package libreria.port;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

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

public class Message {
	// esta clase tiene el buffer de datos

	private String buffer;

	public Message() {
		this.buffer = "";
	}

	public String getBuffer() {
		return buffer;
	}

	public void setBuffer(String buffer) {
		this.buffer = buffer;
	}

	@Override
	public String toString() {
		return this.buffer;
	}

	public static Document convertStringToDocument(String xmlStr) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		StringBuilder xmlStringBuilder = new StringBuilder();
		xmlStringBuilder.append("<?xml version = \"1.0\"?>" + xmlStr);
		ByteArrayInputStream input = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
		return builder.parse(input);
	}

	public String convertDocumentToString(Document documento) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
		XPath xPath = XPathFactory.newInstance().newXPath();
		XPathExpression exp = xPath.compile("/");
		NodeList nl = (NodeList) exp.evaluate(documento, XPathConstants.NODESET);
		System.out.println("Found " + nl.getLength() + " results");

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
	
	public void documentToString(Document documento) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException {
		this.buffer = convertDocumentToString(documento);
	}
	
	public static String putPadre(String padre, String xml) {
		return "<"+padre+">"+xml+"</"+padre+">";
	}

}

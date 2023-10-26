package libreria.port;

import java.io.File;
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

import libreria.Port;
import libreria.Util;

public class Message {
	// esta clase tiene el buffer de datos

	private String buffer;
	private Port port;

	public Message(Port port) {
		this.setPort(port);
	}

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

	public static String getXML(String ruta, String xPathExpression) {
		try {
			// Carga del documento xml
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document documento = builder.parse(new File(ruta));

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

		} catch (Exception e) {
			return "";
		}
	}

	public void documentToString(Document documento) throws XPathExpressionException, ParserConfigurationException,
			SAXException, IOException, TransformerFactoryConfigurationError, TransformerException {
		this.buffer = Util.convertDocumentToString(documento,"/");
	}

	public Port getPort() {
		return this.port;
	}

	public void setPort(Port port) {
		this.port = port;
	}

}

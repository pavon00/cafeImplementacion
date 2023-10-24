package main;

import java.io.File;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import libreria.port.Message;

public class XPathParserDemo {
	public static void main(String[] args) {
		// La expresion xpath de busqueda
		String parentNode = "drinks";
		String xPathExpression = "cafe_order/drinks/drink";
		String ruta = "order2.xml";

		try {
			// Carga del documento xml
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document documento = builder.parse(new File(ruta));

			// Preparación de xpath
			XPath xpath = XPathFactory.newInstance().newXPath();

			// Consultas
			NodeList nodos = (NodeList) xpath.evaluate(xPathExpression, documento, XPathConstants.NODESET);

			for (int i = 0; i < nodos.getLength(); i++) {
				Node nNode = nodos.item(i);
				System.out.println("Current Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					System.out.println("Nombre : " + eElement.getElementsByTagName("name").item(0).getTextContent());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String buffer = "";

		try {
			// Carga del documento xml
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document documento = builder.parse(new File(ruta));

			XPath xPath = XPathFactory.newInstance().newXPath();
			XPathExpression exp = xPath.compile(xPathExpression);

			NodeList nl = (NodeList) exp.evaluate(documento, XPathConstants.NODESET);
			System.out.println("Found " + nl.getLength() + " results");

			for (int i = 0; i < nl.getLength(); i++) {
				Node node = nl.item(i);
				StringWriter buf = new StringWriter();
				Transformer xform = TransformerFactory.newInstance().newTransformer();
				xform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				xform.transform(new DOMSource(node), new StreamResult(buf));
				buffer = buffer + buf.toString();
			}
			System.out.println(buffer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		NodeList nodos = null;
		try {
			Document documento = Message.convertStringToDocument(buffer);
			// Preparación de xpath
			xPathExpression = "drinks/drink";
			XPath xPath = XPathFactory.newInstance().newXPath();

			// Consultas
			nodos = (NodeList) xPath.evaluate(xPathExpression, documento, XPathConstants.NODESET);

		} catch (Exception e) {
			try {
				Document documento = Message.convertStringToDocument(Message.putPadre(parentNode, buffer));
				// Preparación de xpath
				xPathExpression = "drinks/drink";
				XPath xPath = XPathFactory.newInstance().newXPath();

				// Consultas
				nodos = (NodeList) xPath.evaluate(xPathExpression, documento, XPathConstants.NODESET);

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if (nodos != null) {
			for (int i = 0; i < nodos.getLength(); i++) {
				Node nNode = nodos.item(i);
				System.out.println("Current Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					System.out.println("Nombre : " + eElement.getElementsByTagName("name").item(0).getTextContent());
				}
			}
		}

	}
}
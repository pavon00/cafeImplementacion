package libreria.threader.port;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import libreria.Connector;
import libreria.Util;
import libreria.threader.ThreaderAdapter;

public abstract class Port extends ThreaderAdapter {
	private Message m;
	private Connector con;
	// si te confunde hay casos en los que no se usa algun slot

	public Port() {
		this.m = new Message(this);
	}

	public String getBufferString() {
		return m.getBuffer();
	}

	public Document getBuffer() throws ParserConfigurationException, SAXException, IOException {
		return Util.convertStringToDocument(m.getBuffer());
	}

	public void setBufferString(String m) {
		this.m.setBuffer(m);
	}

	public void setBuffer(Document m) throws XPathExpressionException, ParserConfigurationException, SAXException,
			IOException, TransformerFactoryConfigurationError, TransformerException {
		this.m.setBuffer(Util.convertDocumentToString(m, "/"));
	}

	public Connector getConnector() {
		return con;
	}

	public void setConnector(Connector con) {
		this.con = con;
	}

}

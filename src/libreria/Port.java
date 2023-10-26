package libreria;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import libreria.port.Message;

public abstract class Port extends Thread {
	private Message m;
	private Connector con;
	// si te confunde hay casos en los que no se usa algun slot
	
	@Override
	public void run() {
		realizarAccion();
	}

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
	
	public void realizarAccion() {
		
	}
	
	public void setSlotEntrada(Slot s) {
		
	}
	
	public void setSlotSalida(Slot s) {
		
	}

	public Connector getConnector() {
		return con;
	}

	public void setConnector(Connector con) {
		this.con = con;
	}
}

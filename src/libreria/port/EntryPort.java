package libreria.port;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import libreria.Connector;
import libreria.Port;
import libreria.Slot;

public class EntryPort extends Port {
	public EntryPort(Connector connector) {
		super(connector);
	}
	
	@Override
	public void run() {
		// esperar slot entrada;
		super.run();
		this.leerFichero();
		if (this.getSlotSalida().hayPortSalida()) {
			try {
				this.getSlotSalida().getPortSalida().setBufferString(getBufferString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (this.getSlotSalida().hayTaskSalida()) {
			try {
				this.getSlotSalida().getTaskSalida().setBuffer(getBufferString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private Slot outputSlot;

	public Slot getOutputSlot() {
		return outputSlot;
	}

	public void setOutputSlot(Slot outputSlot) {
		this.outputSlot = outputSlot;
	}
}

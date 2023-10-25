package libreria.task;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import libreria.Port;
import libreria.Slot;
import libreria.Task;
import libreria.Util;

/*
 * Divide un mensaje de entrada formado por una lista de elementos en tantos mensajes como elementos tenga
 * Entradas: 1, Salidas: 1
 * 
*/

public class Splitter extends Transformer{
	
	private String xPathExpression;
	
	public Splitter(String xPathExpression){
		this.setxPathExpression(xPathExpression);
	}

	@Override
	public void run() {
		//esperar a los nodos de entrada
		super.run();
		ArrayList<String> bufferAux = null;
		try {
			bufferAux = Util.splitXmlStringToElement(this.getBuffer(), xPathExpression);
			System.out.println(bufferAux);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		if (bufferAux != null) {
			for (Slot slot : this.getSlotsSalida()) {
				for (String string : bufferAux) {
					Task t = slot.getTaskSalida();
					Port p = slot.getPortSalida();
					if (t != null) {
						t.setBuffer(string);
					}
					if (p != null) {
						p.setBufferString(string);
					}
				}
			}
		}
	}

	public String getxPathExpression() {
		return xPathExpression;
	}

	public void setxPathExpression(String xPathExpression) {
		this.xPathExpression = xPathExpression;
	}
	
}

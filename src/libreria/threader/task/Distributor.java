package libreria.threader.task;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import libreria.Slot;
import libreria.Util;

/*
 * Distribuye los mensajes de entrada hacia una o más salidas en función de un criterio
 * Entradas: 1, Salidas: n
 * Hecho
 * 
*/

public class Distributor extends Task {

	private ArrayList<String> buffers, xPathExpressions;
	private ArrayList<Slot> slotsSalida;
	private Slot slotEntrada;
	private ArrayList<Integer> nSlotXPathExpresions;

	public Distributor() {
		this.slotsSalida = new ArrayList<Slot>();
		this.buffers = new ArrayList<String>();
		this.xPathExpressions = new ArrayList<String>();
		this.nSlotXPathExpresions = new ArrayList<Integer>();
	}

	public void direccionar(Integer nSlotXPathExpresion, String xPathExpression) {
		this.nSlotXPathExpresions.add(nSlotXPathExpresion);
		this.xPathExpressions.add(xPathExpression);
	}

	@Override
	public void realizarAccion() {
		super.realizarAccion();
		try {
			distributorTask();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void distributorTask() throws XPathExpressionException, ParserConfigurationException, SAXException,
			IOException, TransformerFactoryConfigurationError, TransformerException {
		for (int i = 0; i < xPathExpressions.size(); i++) {
			Integer nSlot = nSlotXPathExpresions.get(i);
			for (String buff : buffers) {
				ArrayList<String> list = Util.getXmlxPath(buff, xPathExpressions.get(i));
				for (String string : list) {
					Slot sl = slotsSalida.get(nSlot);
					sl.setBufferString(string, sl);
				}
			}

		}
	}

	@Override
	public void esperarNodosEntrada() throws InterruptedException {
		slotEntrada.esperar();
	}

	@Override
	public void setBufferString(String bufferAux, Slot s) {
		this.buffers.add(bufferAux);
	}

	@Override
	public String getBufferString() {
		String aux = "";
		for (String string : buffers) {
			aux = aux + string;
		}
		return aux;
	}

	@Override
	public void setSlotEntrada(Slot s) {
		this.slotEntrada = s;
	}

	@Override
	public void setSlotSalida(Slot s) {
		this.slotsSalida.add(s);
	}

}

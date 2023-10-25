package libreria.task;

import java.util.ArrayList;

import libreria.Slot;
import libreria.Util;

/*
 * Reconstruye un mensaje divido previamente por una tarea Splitter
 * Entradas: 1, Salidas: 1
 * 
*/

public class Aggregator extends Transformer{
	
	private String nodoPadre;
	private ArrayList<String> buffers;
	
	public Aggregator(String nodoPadre){
		this.setNodoPadre(nodoPadre);
		setBuffers(new ArrayList<String>());
	}

	@Override
	public void run() {
		//esperar a los nodos de entrada
		super.run();
		String bufferAux = aggregatorTarea();
		for (Slot slot : this.getSlotsSalida()) {
			slot.getPortSalida().setBufferString(bufferAux);
		}
	}
	
	public String aggregatorTarea() {
		String bufferAux = "";
		for (String string : buffers) {
			bufferAux = bufferAux + string;
		}
		return Util.putPadre(nodoPadre, bufferAux);
	}

	public String getNodoPadre() {
		return nodoPadre;
	}

	public void setNodoPadre(String xPathExpression) {
		this.nodoPadre = xPathExpression;
	}
	
	@Override
	public void setBuffer(String buffer) {
		System.out.println("elemento: "+buffer);
		ArrayList<String> buffersAux = getBuffers();
		buffersAux.add(buffer);
		setBuffers(buffersAux);
	}

	public ArrayList<String> getBuffers() {
		return buffers;
	}

	public void setBuffers(ArrayList<String> buffers) {
		this.buffers = buffers;
	}
	
}

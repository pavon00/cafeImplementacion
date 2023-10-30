package libreria.threader.task;

import java.util.ArrayList;

import libreria.Slot;
import libreria.Util;

/*
 * Reconstruye un mensaje divido previamente por una tarea Splitter
 * Entradas: 1, Salidas: 1
 * 
 * Hecho
 * 
*/

public class Aggregator extends Task {

	private ArrayList<String> nodoPadre;
	private ArrayList<String> buffers;
	private Slot slotEntrada, slotSalida;

	public Aggregator(String... nodoPadre) {
		this.nodoPadre = new ArrayList<String>();
		for (String string : nodoPadre) {
			this.setNodoPadre(string);
		}
		setBuffers(new ArrayList<String>());
	}

	@Override
	public void realizarAccion() {
		// TODO Auto-generated method stub
		super.realizarAccion();
		String bufferAux = aggregatorTarea();
		slotSalida.setBufferString(bufferAux, slotSalida);
	}

	@Override
	public void esperarNodosEntrada() throws InterruptedException {
		slotEntrada.esperar();
	}

	public String aggregatorTarea() {
		String bufferAux = "";
		for (String string : buffers) {
			bufferAux = bufferAux + string;
		}
		for (String string : nodoPadre) {
			bufferAux = Util.putPadre(string, bufferAux);
		}
		return bufferAux;
	}

	public ArrayList<String> getNodoPadre() {
		return nodoPadre;
	}

	public void setNodoPadre(String... xPathExpression) {
		for (String string : xPathExpression) {
			this.nodoPadre.add(string);
		}
	}

	@Override
	public void setBufferString(String buffer, Slot s) {
		System.out.println("elemento Aggregator: " + buffer);
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
		this.slotSalida = s;
	}

}

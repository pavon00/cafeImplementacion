package libreria.threader.task;

import java.util.ArrayList;

import libreria.Process;
import libreria.Slot;
import libreria.Util;

/*
 * Reconstruye un mensaje divido previamente por una tarea Splitter
 * Entradas: 1, Salidas: 1
 * 
*/

public class Aggregator extends Transformer {

	private String nodoPadre;
	private ArrayList<String> buffers;
	private ArrayList<Slot> slotsEntrada, slotsSalida;

	public Aggregator(String nodoPadre) {
		this.slotsEntrada = new ArrayList<Slot>();
		this.slotsSalida = new ArrayList<Slot>();
		this.setNodoPadre(nodoPadre);
		setBuffers(new ArrayList<String>());
	}

	@Override
	public void realizarAccion() {
		// esperar a los nodos de entrada
		if (!slotsEntrada.isEmpty() && !slotsSalida.isEmpty()) {
			try {
				esperarNodosEntrada();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Salir de espera, buffer: " + this.getBufferString());
		}
		if (Process.ESPERAR) {
			try {
				sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		String bufferAux = aggregatorTarea();
		for (Slot slot : this.slotsSalida) {
			slot.setBufferString(bufferAux);
		}
	}

	public void esperarNodosEntrada() throws InterruptedException {
		for (Slot slotEntrada : slotsEntrada) {
			slotEntrada.esperar();
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
	public void setBufferString(String buffer) {
		System.out.println("elemento: " + buffer);
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
		this.slotsEntrada.add(s);

	}

	@Override
	public void setSlotSalida(Slot s) {
		this.slotsSalida.add(s);
	}

}

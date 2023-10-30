package libreria.threader.task;

import java.util.ArrayList;

import libreria.Process;
import libreria.Slot;

/*
 * Distribuye los mensajes de entrada hacia las salidas
 * Entradas: 1, Salidas: n
 * 
 * Hecho
 * 
*/

public class Replicator extends Task {

	private ArrayList<String> buffers;
	private ArrayList<Slot> slotsSalida;
	private Slot slotEntrada;

	public Replicator() {
		setBuffers(new ArrayList<String>());
		this.slotsSalida = new ArrayList<Slot>();
	}

	@Override
	public void setBufferString(String buffer, Slot s) {
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
	public void realizarAccion() {
		super.realizarAccion();
		for (Slot slot : this.slotsSalida) {
			for (String string : buffers) {
				slot.setBufferString(string, slot);
			}
		}

	}

	@Override
	public void esperarNodosEntrada() throws InterruptedException {
		slotEntrada.esperar();
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

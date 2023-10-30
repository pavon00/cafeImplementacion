package libreria.threader.task;

import java.util.ArrayList;

import libreria.Slot;

/*
 * Enruta mensajes de diferentes entradas hacia una misma salida
 * Entradas: n, Salidas: 1
 * Hecho
 * 
*/

public class Merger extends Task {
	private ArrayList<String> buffers;
	private ArrayList<Slot> slotsEntrada;
	private Slot slotSalida;

	public Merger() {
		this.slotsEntrada = new ArrayList<Slot>();
		this.buffers = new ArrayList<String>();
	}

	@Override
	public void realizarAccion() {
		super.realizarAccion();
		for (String string : buffers) {
			System.out.println("Merger enviar: " + this.slotsEntrada.size() + " puertos " + string);
			slotSalida.setBufferString(string, slotSalida);
		}

	}

	@Override
	public void esperarNodosEntrada() throws InterruptedException {
		for (Slot slotEntrada : slotsEntrada) {
			slotEntrada.esperar();
		}
	}

	@Override
	public void setBufferString(String buffer, Slot s) {
		System.out.println("elemento Merger: " + buffer);
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
		this.slotSalida = s;
	}
}

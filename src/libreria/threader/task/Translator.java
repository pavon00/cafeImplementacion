package libreria.threader.task;

import java.util.ArrayList;

import libreria.Slot;

/*
 * Distribuye los mensajes de entrada hacia las salidas
 * Entradas: 1, Salidas: n
 * 
 * Hecho
 * 
*/

public class Translator extends Task {

	private ArrayList<String> buffers;
	private ArrayList<Slot> slotsSalida;
	private Slot slotEntrada;

	public Translator() {
		setBuffers(new ArrayList<String>());
		this.slotsSalida = new ArrayList<Slot>();
	}

	@Override
	public boolean sePuedeEjecutar() {
		// TODO Auto-generated method stub
		return slotEntrada != null;
	}

	@Override
	public ArrayList<Slot> getSlotsEntrada() {
		// TODO Auto-generated method stub
		ArrayList<Slot> aux = new ArrayList<Slot>();
		aux.add(slotEntrada);
		return aux;
	}

	@Override
	public ArrayList<Slot> getSlotsSalida() {
		// TODO Auto-generated method stub
		return this.slotsSalida;
	}

	@Override
	public void setBufferString(String buffer, Slot s) {
		//System.out.println("elemento: " + buffer);
		ArrayList<String> buffersAux = getBuffers();
		buffersAux.add(buffer);
		setBuffers(buffersAux);
	}

	public synchronized ArrayList<String> getBuffers() {
		return buffers;
	}

	public synchronized void setBuffers(ArrayList<String> buffers) {
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
	public void setSlotEntrada(Slot s) {
		this.slotEntrada = s;

	}

	@Override
	public void setSlotSalida(Slot s) {
		this.slotsSalida.add(s);
	}

	@Override
	public boolean nodosEntradaHanMandadoMensaje() {
		return this.isEntradaMensaje();
	}

	@Override
	public void clearBuffer() {
		// TODO Auto-generated method stub
		setBuffers(new ArrayList<String>());
	}
}

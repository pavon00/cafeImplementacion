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
			//System.out.println("Merger enviar: " + this.slotsEntrada.size() + " puertos " + string);
			slotSalida.setBufferString(string, slotSalida);
		}
	}

	@Override
	public boolean sePuedeEjecutar() {
		// TODO Auto-generated method stub
		return !slotsEntrada.isEmpty();
	}

	@Override
	public ArrayList<Slot> getSlotsEntrada() {
		// TODO Auto-generated method stub
		return slotsEntrada;
	}

	@Override
	public ArrayList<Slot> getSlotsSalida() {
		// TODO Auto-generated method stub
		ArrayList<Slot> aux = new ArrayList<Slot>();
		aux.add(this.slotSalida);
		return aux;
	}

	@Override
	public void setBufferString(String buffer, Slot s) {
		//System.out.println("elemento Merger: " + buffer);
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
	public void setSlotEntrada(Slot s) {
		this.slotsEntrada.add(s);
	}

	@Override
	public void setSlotSalida(Slot s) {
		this.slotSalida = s;
	}

	// espera hasta tener el mismo numero de mensajes en el buffer que elementos ha
	// dividido el splitter
	@Override
	public boolean nodosEntradaHanMandadoMensaje() {
		for (Integer nElements : this.getProcess().getListaSplitNElements()) {
			if (nElements == this.buffers.size()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void clearBuffer() {
		// TODO Auto-generated method stub
		this.buffers = new ArrayList<String>();
		this.getProcess().setListaSplitNElements(new ArrayList<Integer>());
	}
}

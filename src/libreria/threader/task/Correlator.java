package libreria.threader.task;

import java.util.ArrayList;

import libreria.Slot;

/*
 * Correlaciona los mensajes en sus distintas entradas y los pone ordenados en sus respectivas salidas
 * Entradas: n,  Salidas: n (mismo número)
 * 
 * Hecho
 * 
*/

public class Correlator extends Task {

	private ArrayList<String> buffers;

	public synchronized ArrayList<String> getBuffers() {
		return buffers;
	}

	public synchronized void setBuffers(ArrayList<String> buffers) {
		this.buffers = buffers;
	}

	private ArrayList<Slot> slotsEntrada, slotsSalida;
	private ArrayList<Integer> nSlotEntrada;

	public Correlator() {
		this.slotsEntrada = new ArrayList<Slot>();
		this.slotsSalida = new ArrayList<Slot>();
		this.nSlotEntrada = new ArrayList<Integer>();
		this.buffers = new ArrayList<String>();
	}

	@Override
	public void realizarAccion() {
		this.setEjecutado(true);
		super.realizarAccion();
		for (int i = 0; i < buffers.size(); i++) {
			int nSlot = nSlotEntrada.get(i);
			Slot slot = this.slotsSalida.get(nSlot);
			slot.setBufferString(buffers.get(i), slot);
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
		return this.slotsSalida;
	}

	@Override
	public void setBufferString(String bufferAux, Slot s) {
		getBuffers().add(bufferAux);
		this.nSlotEntrada.add(this.slotsEntrada.indexOf(s));
	}

	@Override
	public String getBufferString() {
		String aux = "";
		for (int i = 0; i < getBuffers().size(); i++) {
			aux = aux + getBuffers().get(i) + " posicion: " + nSlotEntrada.get(i) + " ";
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

	// espera hasta tener el mismo numero de mensajes de cada puerto de entrada
	@Override
	public boolean nodosEntradaHanMandadoMensaje() {
		if (!this.isEntradaMensaje()) {
			return false;
		}
		ArrayList<Integer> listaConteo = new ArrayList<Integer>();

		for (int i = 0; i < slotsEntrada.size(); i++) {
			listaConteo.add(0);
		}

		for (int i = 0; i < getBuffers().size(); i++) {
			int nSlot = nSlotEntrada.get(i);
			listaConteo.set(nSlot, listaConteo.get(nSlot) + 1);
		}

		for (int i = 0; i < listaConteo.size(); i++) {
			//System.out.println("conteo posicion "+i+": "+listaConteo.get(i));
		}

		int aux = listaConteo.get(0);
		for (int i = 1; i < listaConteo.size(); i++) {
			if (listaConteo.get(i) == 0) {
				return false;
			}
			if (aux != listaConteo.get(i)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void clearBuffer() {
		// TODO Auto-generated method stub
		setBuffers(new ArrayList<String>()); 
		this.nSlotEntrada = new ArrayList<Integer>();
	}

}

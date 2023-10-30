package libreria.threader.port;

import java.util.ArrayList;

import libreria.Slot;

public class EntryPort extends Port {
	
	private Slot outputSlot;

	@Override
	public void realizarAccion() {
		outputSlot.setBufferString(getBufferString(), outputSlot);
	}
	

	@Override
	public boolean sePuedeEjecutar() {
		// TODO Auto-generated method stub
		return outputSlot != null;
	}

	@Override
	public ArrayList<Slot> getSlotsEntrada() {
		// TODO Auto-generated method stub
		ArrayList<Slot> aux = new ArrayList<Slot>();
		return aux;
	}


	public Slot getSlotSalida() {
		return outputSlot;
	}


	@Override
	public void setSlotSalida(Slot s) {
		this.outputSlot = s;
	}

	@Override
	public void setSlotEntrada(Slot s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBufferString(String string, Slot s) {
		this.setBufferString(string);
	}

	@Override
	public ArrayList<Slot> getSlotsSalida() {
		// TODO Auto-generated method stub
		ArrayList<Slot> aux = new ArrayList<Slot>();
		aux.add(this.outputSlot);
		return aux;
	}

	@Override
	public boolean nodosEntradaHanMandadoMensaje() {
		return this.isEntradaMensaje();
	}

}

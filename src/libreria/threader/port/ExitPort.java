package libreria.threader.port;

import java.util.ArrayList;

import libreria.Connector;
import libreria.Slot;

public class ExitPort extends Port {

	private Slot inputSlot;

	public ExitPort(Connector con) {
		super(con);
	}

	@Override
	public void realizarAccion() {
		// esperar slot entrada
		this.getConnector().getFuncion().ejecutar();
	}

	@Override
	public boolean sePuedeEjecutar() {
		// TODO Auto-generated method stub
		return inputSlot != null;
	}

	@Override
	public ArrayList<Slot> getSlotsEntrada() {
		// TODO Auto-generated method stub
		ArrayList<Slot> aux = new ArrayList<Slot>();
		aux.add(inputSlot);
		return aux;
	}

	public Slot getInputSlot() {
		return inputSlot;
	}

	public void setInputSlot(Slot inputSlot) {
		this.inputSlot = inputSlot;
	}

	@Override
	public void setSlotEntrada(Slot s) {
		// TODO Auto-generated method stub
		this.setInputSlot(s);
	}

	@Override
	public void setSlotSalida(Slot s) {
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
		return aux;
	}

	@Override
	public boolean nodosEntradaHanMandadoMensaje() {
		return this.isEntradaMensaje();
	}

	@Override
	public void ejecutar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearBuffer() {
		// TODO Auto-generated method stub
		setBuffers(new ArrayList<String>());
	}

}

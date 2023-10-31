package libreria.threader.port;

import java.util.ArrayList;

import libreria.Connector;
import libreria.Slot;

public class SolPort extends Port {

	private Slot inputSlot, outputSlot;
	private String ruta;

	public SolPort(Connector con, String ruta) {
		super(con);
		this.ruta = ruta;
	}

	@Override
	public void realizarAccion() {
		this.getConnector().getFuncion().ejecutar();
	}

	@Override
	public boolean sePuedeEjecutar() {
		// TODO Auto-generated method stub
		return inputSlot != null && outputSlot != null;
	}

	@Override
	public ArrayList<Slot> getSlotsEntrada() {
		// TODO Auto-generated method stub
		ArrayList<Slot> aux = new ArrayList<Slot>();
		aux.add(inputSlot);
		return aux;
	}

	public Slot getSlotEntrada() {
		return inputSlot;
	}

	public Slot getSlotSalida() {
		return outputSlot;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	@Override
	public void setSlotEntrada(Slot s) {
		this.inputSlot = s;
	}

	@Override
	public void setSlotSalida(Slot s) {
		this.outputSlot = s;
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

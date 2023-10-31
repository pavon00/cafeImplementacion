package libreria.threader.port;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import libreria.Connector;
import libreria.Process;
import libreria.Slot;

public class EntryPort extends Port {

	public EntryPort(Connector con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	private Slot outputSlot;

	@Override
	public void realizarAccion() {
		for (String s : getBuffers()) {
			outputSlot.setBufferString(s, outputSlot);
		}
	}

	@Override
	public void ejecutar() {
		Process p = Process.getInstance();
		ReentrantLock lock = p.getLock();
		Condition c = p.getColaProcesos();
		try {
			lock.lock();
		} finally {
			while (p.isEjecutandoProceso()) {
				try {
					c.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.getConnector().getFuncion().ejecutar();
			lock.unlock();
		}
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

	@Override
	public void clearBuffer() {
		// TODO Auto-generated method stub
		setBuffers(new ArrayList<String>());
	}

}

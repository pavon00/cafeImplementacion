package libreria.threader;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import libreria.Process;
import libreria.Slot;

public abstract class ThreaderAdapter extends Thread {

	private boolean ejecutado, entradaMensaje;

	@Override
	public void run() {
		setEjecutado(true);
		if (sePuedeEjecutar()) {
			while (!isTerminado()) {
				esperarNodosEntrada();
				if (!isTerminado()) {
					realizarAccion();
					for (Slot s : getSlotsSalida()) {
						s.getSalida().setEntradaMensaje(true);
					}
					notificar();
				}
			}
		}
	}

	public boolean isTerminado() {
		return Process.getInstance().isTerminar();
	}

	public abstract boolean sePuedeEjecutar();

	public abstract void realizarAccion();

	public abstract String getBufferString();

	public abstract void setSlotEntrada(Slot s);

	public abstract ArrayList<Slot> getSlotsEntrada();

	public abstract void setSlotSalida(Slot s);

	public abstract ArrayList<Slot> getSlotsSalida();

	public abstract void setBufferString(String string, Slot s);

	public void notificar() {
		ReentrantLock lock = Process.getInstance().getLock();
		Condition c = Process.getInstance().getCondition();
		try {
			lock.lock();
			this.setEntradaMensaje(false);
		} finally {
			c.signalAll();
			lock.unlock();
		}
	}

	public synchronized void setEntradaMensaje(boolean entradaMensaje) {
		this.entradaMensaje = entradaMensaje;
	}

	public synchronized boolean isEntradaMensaje() {
		return entradaMensaje;
	}

	public abstract boolean nodosEntradaHanMandadoMensaje();

	public void esperarNodosEntrada() {
		ReentrantLock lock = Process.getInstance().getLock();
		Condition c = Process.getInstance().getCondition();
		try {
			lock.lock();
		} finally {
			while (!nodosEntradaHanMandadoMensaje() && !isTerminado()) {
				try {
					c.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			lock.unlock();
		}
	}

	public synchronized boolean isEjecutado() {
		return ejecutado;
	}

	public synchronized void setEjecutado(boolean ejecutado) {
		this.ejecutado = ejecutado;
	}
}

package libreria.threader;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import libreria.Process;
import libreria.Slot;

public abstract class ThreaderAdapter extends Thread {

	private boolean ejecutado, entradaMensaje, primero, ultimo;

	@Override
	public void run() {
		setEjecutado(true);
		if (sePuedeEjecutar()) {
			while (!isTerminado()) {
				esperarNodosEntrada();
				if (!isTerminado()) {
					Process p = Process.getInstance();
					if (primero) {
						p.setEjecutandoProceso(true);
					}
					realizarAccion();
					clearBuffer();
					for (Slot s : getSlotsSalida()) {
						s.getSalida().setEntradaMensaje(true);
					}
					notificar();
					this.setEntradaMensaje(false);
					if (ultimo) {
						p.setEjecutandoProceso(false);
						try {
							p.getLock().lock();
						} finally {
							p.getColaProcesos().signalAll();
							p.getLock().unlock();
						}
					}
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

	public abstract void clearBuffer();

	public abstract void setBufferString(String string, Slot s);

	public static void notificar() {
		ReentrantLock lock = Process.getInstance().getLock();
		Condition c = Process.getInstance().getColaTareas();
		try {
			lock.lock();
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
		Condition c = Process.getInstance().getColaTareas();
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

	public boolean isPrimero() {
		return primero;
	}

	public void setPrimero(boolean primero) {
		this.primero = primero;
	}

	public boolean isUltimo() {
		return ultimo;
	}

	public void setUltimo(boolean ultimo) {
		this.ultimo = ultimo;
	}
}

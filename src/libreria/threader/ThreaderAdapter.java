package libreria.threader;

import libreria.Slot;

public abstract class ThreaderAdapter extends Thread{
	
	private boolean ejecutado, terminado;
	
	@Override
	public void run() {
		setEjecutado(true);
		realizarAccion();
		setTerminado(true);
	}

	public abstract void realizarAccion();

	public abstract String getBufferString();
	
	public abstract void setSlotEntrada(Slot s);

	public abstract void setSlotSalida(Slot s);

	public abstract void setBufferString(String string, Slot s);
	
	public boolean isTerminado() {
		return terminado;
	}

	public synchronized void setTerminado(boolean terminado) {
		this.terminado = terminado;
		if (terminado) {
			this.notifyAll();
		}
	}

	public boolean isEjecutado() {
		return ejecutado;
	}

	public void setEjecutado(boolean ejecutado) {
		this.ejecutado = ejecutado;
	}
}

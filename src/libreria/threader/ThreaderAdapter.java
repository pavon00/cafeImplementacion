package libreria.threader;

import libreria.Slot;

public abstract class ThreaderAdapter extends Thread{
	@Override
	public void run() {
		realizarAccion();
	}

	public abstract void realizarAccion();

	public abstract void setBufferString(String bufferAux);

	public abstract String getBufferString();
	
	public abstract void setSlotEntrada(Slot s);

	public abstract void setSlotSalida(Slot s);
	
	
}
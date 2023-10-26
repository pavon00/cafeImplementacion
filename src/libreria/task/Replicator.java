package libreria.task;

import libreria.Slot;

/*
 * Distribuye los mensajes de entrada hacia las salidas
 * Entradas: 1, Salidas: n
 * 
*/

public class Replicator extends Router {
	
	private String buffer;
	
	@Override
	public void run() {
		// esperar a los nodos de entrada
		super.run();
		for (Slot slot : this.getSlotsSalida()) {
			slot.getPortSalida().setBufferString(this.getBuffer());
		}
	}
	
	@Override
	public void setBuffer(String buffer) {
		// TODO Auto-generated method stub
		this.buffer = buffer;
	}
	
	@Override
	public String getBuffer() {
		// TODO Auto-generated method stub
		return this.buffer;
	}
}

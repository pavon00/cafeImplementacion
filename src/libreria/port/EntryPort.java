package libreria.port;

import libreria.Port;
import libreria.Slot;

public class EntryPort extends Port {
	private Slot outputSlot;

	public EntryPort(String ruta) {
		this.outputSlot = new Slot(this, ruta);
	}

	public void escribir() {
		this.outputSlot.escribirFichero();
	}

	public Slot getOutputSlot() {
		return outputSlot;
	}

	public void setOutputSlot(Slot outputSlot) {
		this.outputSlot = outputSlot;
	}
}

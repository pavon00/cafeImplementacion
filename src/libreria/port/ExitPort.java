package libreria.port;

import libreria.Port;
import libreria.Slot;

public class ExitPort extends Port {
	private Slot inputSlot;

	public ExitPort(String ruta) {
		this.inputSlot = new Slot(this, ruta);
	}

	public void leer() {
		this.inputSlot.leerFichero();
	}

	public Slot getInputSlot() {
		return inputSlot;
	}

	public void setInputSlot(Slot inputSlot) {
		this.inputSlot = inputSlot;
	}
}

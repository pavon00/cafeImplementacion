package libreria.port;

import libreria.Port;
import libreria.Slot;

public class SolPort extends Port {
	private Slot inputSlot, outputSlot;

	public SolPort(String rutaInput, String rutaOutput) {
		this.inputSlot = new Slot(this, rutaInput);
		this.outputSlot = new Slot(this, rutaOutput);
	}

	public void escribir() {
		this.outputSlot.escribirFichero();
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

package libreria.port;

import libreria.Port;
import libreria.Slot;

public class ExitPort extends Port {
	private Slot inputSlot;
	
	public Slot getInputSlot() {
		return inputSlot;
	}

	public void setInputSlot(Slot inputSlot) {
		this.inputSlot = inputSlot;
	}
}

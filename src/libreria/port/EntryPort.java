package libreria.port;

import libreria.Port;
import libreria.Slot;

public class EntryPort extends Port {
	private Slot outputSlot;

	public Slot getOutputSlot() {
		return outputSlot;
	}

	public void setOutputSlot(Slot outputSlot) {
		this.outputSlot = outputSlot;
	}
}

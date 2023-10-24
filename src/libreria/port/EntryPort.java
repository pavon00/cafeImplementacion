package libreria.port;

import libreria.Connector;
import libreria.Port;
import libreria.Slot;

public class EntryPort extends Port {
	public EntryPort(Connector connector) {
		super(connector);
		// TODO Auto-generated constructor stub
	}

	private Slot outputSlot;

	public Slot getOutputSlot() {
		return outputSlot;
	}

	public void setOutputSlot(Slot outputSlot) {
		this.outputSlot = outputSlot;
	}
}

package libreria.port;

import libreria.Connector;
import libreria.Port;
import libreria.Slot;

public class SolPort extends Port {
	public SolPort(Connector connector) {
		super(connector);
		// TODO Auto-generated constructor stub
	}

	private Slot inputSlot, outputSlot;

	public Slot getInputSlot() {
		return inputSlot;
	}

	public void setInputSlot(Slot inputSlot) {
		this.inputSlot = inputSlot;
	}

	public Slot getOutputSlot() {
		return outputSlot;
	}

	public void setOutputSlot(Slot outputSlot) {
		this.outputSlot = outputSlot;
	}
}

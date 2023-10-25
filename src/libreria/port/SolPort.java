package libreria.port;

import libreria.Connector;
import libreria.Port;
import libreria.Slot;

public class SolPort extends Port {
	public SolPort(Connector connector) {
		super(connector);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run() {
		// esperar slot entrada;
		super.run();
		this.leerFichero();
		if (this.getSlotSalida().hayPortSalida()) {
			try {
				this.getSlotSalida().getPortSalida().setBufferString(getBufferString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (this.getSlotSalida().hayTaskSalida()) {
			try {
				this.getSlotSalida().getTaskSalida().setBuffer(getBufferString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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

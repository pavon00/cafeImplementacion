package libreria.port;

import libreria.Connector;
import libreria.Port;
import libreria.Slot;

public class ExitPort extends Port {
	public ExitPort(Connector connector) {
		super(connector);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run() {
		// esperar slot entrada;
		super.run();
		this.escribirFichero();
	}

	private Slot inputSlot;
	
	public Slot getInputSlot() {
		return inputSlot;
	}

	public void setInputSlot(Slot inputSlot) {
		this.inputSlot = inputSlot;
	}
}

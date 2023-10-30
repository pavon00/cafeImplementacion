package libreria;

import libreria.threader.ThreaderAdapter;
import libreria.threader.port.FactoryEntryPort;
import libreria.threader.port.FactoryExitPort;
import libreria.threader.port.FactorySolPort;
import libreria.threader.port.Port;

public class Connector {
	
	public enum Tipo {
		Entrada, Salida, Sol;
	}
	
	private Port port;
	private FactoryEntryPort factoryEntryPort;
	private FactoryExitPort factoryExitPort;
	private FactorySolPort factorySolPort;

	public Connector(Tipo t, String ruta) {
		switch (t) {
		case Entrada:
			factoryEntryPort = new FactoryEntryPort();
			this.port = factoryEntryPort.crear(ruta);
			this.port.setConnector(this);
			break;
		case Salida:
			factoryExitPort = new FactoryExitPort();
			this.port = factoryExitPort.crear(ruta);
			this.port.setConnector(this);
			break;
		case Sol:
			factorySolPort = new FactorySolPort();
			this.port = factorySolPort.crear(ruta);
			this.port.setConnector(this);
			break;

		default:
			break;
		}

	}
	
	public void notificar() {
		this.port.setEntradaMensaje(true);
		ThreaderAdapter.notificar();
	}


	public Port getPort() {
		return port;
	}

	public void setPort(Port p) {
		this.port = p;
	}

}

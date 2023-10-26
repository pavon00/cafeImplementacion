package libreria;

import libreria.threader.port.EntryPort;
import libreria.threader.port.ExitPort;
import libreria.threader.port.Port;
import libreria.threader.port.SolPort;

public class Connector {
	
	public enum Tipo {
		Entrada, Salida, Sol;
	}
	
	private Port port;

	public Connector(Tipo t, String ruta) {
		
		switch (t) {
		case Entrada:
			this.port = new EntryPort(ruta);
			this.port.setConnector(this);
			break;
		case Salida:
			this.port = new ExitPort(ruta);
			this.port.setConnector(this);
			break;
		case Sol:
			this.port = new SolPort(ruta);
			this.port.setConnector(this);
			break;

		default:
			break;
		}

	}


	public Port getPort() {
		return port;
	}

	public void setPort(Port p) {
		this.port = p;
	}

}

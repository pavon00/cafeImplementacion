package libreria;

import libreria.port.EntryPort;
import libreria.port.ExitPort;
import libreria.port.SolPort;

public class Application {

	public enum Tipo {
		Entrada, Salida, Sol;
	}

	private Connector c;

	public Application(Tipo t, String rutaInput, String rutaOutput) {

		switch (t) {
		case Entrada:
			c = new Connector(this, new EntryPort(rutaInput));
			break;
		case Salida:
			c = new Connector(this, new ExitPort(rutaOutput));
			break;
		case Sol:
			c = new Connector(this, new SolPort(rutaInput, rutaOutput));
			break;

		default:
			break;
		}

	}

	public Connector getConnector() {
		return c;
	}

	public void setConnector(Connector c) {
		this.c = c;
	}

}

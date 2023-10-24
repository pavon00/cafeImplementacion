package libreria;

import libreria.port.EntryPort;
import libreria.port.ExitPort;
import libreria.port.SolPort;

public class Application {

	public enum Tipo {
		Entrada, Salida, Sol;
	}

	private Connector c;
	private String rutaInput, rutaOutput;

	public Application(Tipo t, String rutaInput, String rutaOutput) {
		
		this.setRutaInput(rutaInput);
		this.setRutaOutput(rutaOutput);
		
		switch (t) {
		case Entrada:
			c = new Connector(this, new EntryPort());
			break;
		case Salida:
			c = new Connector(this, new ExitPort());
			break;
		case Sol:
			c = new Connector(this, new SolPort());
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

	public String getRutaInput() {
		return rutaInput;
	}

	public void setRutaInput(String rutaInput) {
		this.rutaInput = rutaInput;
	}

	public String getRutaOutput() {
		return rutaOutput;
	}

	public void setRutaOutput(String rutaOutput) {
		this.rutaOutput = rutaOutput;
	}

}

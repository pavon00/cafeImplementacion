package libreria;

import libreria.threader.ThreaderAdapter;
import libreria.threader.port.FactoryEntryPort;
import libreria.threader.port.FactoryExitPort;
import libreria.threader.port.FactorySolPort;
import libreria.threader.port.Port;

public class Connector {

	public interface ConnectorFuncion {
		void ejecutar();
	}

	public enum Tipo {
		Entrada, Salida, Sol;
	}

	private Port port;
	private ConnectorFuncion funcion;
	private FactoryEntryPort factoryEntryPort;
	private FactoryExitPort factoryExitPort;
	private FactorySolPort factorySolPort;

	public Connector(Tipo t, String ruta) {
		switch (t) {
		case Entrada:
			factoryEntryPort = new FactoryEntryPort();
			this.port = factoryEntryPort.crear(this, ruta);
			this.port.setConnector(this);
			break;
		case Salida:
			factoryExitPort = new FactoryExitPort();
			this.port = factoryExitPort.crear(this, ruta);
			this.port.setConnector(this);
			break;
		case Sol:
			factorySolPort = new FactorySolPort();
			this.port = factorySolPort.crear(this, ruta);
			this.port.setConnector(this);
			break;

		default:
			break;
		}

	}

	public void ejecutar() {
		this.port.ejecutar();
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

	public ConnectorFuncion getFuncion() {
		return funcion;
	}

	public void setFuncion(ConnectorFuncion funcion) {
		this.funcion = funcion;
	}

}

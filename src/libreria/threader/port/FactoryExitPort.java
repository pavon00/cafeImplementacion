package libreria.threader.port;

import libreria.Connector;

public class FactoryExitPort implements FactoryPort {

	@Override
	public ExitPort crear(Connector con, String ruta) {
		return new ExitPort(con, ruta);
	}

}

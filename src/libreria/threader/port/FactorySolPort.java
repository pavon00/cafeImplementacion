package libreria.threader.port;

import libreria.Connector;

public class FactorySolPort implements FactoryPort {

	@Override
	public SolPort crear(Connector con, String ruta) {
		return new SolPort(con, ruta);
	}

}

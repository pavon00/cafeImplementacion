package libreria.threader.port;

import libreria.Connector;

public class FactoryExitPort implements FactoryPort {

	@Override
	public ExitPort crear(Connector con) {
		return new ExitPort(con);
	}

}

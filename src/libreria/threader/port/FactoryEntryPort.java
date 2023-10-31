package libreria.threader.port;

import libreria.Connector;

public class FactoryEntryPort implements FactoryPort {

	@Override
	public EntryPort crear(Connector con, String ruta) {
		return new EntryPort(con);
	}

}

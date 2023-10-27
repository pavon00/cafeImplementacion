package libreria.threader.port;

public class FactoryEntryPort implements FactoryPort{

	@Override
	public EntryPort crear(String ruta) {
		return new EntryPort(ruta);
	}
	
}

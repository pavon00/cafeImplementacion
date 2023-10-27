package libreria.threader.port;

public class FactoryExitPort implements FactoryPort{

	@Override
	public ExitPort crear(String ruta) {
		return new ExitPort(ruta);
	}
	
}

package libreria.threader.port;

public class FactorySolPort implements FactoryPort{

	@Override
	public SolPort crear(String ruta) {
		return new SolPort(ruta);
	}
	
}

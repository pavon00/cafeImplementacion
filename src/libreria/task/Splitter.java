package libreria.task;

/*
 * Divide un mensaje de entrada formado por una lista de elementos en tantos mensajes como elementos tenga
 * Entradas: 1, Salidas: 1
 * 
*/

public class Splitter extends Transformer{
	
	private String xPathExpression;
	
	Splitter(String xPathExpression){
		this.setxPathExpression(xPathExpression);
	}

	@Override
	public void run() {
		//esperar a los nodos de entrada
		super.run();
		
		
	}

	public String getxPathExpression() {
		return xPathExpression;
	}

	public void setxPathExpression(String xPathExpression) {
		this.xPathExpression = xPathExpression;
	}
	
}

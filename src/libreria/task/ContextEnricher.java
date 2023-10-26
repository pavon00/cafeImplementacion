package libreria.task;

/*
 * Añade contenido al cuerpo del mensaje de entrada a partir de la información de contexto ofrecida en la entrada “contexto”
 * Entradas: 2 (contexto y entrada), Salidas: 1
 * 
*/

public class ContextEnricher extends Modifier {

	private String buffer;

	@Override
	public void setBufferString(String bufferAux) {
		this.buffer = bufferAux;
	}
	
	@Override
	public String getBuffer() {
		// TODO Auto-generated method stub
		return this.buffer;
	}

}

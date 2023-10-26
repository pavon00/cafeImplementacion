package libreria.task;

import java.util.ArrayList;

import libreria.Port;
import libreria.Slot;
import libreria.Task;
import libreria.Util;

/*
 * Divide un mensaje de entrada formado por una lista de elementos en tantos mensajes como elementos tenga
 * Entradas: 1, Salidas: 1
 * 
*/

public class Splitter extends Transformer{
	
	private String xPathExpression;
	private String buffer;
	
	public Splitter(String xPathExpression){
		this.setxPathExpression(xPathExpression);
	}

	@Override
	public void run() {
		//esperar a los nodos de entrada
		super.run();
		ArrayList<String> bufferAux = null;
		try {
			bufferAux = Util.splitXmlStringToElement(this.getBuffer(), xPathExpression);
			System.out.println(bufferAux);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		if (bufferAux != null) {
			for (Slot slot : this.getSlotsSalida()) {
				for (String string : bufferAux) {
					Task t = (Task) slot.getTaskSalida();
					Port p = (Port) slot.getPortSalida();
					if (t != null) {
						t.setBufferString(string);
					}
					if (p != null) {
						p.setBufferString(string);
					}
				}
			}
		}
	}
	
	@Override
	public void setBufferString(String buffer) {
		// TODO Auto-generated method stub
		this.buffer = buffer;
	}
	
	@Override
	public String getBuffer() {
		// TODO Auto-generated method stub
		return this.buffer;
	}

	public String getxPathExpression() {
		return xPathExpression;
	}

	public void setxPathExpression(String xPathExpression) {
		this.xPathExpression = xPathExpression;
	}
	
}

package libreria.threader.task;

import java.util.ArrayList;

import libreria.Process;
import libreria.Slot;
import libreria.Util;

/*
 * Divide un mensaje de entrada formado por una lista de elementos en tantos mensajes como elementos tenga
 * Entradas: 1, Salidas: 1
 * 
*/

public class Splitter extends Task{

	private String buffer;
	private ArrayList<Slot> slotsEntrada, slotsSalida;
	private String xPathExpression;
	
	public Splitter(String xPathExpression){
		this.setxPathExpression(xPathExpression);
		this.slotsEntrada = new ArrayList<Slot>();
		this.slotsSalida = new ArrayList<Slot>();
	}

	
	@Override
	public void realizarAccion() {
		//esperar a los nodos de entrada
		if (!slotsEntrada.isEmpty() && !slotsSalida.isEmpty()) {
			try {
				esperarNodosEntrada();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Salir de espera, buffer: "+this.getBufferString());
			if (Process.ESPERAR) {
				try {
					sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			ArrayList<String> bufferAux = null;
			try {
				bufferAux = Util.splitXmlStringToElement(this.getBufferString(), xPathExpression);
				System.out.println(bufferAux);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			if (bufferAux != null) {
				for (Slot slot : this.slotsSalida) {
					for (String string : bufferAux) {
						slot.setBufferString(string);
					}
				}
			}
		}
		
	}
	
	public void esperarNodosEntrada() throws InterruptedException {
		for (Slot slotEntrada : slotsEntrada) {
			slotEntrada.esperar();
		}
	}
	
	@Override
	public void setSlotEntrada(Slot s) {
		this.slotsEntrada.add(s);
		
	}

	@Override
	public void setSlotSalida(Slot s) {
		this.slotsSalida.add(s);
	}
	
	@Override
	public void setBufferString(String buffer) {
		// TODO Auto-generated method stub
		this.buffer = buffer;
	}
	
	@Override
	public String getBufferString() {
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

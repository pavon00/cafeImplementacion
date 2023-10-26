package libreria.threader.task;

import java.util.ArrayList;

import libreria.Process;
import libreria.Slot;

/*
 * Añade contenido al cuerpo del mensaje de entrada a partir de la información de contexto ofrecida en la entrada “contexto”
 * Entradas: 2 (contexto y entrada), Salidas: 1
 * 
*/

public class ContextEnricher extends Modifier {

	private String buffer;
	private ArrayList<Slot> slotsEntrada, slotsSalida;
	
	public ContextEnricher() {
		this.slotsEntrada = new ArrayList<Slot>();
		this.slotsSalida = new ArrayList<Slot>();
	}

	@Override
	public void setBufferString(String bufferAux) {
		this.buffer = bufferAux;
	}
	
	@Override
	public String getBufferString() {
		// TODO Auto-generated method stub
		return this.buffer;
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
		}
		if (Process.ESPERAR) {
			try {
				sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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

}

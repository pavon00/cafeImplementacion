package libreria.threader.task;

import java.util.ArrayList;

import libreria.Process;
import libreria.Slot;

/*
 * Distribuye los mensajes de entrada hacia una o más salidas en función de un criterio
 * Entradas: 1, Salidas: n
 * 
*/

public class Distributor extends Task{

	private ArrayList<String> buffers;
	private ArrayList<Slot> slotsSalida;
	private Slot slotEntrada;
	
	public Distributor(){
		this.slotsSalida = new ArrayList<Slot>();
		this.buffers = new ArrayList<String>();
	}


	@Override
	public void realizarAccion() {
		// esperar a los nodos de entrada
		if (slotEntrada!=null && !slotsSalida.isEmpty()) {
			try {
				esperarNodosEntrada();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Salir de espera, buffer: " + this.getBufferString());
			if (Process.ESPERAR) {
				try {
					sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void esperarNodosEntrada() throws InterruptedException {
		slotEntrada.esperar();
	}

	@Override
	public void setBufferString(String bufferAux) {
		this.buffers.add(bufferAux);
	}

	@Override
	public String getBufferString() {
		String aux = "";
		for (String string : buffers) {
			aux = aux + string;
		}
		return aux;
	}

	@Override
	public void setSlotEntrada(Slot s) {
		this.slotEntrada = s;
	}

	@Override
	public void setSlotSalida(Slot s) {
		this.slotsSalida.add(s);
	}

}

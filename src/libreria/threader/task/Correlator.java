package libreria.threader.task;

import java.util.ArrayList;

import libreria.Process;
import libreria.Slot;

/*
 * Correlaciona los mensajes en sus distintas entradas y los pone ordenados en sus respectivas salidas
 * Entradas: n,  Salidas: n (mismo número)
 * 
*/

public class Correlator extends Task {

	private ArrayList<String> buffers;
	private ArrayList<Slot> slotsEntrada, slotsSalida;

	public Correlator() {
		this.slotsEntrada = new ArrayList<Slot>();
		this.slotsSalida = new ArrayList<Slot>();
		this.buffers = new ArrayList<String>();
	}

	@Override
	public void realizarAccion() {
		// esperar a los nodos de entrada
		if (!slotsEntrada.isEmpty() && !slotsSalida.isEmpty()) {
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
		for (Slot s : slotsEntrada) {
			s.esperar();
		}
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
		this.slotsEntrada.add(s);
	}

	@Override
	public void setSlotSalida(Slot s) {
		this.slotsSalida.add(s);
	}

}

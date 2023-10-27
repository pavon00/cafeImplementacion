package libreria.threader.task;

import java.util.ArrayList;
import libreria.Process;
import libreria.Slot;

/*
 * Correlaciona los mensajes en sus distintas entradas y los pone ordenados en sus respectivas salidas
 * Entradas: n,  Salidas: n (mismo número)
 * 
 * Hecho
 * 
*/

public class Correlator extends Task {

	private ArrayList<String> buffers;
	private ArrayList<Slot> slotsEntrada, slotsSalida;
	private ArrayList<Integer> nSlotEntrada;

	public Correlator() {
		this.slotsEntrada = new ArrayList<Slot>();
		this.slotsSalida = new ArrayList<Slot>();
		this.nSlotEntrada = new ArrayList<Integer>();
		this.buffers = new ArrayList<String>();
	}

	@Override
	public void realizarAccion() {
		this.setEjecutado(true);
		// esperar a los nodos de entrada
		if (!slotsEntrada.isEmpty() && !slotsSalida.isEmpty()) {
			try {
				esperarNodosEntrada();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("---  "+this.getClass()+"  -----Salir de espera, buffer: " + this.getBufferString());
			if (Process.ESPERAR) {
				try {
					sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		for (int i = 0; i < buffers.size(); i++) {
			int nSlot = nSlotEntrada.get(i);
			Slot slot = this.slotsSalida.get(nSlot);
			slot.setBufferString(buffers.get(i), slot);
		}
		
	}
	
	public void esperarNodosEntrada() throws InterruptedException {
		for (Slot s : slotsEntrada) {
			s.esperar();
		}
	}

	@Override
	public void setBufferString(String bufferAux, Slot s) {
		this.buffers.add(bufferAux);
		this.nSlotEntrada.add(this.slotsEntrada.indexOf(s));
	}

	@Override
	public String getBufferString() {
		String aux = "";
		for (int i = 0; i < buffers.size(); i++) {
			aux = aux + buffers.get(i) + " posicion: "+ nSlotEntrada.get(i)+ " ";
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

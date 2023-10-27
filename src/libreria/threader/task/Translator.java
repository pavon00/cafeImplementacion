package libreria.threader.task;

import libreria.Process;
import libreria.Slot;

/*
 * Transforma el cuerpo de un mensaje de un esquema a otro
 * Entradas: 1, Salidas: 1
 * 
*/

public class Translator extends Task{

	private String buffer;
	private Slot slotEntrada, slotSalida;


	@Override
	public void realizarAccion() {
		// esperar a los nodos de entrada
		if (slotEntrada != null && slotSalida != null) {
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
	public void setSlotEntrada(Slot s) {
		this.slotEntrada=s;

	}

	@Override
	public void setSlotSalida(Slot s) {
		this.slotSalida= s;
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
}

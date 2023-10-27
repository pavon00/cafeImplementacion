package libreria.threader.task;

import java.util.ArrayList;

import libreria.Process;
import libreria.Slot;

/*
 * A�ade contenido al cuerpo del mensaje de entrada a partir de la informaci�n de contexto ofrecida en la entrada �contexto�
 * Entradas: 2 (contexto y entrada), Salidas: 1
 * Hecho
 * 
*/

public class ContextEnricher extends Task {

	private ArrayList<String> buffersEntrada, buffersContexto;
	private Slot slotEntrada, slotContexto, slotSalida;

	public ContextEnricher() {
		this.buffersEntrada = new ArrayList<String>();
		this.buffersContexto = new ArrayList<String>();
	}

	@Override
	public void setBufferString(String bufferAux, Slot s) {
		if (s.equals(slotEntrada)) {
			buffersEntrada.add(bufferAux);
		}
		if (s.equals(slotContexto)) {
			buffersContexto.add(bufferAux);
		}
	}

	@Override
	public String getBufferString() {
		String aux1 = "";
		for (String string : buffersEntrada) {
			aux1 = aux1 + string;
		}
		String aux2 = "";
		for (String string : buffersContexto) {
			aux2 = aux2 + string;
		}
		return aux1 + aux2;
	}

	@Override
	public void realizarAccion() {
		this.setEjecutado(true);
		// esperar a los nodos de entrada
		if (slotEntrada != null && slotContexto != null && slotSalida != null) {
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

		for (int i = 0; i < buffersContexto.size(); i++) {
			String contextBuff = buffersContexto.get(i);
			String entradaBuff = buffersEntrada.get(i);
			String buff = anyadirMensajeContextoACuerpo(contextBuff, entradaBuff);
			slotSalida.setBufferString(buff, slotSalida);
		}

	}

	public String anyadirMensajeContextoACuerpo(String mensaje, String xml) {
		int indice = xml.indexOf('>');
		String primeraParte = xml.substring(0, indice + 1);
		String segundaParte = xml.substring(indice + 1);
		return primeraParte + mensaje + segundaParte;
	}

	public void esperarNodosEntrada() throws InterruptedException {
		slotEntrada.esperar();
		slotContexto.esperar();
	}

	@Override
	public void setSlotEntrada(Slot s) {
		if (slotEntrada != null) {
			slotContexto = s;
		} else {
			slotEntrada = s;
		}
	}

	@Override
	public void setSlotSalida(Slot s) {
		this.slotSalida = s;
	}

}

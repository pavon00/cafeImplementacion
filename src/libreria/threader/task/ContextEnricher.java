package libreria.threader.task;

import java.util.ArrayList;

import libreria.Slot;

/*
 * Añade contenido al cuerpo del mensaje de entrada a partir de la información de contexto ofrecida en la entrada “contexto”
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
	public boolean sePuedeEjecutar() {
		// TODO Auto-generated method stub
		return slotEntrada != null && slotContexto != null;
	}
	
	@Override
	public ArrayList<Slot> getSlotsSalida() {
		// TODO Auto-generated method stub
		ArrayList<Slot> aux = new ArrayList<Slot>();
		aux.add(this.slotSalida);
		return aux;
	}

	@Override
	public ArrayList<Slot> getSlotsEntrada() {
		// TODO Auto-generated method stub
		ArrayList<Slot> aux = new ArrayList<Slot>();
		aux.add(slotEntrada);
		aux.add(slotContexto);
		return aux;
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
		super.realizarAccion();
		for (int i = 0; i < buffersContexto.size(); i++) {
			String contextBuff = buffersContexto.get(i);
			String entradaBuff = buffersEntrada.get(i);
			String buff = anyadirMensajeContextoACuerpo(contextBuff, entradaBuff);
			if (buff != null) {
				slotSalida.setBufferString(buff, slotSalida);
			}
		}
		this.buffersEntrada = new ArrayList<String>();
		this.buffersContexto = new ArrayList<String>();

	}

	public String anyadirMensajeContextoACuerpo(String mensaje, String xml) {
		if (mensaje.isEmpty() || xml.isEmpty()) {
			return null;
		}
		int indice = xml.indexOf('>');
		String primeraParte = xml.substring(0, indice + 1);
		String segundaParte = xml.substring(indice + 1);
		return primeraParte + mensaje + segundaParte;
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
	

	@Override
	public boolean nodosEntradaHanMandadoMensaje() {
		return this.isEntradaMensaje();
	}

}

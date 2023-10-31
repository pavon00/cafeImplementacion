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

	public synchronized ArrayList<String> getBuffersEntrada() {
		return buffersEntrada;
	}

	public synchronized void setBuffersEntrada(ArrayList<String> buffersEntrada) {
		this.buffersEntrada = buffersEntrada;
	}

	public synchronized ArrayList<String> getBuffersContexto() {
		return buffersContexto;
	}

	public synchronized void setBuffersContexto(ArrayList<String> buffersContexto) {
		this.buffersContexto = buffersContexto;
	}

	private Slot slotEntrada, slotContexto, slotSalida;

	public ContextEnricher() {
		this.buffersEntrada = new ArrayList<String>();
		this.buffersContexto = new ArrayList<String>();
	}

	@Override
	public void setBufferString(String bufferAux, Slot s) {
		if (s.equals(slotEntrada)) {
			getBuffersEntrada().add(bufferAux);
		}
		if (s.equals(slotContexto)) {
			getBuffersContexto().add(bufferAux);
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
		for (String string : getBuffersEntrada()) {
			aux1 = aux1 + string;
		}
		String aux2 = "";
		for (String string : getBuffersContexto()) {
			aux2 = aux2 + string;
		}
		return aux1 + aux2;
	}

	@Override
	public void realizarAccion() {
		super.realizarAccion();
		for (int i = 0; i < getBuffersContexto().size(); i++) {
			String contextBuff = getBuffersContexto().get(i);
			String entradaBuff = getBuffersEntrada().get(i);
			String buff = anyadirMensajeContextoACuerpo(contextBuff, entradaBuff);
			if (buff != null) {
				slotSalida.setBufferString(buff, slotSalida);
			}
		}

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

	@Override
	public void clearBuffer() {
		// TODO Auto-generated method stub
		setBuffersEntrada(new ArrayList<String>());
		setBuffersContexto(new ArrayList<String>());
	}

}

package libreria.threader.task;

import libreria.Slot;

/*
 * Transforma el cuerpo de un mensaje de un esquema a otro
 * Entradas: 1, Salidas: 1
 * 
*/

public class Translator extends Task {

	private String buffer;
	private Slot slotEntrada, slotSalida;
	private String addString;
	
	public Translator(String addString) {
		this.addString = addString;
	}

	@Override
	public void realizarAccion() {
		super.realizarAccion();
		String buff = anyadirMensajeContextoACuerpo(addString, this.getBufferString());
		slotSalida.setBufferString(buff, slotSalida);
	}
	
	public String anyadirMensajeContextoACuerpo(String mensaje, String xml) {
		int indice = xml.indexOf('>');
		String primeraParte = xml.substring(0, indice + 1);
		String segundaParte = xml.substring(indice + 1);
		return primeraParte + mensaje + segundaParte;
	}

	@Override
	public void esperarNodosEntrada() throws InterruptedException {
		slotEntrada.esperar();
	}

	@Override
	public void setSlotEntrada(Slot s) {
		this.slotEntrada = s;

	}

	@Override
	public void setSlotSalida(Slot s) {
		this.slotSalida = s;
	}

	@Override
	public void setBufferString(String buffer, Slot s) {
		// TODO Auto-generated method stub
		this.buffer = buffer;
	}

	@Override
	public String getBufferString() {
		// TODO Auto-generated method stub
		return this.buffer;
	}
}

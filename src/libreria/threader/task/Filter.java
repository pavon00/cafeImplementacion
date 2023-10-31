package libreria.threader.task;

import java.util.ArrayList;

import libreria.Slot;
import libreria.Util;

/*
 * Distribuye los mensajes de entrada hacia las salidas
 * Entradas: 1, Salidas: n
 * 
 * Hecho
 * 
*/

public class Filter extends Task {

	private ArrayList<String> buffers;
	private ArrayList<Slot> slotsSalida;
	private Slot slotEntrada;
	private String nodoFiltrar, valorEsperado;

	public Filter(String nodoFiltrar, String valorEsperado) {
		this.nodoFiltrar = nodoFiltrar;
		this.valorEsperado = valorEsperado;
		setBuffers(new ArrayList<String>());
		this.slotsSalida = new ArrayList<Slot>();
	}

	@Override
	public boolean sePuedeEjecutar() {
		// TODO Auto-generated method stub
		return slotEntrada != null;
	}

	@Override
	public ArrayList<Slot> getSlotsEntrada() {
		// TODO Auto-generated method stub
		ArrayList<Slot> aux = new ArrayList<Slot>();
		aux.add(slotEntrada);
		return aux;
	}

	@Override
	public ArrayList<Slot> getSlotsSalida() {
		// TODO Auto-generated method stub
		return this.slotsSalida;
	}

	@Override
	public void setBufferString(String buffer, Slot s) {
		// System.out.println("elemento: " + buffer);
		ArrayList<String> buffersAux = getBuffers();
		buffersAux.add(buffer);
		setBuffers(buffersAux);
	}

	public synchronized ArrayList<String> getBuffers() {
		return buffers;
	}

	public synchronized void setBuffers(ArrayList<String> buffers) {
		this.buffers = buffers;
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
	public void realizarAccion() {
		super.realizarAccion();
		for (Slot slot : this.slotsSalida) {
			for (String string : buffers) {
				if (string.contains(this.nodoFiltrar)) {
					String valor = Util.obtenerContenido(string, this.nodoFiltrar);
					if (valor.equals(this.valorEsperado)) {
						slot.setBufferString(string, slot);
					}
				}
			}
		}

	}

	@Override
	public void setSlotEntrada(Slot s) {
		this.slotEntrada = s;

	}

	@Override
	public void setSlotSalida(Slot s) {
		this.slotsSalida.add(s);
	}

	@Override
	public boolean nodosEntradaHanMandadoMensaje() {
		return this.isEntradaMensaje();
	}

	@Override
	public void clearBuffer() {
		// TODO Auto-generated method stub
		setBuffers(new ArrayList<String>());
	}

	public String getNodoFiltrar() {
		return nodoFiltrar;
	}

	public void setNodoFiltrar(String nodoFiltrar) {
		this.nodoFiltrar = nodoFiltrar;
	}

	public String getValorEsperado() {
		return valorEsperado;
	}

	public void setValorEsperado(String valorEsperado) {
		this.valorEsperado = valorEsperado;
	}
}

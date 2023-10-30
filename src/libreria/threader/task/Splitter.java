package libreria.threader.task;

import java.util.ArrayList;

import libreria.Slot;
import libreria.Util;

/*
 * Divide un mensaje de entrada formado por una lista de elementos en tantos mensajes como elementos tenga
 * Entradas: 1, Salidas: 1
 * 
 * Hecho
 * 
*/

public class Splitter extends Task {

	private String buffer;
	private Slot slotEntrada, slotSalida;
	private String xPathExpression;

	public Splitter(String xPathExpression) {
		this.setxPathExpression(xPathExpression);
	}

	@Override
	public void realizarAccion() {
		super.realizarAccion();
		ArrayList<String> bufferAux = null;
		try {
			bufferAux = Util.splitXmlStringToElement(this.getBufferString(), xPathExpression);
			int nElements = Util.splitXmlStringGetNElements(this.getBufferString(), xPathExpression);
			setIdProceso(nElements);
			System.out.println(bufferAux);
			for (String string : bufferAux) {
				slotSalida.setBufferString(string, slotSalida);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setIdProceso(int nElements) {
		this.getProcess().getListaSplitNElements().add(nElements);
	}
	
	@Override
	public ArrayList<Slot> getSlotsSalida() {
		// TODO Auto-generated method stub
		ArrayList<Slot> aux = new ArrayList<Slot>();
		aux.add(this.slotSalida);
		return aux;
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

	public String getxPathExpression() {
		return xPathExpression;
	}

	public void setxPathExpression(String xPathExpression) {
		this.xPathExpression = xPathExpression;
	}
	

	@Override
	public boolean nodosEntradaHanMandadoMensaje() {
		return this.isEntradaMensaje();
	}

}

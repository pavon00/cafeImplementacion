package libreria;

import java.util.ArrayList;

public abstract class Task {

	private Process p;
	private ArrayList<Slot> slotsEntrada, slotsSalida;
	private boolean terminado;
	
	Task(){
		this.p = Process.getInstance();
		this.slotsEntrada = new ArrayList<Slot>();
		this.slotsSalida = new ArrayList<Slot>();
		this.setTerminado(false);
	}
	
	public void anyadirASlotsEntrada(Slot... elementos) {
        this.slotsEntrada = new ArrayList<Slot>();
        for (Slot elemento : elementos) {
        	slotsEntrada.add(elemento);
        	elemento.setTaskSalida(this);
        }
    }
	
	public void anyadirASlotsSalida(Slot... elementos) {
        this.slotsSalida = new ArrayList<Slot>();
        for (Slot elemento : elementos) {
        	slotsSalida.add(elemento);
        	elemento.setTaskEntrada(this);
        }
    }
	
	public void anyadirSlotEntrada(Slot s) {
		this.slotsEntrada.add(s);
	}
	
	public void anyadirSlotSalida(Slot s) {
		this.slotsSalida.add(s);
	}
	
	public boolean eliminarSlotEntrada(Slot s) {
		return this.slotsEntrada.remove(s);
	}
	
	public boolean eliminarSlotSalida(Slot s) {
		return this.slotsSalida.remove(s);
	}

	public Process getProcess() {
		return p;
	}

	public void setProcess(Process p) {
		this.p = p;
	}

	public ArrayList<Slot> getSlotsEntrada() {
		return slotsEntrada;
	}

	public ArrayList<Slot> getSlotsSalida() {
		return slotsSalida;
	}

	public void setslotsEntrada(ArrayList<Slot> listaSlots) {
		this.slotsEntrada = listaSlots;
	}

	public void setslotsSalida(ArrayList<Slot> listaSlots) {
		this.slotsSalida = listaSlots;
	}

	public boolean isTerminado() {
		return terminado;
	}

	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}
	
}

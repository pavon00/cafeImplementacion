package libreria;

import java.util.ArrayList;

public abstract class Task extends Thread {

	private Process p;
	private ArrayList<Slot> slotsEntrada, slotsSalida;
	private boolean terminado;
	private String buffer;

	public Task() {
		this.buffer = "";
		this.p = Process.getInstance();
		this.slotsEntrada = new ArrayList<Slot>();
		this.slotsSalida = new ArrayList<Slot>();
		this.setTerminado(false);
	}

	@Override
	public void run() {
		//esperar a los nodos de entrada
		if (!slotsEntrada.isEmpty() && !slotsSalida.isEmpty()) {
			try {
				esperarNodosEntrada();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Salir de espera, buffer: "+this.getBuffer());
		}
		if (Process.ESPERAR) {
			try {
				sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void esperarNodosEntrada() throws InterruptedException {
		for (Slot slotEntrada : slotsEntrada) {
			slotEntrada.esperar();
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

	public String getBuffer() {
		return buffer;
	}

	public void setBuffer(String buffer) {
		this.buffer = buffer;
	}

}

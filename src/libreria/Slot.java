package libreria;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Slot {

	private Ejecutable portEntrada, portSalida, taskEntrada, taskSalida;
	
	public void ejecutar() {
		if (portEntrada != null) {
			portEntrada.run();
		}
		if (portSalida != null) {
			portSalida.run();
		}
		if (taskSalida != null) {
			taskSalida.run();
		}
	}
	
	public boolean hayPortSalida() {
		return portSalida != null;
	}
	
	public boolean hayTaskSalida() {
		return taskSalida != null;
	}
	
	public void esperar() throws InterruptedException {
		if (portEntrada !=null) {
			portEntrada.join();
		}
		if (taskEntrada != null) {
			taskEntrada.join();
		}
	}

	Slot(Port portEntrada, Task taskSalida) throws ParserConfigurationException, SAXException, IOException {
		this.setPortEntrada(portEntrada);
		this.setTaskSalida(taskSalida);
	}

	Slot(Task taskEntrada, Task taskSalida) {
		this.setTaskEntrada(taskEntrada);
		this.setTaskSalida(taskSalida);
	}

	Slot(Task taskEntrada, Port portSalida) {
		this.setTaskEntrada(taskEntrada);
		this.setPortSalida(portSalida);
	}

	public Ejecutable getPortEntrada() {
		return portEntrada;
	}

	private void setPortEntrada(Port portEntrada) {
		this.portEntrada = portEntrada;
		portEntrada.setSlotSalida(this);
	}

	public Ejecutable getPortSalida() {
		return portSalida;
	}

	private void setPortSalida(Port portSalida) {
		this.portSalida = portSalida;
		portSalida.setSlotEntrada(this);
	}

	public Ejecutable getTaskEntrada() {
		return taskEntrada;
	}

	private void setTaskEntrada(Task taskEntrada) {
		this.taskEntrada = taskEntrada;
		taskEntrada.anyadirSlotSalida(this);
	}

	public Ejecutable getTaskSalida() {
		return taskSalida;
	}

	private void setTaskSalida(Task taskSalida) {
		this.taskSalida = taskSalida;
		taskSalida.anyadirSlotEntrada(this);
	}

}

package libreria;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Slot {

	private Port portEntrada, portSalida;
	private Task taskEntrada, taskSalida;
	
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

	public Port getPortEntrada() {
		return portEntrada;
	}

	private void setPortEntrada(Port portEntrada) {
		this.portEntrada = portEntrada;
		portEntrada.setSlotSalida(this);
	}

	public Port getPortSalida() {
		return portSalida;
	}

	private void setPortSalida(Port portSalida) {
		this.portSalida = portSalida;
		portSalida.setSlotEntrada(this);
	}

	public Task getTaskEntrada() {
		return taskEntrada;
	}

	private void setTaskEntrada(Task taskEntrada) {
		this.taskEntrada = taskEntrada;
		taskEntrada.anyadirSlotSalida(this);
	}

	public Task getTaskSalida() {
		return taskSalida;
	}

	private void setTaskSalida(Task taskSalida) {
		this.taskSalida = taskSalida;
		taskSalida.anyadirSlotEntrada(this);
	}

}

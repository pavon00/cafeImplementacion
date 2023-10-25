package libreria;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Slot {

	private Port portEntrada, portSalida;
	private Task taskEntrada, taskSalida;

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
	}

	public Port getPortSalida() {
		return portSalida;
	}

	private void setPortSalida(Port portSalida) {
		this.portSalida = portSalida;
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

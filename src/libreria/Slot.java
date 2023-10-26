package libreria;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import libreria.threader.ThreaderAdapter;
import libreria.threader.port.Port;
import libreria.threader.task.Task;

public class Slot {

	private ThreaderAdapter entrada, salida;

	public void ejecutar() {
		if (entrada != null) {
			entrada.run();
		}
		if (salida != null) {
			if (salida.getClass().equals(Port.class)) {
				salida.run();
			}
		}
	}

	public boolean hayPortSalida() {
		if (salida != null) {
			return salida.getClass().equals(Port.class);
		}
		return false;
	}

	public boolean hayTaskSalida() {
		if (salida != null) {
			return salida.getClass().equals(Task.class);
		}
		return false;
	}

	public void esperar() throws InterruptedException {
		if (entrada != null) {
			entrada.join();
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

	public ThreaderAdapter getEntrada() {
		return entrada;
	}

	public ThreaderAdapter getSalida() {
		return salida;
	}

	private void setPortEntrada(Port portEntrada) {
		portEntrada.setSlotSalida(this);
		this.entrada = portEntrada;
	}

	private void setPortSalida(Port portSalida) {
		portSalida.setSlotEntrada(this);
		this.salida = portSalida;
	}

	private void setTaskEntrada(Task taskEntrada) {
		taskEntrada.setSlotSalida(this);
		this.entrada = taskEntrada;
	}
	
	public void setBufferString(String string) {
		this.salida.setBufferString(string);
	}

	private void setTaskSalida(Task taskSalida) {
		taskSalida.setSlotEntrada(this);
		this.salida = taskSalida;
	}

}

package libreria;

public class Slot {
	private Port portEntrada, portSalida;
	private Task taskEntrada, taskSalida;

	public Port getPortEntrada() {
		return portEntrada;
	}

	public void setPortEntrada(Port portEntrada) {
		this.portEntrada = portEntrada;
	}

	public Port getPortSalida() {
		return portSalida;
	}

	public void setPortSalida(Port portSalida) {
		this.portSalida = portSalida;
	}

	public Task getTaskEntrada() {
		return taskEntrada;
	}

	public void setTaskEntrada(Task taskEntrada) {
		this.taskEntrada = taskEntrada;
	}

	public Task getTaskSalida() {
		return taskSalida;
	}

	public void setTaskSalida(Task taskSalida) {
		this.taskSalida = taskSalida;
	}

}

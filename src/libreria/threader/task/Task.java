package libreria.threader.task;

import libreria.Process;
import libreria.threader.ThreaderAdapter;

public abstract class Task extends ThreaderAdapter {

	private Process p;
	private boolean ejecutado;

	public Task() {
		this.p = Process.getInstance();
		this.setEjecutado(false);
	}

	@Override
	public void realizarAccion() {
		this.setEjecutado(true);
		try {
			esperarNodosEntrada();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (Process.ESPERAR) {
			try {
				sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		System.out.println("---  " + this.getClass() + "  -----Salir de espera, buffer: " + this.getBufferString());

	}

	public abstract void esperarNodosEntrada() throws InterruptedException;

	public Process getProcess() {
		return p;
	}

	public void setProcess(Process p) {
		this.p = p;
	}

	public boolean isEjecutado() {
		return ejecutado;
	}

	public void setEjecutado(boolean ejecutado) {
		this.ejecutado = ejecutado;
	}

}

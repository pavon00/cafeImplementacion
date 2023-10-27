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

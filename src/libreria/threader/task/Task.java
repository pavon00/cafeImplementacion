package libreria.threader.task;

import libreria.Process;
import libreria.threader.ThreaderAdapter;

public abstract class Task extends ThreaderAdapter {

	private Process p;
	private boolean terminado;

	public Task() {
		this.p = Process.getInstance();
		this.setTerminado(false);
	}
	
	public Process getProcess() {
		return p;
	}

	public void setProcess(Process p) {
		this.p = p;
	}

	public boolean isTerminado() {
		return terminado;
	}

	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}

}

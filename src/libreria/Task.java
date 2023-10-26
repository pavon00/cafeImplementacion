package libreria;

public abstract class Task extends EjecutableAdapter {

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

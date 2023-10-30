package libreria.threader.task;

import libreria.Process;
import libreria.threader.ThreaderAdapter;

public abstract class Task extends ThreaderAdapter {

	private Process p;

	public Task() {
		this.p = Process.getInstance();
	}

	@Override
	public void realizarAccion() {
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

}

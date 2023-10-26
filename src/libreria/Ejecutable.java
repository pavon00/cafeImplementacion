package libreria;

public abstract class Ejecutable extends Thread{
	@Override
	public void run() {
		realizarAccion();
	}

	public abstract void realizarAccion();

	public abstract void setBufferString(String bufferAux);
	
}

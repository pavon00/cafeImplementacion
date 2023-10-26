package libreria.port;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import libreria.Port;
import libreria.Slot;
import libreria.Util;

public class ExitPort extends Port {

	private Slot inputSlot;
	private String ruta;
	
	public ExitPort(String ruta){
		this.ruta = ruta;
	}
	
	@Override
	public void realizarAccion() {
		// esperar slot entrada
		if (inputSlot != null) {
			try {
				esperarNodosEntrada();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.escribirFichero();
		}
	}

	private void esperarNodosEntrada() throws InterruptedException {
		if (inputSlot != null) {
			inputSlot.esperar();
		}
	}
	

	public void escribirFichero() {
		escribirFichero(ruta, this);
	}
	
	public static void escribirFichero(String ruta, Port port) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			System.out.println("ruta "+ruta);
			fichero = getFileWriteCreateFile(ruta);
			pw = new PrintWriter(fichero);
			pw.println(Util.convertDocumentToString(port.getBuffer(), "/"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Nuevamente aprovechamos el finally para
				// asegurarnos que se cierra el fichero.
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	

	private static FileWriter getFileWriteCreateFile(String ruta) throws IOException {
		File file = new File(ruta);
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter f = new FileWriter(ruta);
		return f;
	}


	public Slot getInputSlot() {
		return inputSlot;
	}

	public void setInputSlot(Slot inputSlot) {
		this.inputSlot = inputSlot;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	@Override
	public void setSlotEntrada(Slot s) {
		// TODO Auto-generated method stub
		this.setInputSlot(s);
	}

	@Override
	public void setSlotSalida(Slot s) {
		// TODO Auto-generated method stub
		
	}
	
}

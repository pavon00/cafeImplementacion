package libreria.port;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import libreria.Port;
import libreria.Slot;

public class SolPort extends Port {

	private Slot inputSlot, outputSlot;
	private String ruta;

	public SolPort(String ruta) {
		this.ruta = ruta;
	}

	@Override
	public void realizarAccion() {
		// esperar slot entrada;
		if (inputSlot != null) {
			try {
				esperarNodosEntrada();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.leerFichero();
		if (outputSlot.hayPortSalida()) {
			try {
				outputSlot.getPortSalida().setBufferString(getBufferString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (outputSlot.hayTaskSalida()) {
			try {
				outputSlot.getTaskSalida().setBufferString(getBufferString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void esperarNodosEntrada() throws InterruptedException {
		if (inputSlot != null) {
			inputSlot.esperar();
		}
	}

	private void leerFichero() {
		leerFichero(ruta, this);
	}

	public static void leerFichero(String ruta, Port port) {
		// hace uso de fichero para escribir en buffer de port
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			archivo = new File(ruta);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero
			String stringFichero = "";
			String linea = "";
			while ((linea = br.readLine()) != null)
				stringFichero = stringFichero + linea;
			port.setBufferString(stringFichero);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// En el finally cerramos el fichero, para asegurarnos
			// que se cierra tanto si todo va bien como si salta
			// una excepcion.
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public Slot getInputSlot() {
		return inputSlot;
	}

	public void setInputSlot(Slot inputSlot) {
		this.inputSlot = inputSlot;
	}

	public Slot getOutputSlot() {
		return outputSlot;
	}

	public void setOutputSlot(Slot outputSlot) {
		this.outputSlot = outputSlot;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

}

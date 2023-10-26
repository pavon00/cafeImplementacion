package libreria.port;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import libreria.Port;
import libreria.Slot;

public class EntryPort extends Port {
	private Slot outputSlot;
	private String ruta;

	public EntryPort(String ruta){
		this.ruta = ruta;
	}

	@Override
	public void realizarAccion() {
		this.leerFichero();
		outputSlot.setBufferString(getBufferString());
	}

	private void leerFichero() {
		leerFichero(ruta, this);
	}

	private static void leerFichero(String ruta, Port port) {
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
			System.out.println("Leido "+stringFichero);
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

	public Slot getSlotSalida() {
		return outputSlot;
	}


	@Override
	public void setSlotSalida(Slot s) {
		this.outputSlot = s;
	}

	@Override
	public void setSlotEntrada(Slot s) {
		// TODO Auto-generated method stub
		
	}
}

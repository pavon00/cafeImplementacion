package libreria;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Slot {
	private String ruta;
	private Port port;

	public Slot(Port p, String ruta) {
		this.port = p;
		this.ruta = ruta;
	}
	
	Slot(){
		this.port = new Port();
		this.ruta = "";
	}
	
	public void escribirBuffer() {
		this.port.setBuffer("");
	}
	
	public String leerBuffer() {
		return this.port.getBuffer();
	}

	public void escribirFichero() {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter(this.ruta);
			pw = new PrintWriter(fichero);
			pw.println(port.getBuffer());
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

	public void leerFichero() {
		// hace uso de fichero para escribir en buffer de port
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			archivo = new File(this.ruta);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero
			String stringFichero = "";
			String linea = "";
			while ((linea = br.readLine()) != null)
				stringFichero = stringFichero + linea;
			this.port.setBuffer(stringFichero);
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

	public Port getPort() {
		return port;
	}

	public void setPort(Port p) {
		this.port = p;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
}

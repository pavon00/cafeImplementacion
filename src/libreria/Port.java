package libreria;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import libreria.port.Message;

public abstract class Port {
	private Message m;
	private Connector con;
	
	public Port(Connector connector) {
		this.m = new Message(connector);
		this.con = connector;
	}

	public String getBuffer() {
		return m.getBuffer();
	}

	public void setBuffer(String m) {
		this.m.setBuffer(m);
	}

	public void leerFichero() {
		leerFichero(con.getApp().getRutaInput(), this);
	}

	public void escribirFichero() {
		escribirFichero(con.getApp().getRutaOutput(), this);
	}
	
	public static void escribirFichero(String ruta, Port port) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter(ruta);
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
			port.setBuffer(stringFichero);
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

	public Connector getConnector() {
		return con;
	}

	public void setConnector(Connector con) {
		this.con = con;
	}
	
}

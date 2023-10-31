package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import libreria.Connector;
import libreria.Slot;
import libreria.Util;

public class Barista {
	private Connector connector;

	public Barista(Connector connector) {
		// TODO Auto-generated constructor stub
		this.connector = connector;
	}

	public void procesarInformacion() {
		connector.setFuncion(() -> {
			try {
				ArrayList<String> aux = new ArrayList<String>();
				for (int i = 0; i < this.getConnector().getPort().getBuffers().size(); i++) {
					//System.out.println("ssssssssssssssssssssssssssaaaaaaaaaaaa"+this.getConnector().getPort().getBuffers().size()+this.getConnector().getPort().getBuffers().get(i));
					aux.add("<status>terminado</status>");
				}
				for (String string : aux) {
					ArrayList<Slot> slots = this.getConnector().getPort().getSlotsSalida();
					for (Slot s : slots) {
						s.setBufferString(string, s);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	public static void escribirFichero(String ruta, Barista barista) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = getFileWriteCreateFile(ruta);
			pw = new PrintWriter(fichero);
			pw.println(Util.convertDocumentToString(barista.getConnector().getPort().getBuffer(), "/"));
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

	private void leerFichero(String ruta) {
		leerFichero(ruta, this);
	}

	public static void leerFichero(String ruta, Barista barista) {
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
			barista.getConnector().getPort().setBufferString(stringFichero);
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
		return connector;
	}

	public void setConnector(Connector connector) {
		this.connector = connector;
	}

}

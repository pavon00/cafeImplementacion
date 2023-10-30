package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import libreria.Connector;
import libreria.Process;

public class Order {
	private Connector connector;

	public Order(Connector connector) {
		// TODO Auto-generated constructor stub
		this.connector = connector;
	}
	
	public void leerFichero(String ruta) {
		Process p = Process.getInstance();
		ReentrantLock lock = p.getLock();
		Condition c = p.getColaProcesos();
		try {
			lock.lock();
		} finally {
			while (p.isEjecutandoProceso()) {
				try {
					c.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			p.setEjecutandoProceso(true);
			String buffer = leerFichero(ruta, this);
			if (buffer != null) {
				this.connector.getPort().setBufferString(buffer);
				connector.notificar();
			}
			lock.unlock();
		}
	}

	private static String leerFichero(String ruta, Order order) {
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
			return stringFichero;
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
		return null;
	}


	public Connector getConnector() {
		return connector;
	}

	public void setConnector(Connector connector) {
		this.connector = connector;
	}
	
}

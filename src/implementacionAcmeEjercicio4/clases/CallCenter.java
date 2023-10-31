package implementacionAcmeEjercicio4.clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import libreria.Connector;
import libreria.Process;

public class CallCenter {
	private Connector connector;

	public CallCenter(Connector connector) {
		// TODO Auto-generated constructor stub
		this.connector = connector;
	}

	public void leerFichero(String ruta) {
		connector.setFuncion(() -> {
			Process p = Process.getInstance();
			p.setEjecutandoProceso(true);
			String buffer = leerFichero(ruta, this);
			System.out.println("Order pide las siguientes comandas: \n\n"+buffer+"\n");
			if (buffer != null) {
				this.connector.getPort().setBufferString(buffer);
				connector.notificar();
			}
		});
		connector.ejecutar();
	}

	public void terminar() {
		connector.setFuncion(() -> {
			Process p = Process.getInstance();
			p.setTerminar(true);
		});
		connector.ejecutar();
	}

	private static String leerFichero(String ruta, CallCenter order) {
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

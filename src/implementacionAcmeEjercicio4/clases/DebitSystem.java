package implementacionAcmeEjercicio4.clases;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import libreria.Connector;
import libreria.Util;

public class DebitSystem {
	private Connector connector;
	private static int contador = 1;

	public DebitSystem(Connector connector) {
		// TODO Auto-generated constructor stub
		this.connector = connector;
	}

	public void escribirFichero(String ruta) {
		connector.setFuncion(() -> {
			escribirFichero(ruta, this);
		});
	}

	public static void escribirFichero(String ruta, DebitSystem waiter) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			System.out.print("\nWaiter prepara pedido numero " + contador + ":\n\n"
					+ Util.convertDocumentToString(waiter.getConnector().getPort().getBuffer(), "/") + "\n\n\n");
			contador++;
			fichero = getFileWriteCreateFile(ruta);
			pw = new PrintWriter(fichero);
			pw.println(Util.convertDocumentToString(waiter.getConnector().getPort().getBuffer(), "/"));
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

	public Connector getConnector() {
		return connector;
	}

	public void setConnector(Connector connector) {
		this.connector = connector;
	}

}

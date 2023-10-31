package implementacionAcmeEjercicio4.clases;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import libreria.Connector;
import libreria.Slot;
import libreria.Util;

public class Aplicacion {
	private Connector connector;
	private String consoleOutputName;

	public Aplicacion(Connector connector, String consoleOutputName) {
		// TODO Auto-generated constructor stub
		this.connector = connector;
		this.consoleOutputName = consoleOutputName;
	}

	public void procesarInformacion() {
		connector.setFuncion(() -> {
			try {
				ArrayList<String> aux = new ArrayList<String>();
				System.out.println("\n" + consoleOutputName + " tiene "
						+ this.getConnector().getPort().getBuffers().size() + " llamadas:");
				for (int i = 0; i < this.getConnector().getPort().getBuffers().size(); i++) {
					System.out.println("          " + this.getConnector().getPort().getBuffers().get(i));
					String contenido = Util.obtenerContenido(this.getConnector().getPort().getBuffers().get(i), "tipo");
					if (contenido != null) {
						if (contenido.equals("privada")) {
							System.out.println("EL TIPO DE LLAMADA ES PRIVADA POR LO TANTO SE DEBE COBRAR");
							aux.add("<cobrar>si</cobrar>");
						} else {
							System.out.println("EL TIPO DE LLAMADA ES PRIVADA POR LO TANTO SE DEBE COBRAR");
							aux.add("<cobrar>no</cobrar>");
						}
					} else {
						System.out.println(
								"EL TIPO DE LLAMADA NO HA SIDO INTRODUCIDO POR EL TRABAJADOR POR LO QUE ES PRIVADA Y SE DEBE COBRAR");
						aux.add("<cobrar>si</cobrar>");
					}
				}
				System.out.println("");
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

	public static void escribirFichero(String ruta, Aplicacion barista) {
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

	public Connector getConnector() {
		return connector;
	}

	public void setConnector(Connector connector) {
		this.connector = connector;
	}

}

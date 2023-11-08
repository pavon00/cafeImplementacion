package implementacionCafe.clases;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import libreria.Connector;
import libreria.Slot;
import libreria.Util;

public class Barista {
	private Connector connector;
	private String consoleOutputName;

	public Barista(Connector connector, String consoleOutputName) {
		// TODO Auto-generated constructor stub
		this.connector = connector;
		this.consoleOutputName = consoleOutputName;
	}

	public void procesarInformacion() {
		connector.setFuncion(() -> {
			ArrayList<String> aux = new ArrayList<String>();
			System.out.println("\n" + consoleOutputName + " tiene " + this.getConnector().getPort().getBuffers().size()
					+ " comandas:");
			for (int i = 0; i < this.getConnector().getPort().getBuffers().size(); i++) {
				String bebida = Util.obtenerContenido(this.getConnector().getPort().getBuffers().get(i), "name");
				try {
					if (verificaStockBebida(bebida)) {
						aux.add("<status>bebida_servida</status>");
					} else {
						aux.add("<status>terminado</status>");
					}
				} catch (Exception e) {
					aux.add("<status>terminado</status>");
				}
			}
			System.out.println("");
			for (String string : aux) {
				ArrayList<Slot> slots = this.getConnector().getPort().getSlotsSalida();
				for (Slot s : slots) {
					s.setBufferString(string, s);
				}
			}

		});
	}

	public static Connection getConnection() throws SQLException {
		String url = "jdbc:h2:file:./src/jars/resources/data"; // Cambia esto según tu configuración
		String username = "sa"; // Usuario predeterminado de H2
		String password = ""; // Contraseña predeterminada de H2
		return DriverManager.getConnection(url, username, password);
	}

	public static boolean verificaStockBebida(String bebida) {
		try (Connection connection = getConnection()) {
			String query = "SELECT hay_stock FROM bebidas WHERE nombre = '" + bebida + "'";
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						return resultSet.getBoolean("hay_stock");
					}
				}
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
			return false;
		}
		return false;
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

	public Connector getConnector() {
		return connector;
	}

	public void setConnector(Connector connector) {
		this.connector = connector;
	}

}

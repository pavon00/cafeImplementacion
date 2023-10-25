package libreria;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import libreria.port.Message;

public abstract class Port extends Thread {
	private Message m;
	private Connector con;
	private Slot slotEntrada, slotSalida;

	@Override
	public void run() {
		// esperar slot entrada
		if (slotEntrada != null && slotSalida != null) {
			try {
				esperarNodosEntrada();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void esperarNodosEntrada() throws InterruptedException {
		if (slotEntrada != null) {
			slotEntrada.esperar();
		}
	}

	public Port(Connector connector) {
		this.m = new Message(connector);
		this.con = connector;
	}

	public String getBufferString() {
		return m.getBuffer();
	}

	public Document getBuffer() throws ParserConfigurationException, SAXException, IOException {
		return Util.convertStringToDocument(m.getBuffer());
	}

	public void setBufferString(String m) {
		this.m.setBuffer(m);
	}

	public void setBuffer(Document m) throws XPathExpressionException, ParserConfigurationException, SAXException,
			IOException, TransformerFactoryConfigurationError, TransformerException {
		this.m.setBuffer(Util.convertDocumentToString(m, "/"));
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
			System.out.println("ruta "+ruta);
			fichero = getFileWriteCreateFile(ruta);
			pw = new PrintWriter(fichero);
			pw.println(Util.convertDocumentToString(port.getBuffer(), "/"));
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

	public Connector getConnector() {
		return con;
	}

	public void setConnector(Connector con) {
		this.con = con;
	}

	public Slot getSlotEntrada() {
		return slotEntrada;
	}

	public void setSlotEntrada(Slot slotEntrada) {
		this.slotEntrada = slotEntrada;
	}

	public Slot getSlotSalida() {
		return slotSalida;
	}

	public void setSlotSalida(Slot slotSalida) {
		this.slotSalida = slotSalida;
	}

}

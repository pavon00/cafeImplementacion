package libreria.threader.port;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import libreria.Slot;
import libreria.Util;

public class SolPort extends Port {

	private Slot inputSlot, outputSlot;
	private String ruta;

	public SolPort(String ruta) {
		this.ruta = ruta;
	}

	@Override
	public void realizarAccion() {
		// this.escribirFichero();
		this.leerFichero();
		try {
			ArrayList<String> aux = Util.splitXmlStringToElement(getBufferString(), "//status");
			for (String string : aux) {
				outputSlot.setBufferString(string, outputSlot);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean sePuedeEjecutar() {
		// TODO Auto-generated method stub
		return inputSlot != null && outputSlot != null;
	}

	@Override
	public ArrayList<Slot> getSlotsEntrada() {
		// TODO Auto-generated method stub
		ArrayList<Slot> aux = new ArrayList<Slot>();
		aux.add(inputSlot);
		return aux;
	}

	public void escribirFichero() {
		escribirFichero(ruta, this);
	}

	public static void escribirFichero(String ruta, Port port) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
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

	private void leerFichero() {
		leerFichero(ruta, this);
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

	public Slot getSlotEntrada() {
		return inputSlot;
	}

	public Slot getSlotSalida() {
		return outputSlot;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	@Override
	public void setSlotEntrada(Slot s) {
		this.inputSlot = s;
	}

	@Override
	public void setSlotSalida(Slot s) {
		this.outputSlot = s;
	}

	@Override
	public void setBufferString(String string, Slot s) {
		this.setBufferString(string);

	}

	@Override
	public ArrayList<Slot> getSlotsSalida() {
		// TODO Auto-generated method stub
		ArrayList<Slot> aux = new ArrayList<Slot>();
		aux.add(this.outputSlot);
		return aux;
	}
	
	@Override
	public boolean nodosEntradaHanMandadoMensaje() {
		return this.isEntradaMensaje();
	}

}

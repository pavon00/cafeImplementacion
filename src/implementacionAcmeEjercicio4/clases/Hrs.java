package implementacionAcmeEjercicio4.clases;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import libreria.Connector;
import libreria.Slot;
import libreria.Util;

public class Hrs {

	public class Persona {
		private String numeroRegistroPersonal, nombre, departamento, email, numeroTelefonoUniversidad,
				numeroTelefonoMovil;

		public Persona(String numeroRegistroPersonal, String nombre, String departamento, String email,
				String numeroTelefonoUniversidad, String numeroTelefonoMovil) {
			this.numeroRegistroPersonal = numeroRegistroPersonal;
			this.nombre = nombre;
			this.departamento = departamento;
			this.email = email;
			this.numeroTelefonoUniversidad = numeroTelefonoUniversidad;
			this.numeroTelefonoMovil = numeroTelefonoMovil;
		}

		public String getNumeroRegistroPersonal() {
			return numeroRegistroPersonal;
		}

		public void setNumeroRegistroPersonal(String numeroRegistroPersonal) {
			this.numeroRegistroPersonal = numeroRegistroPersonal;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getDepartamento() {
			return departamento;
		}

		public void setDepartamento(String departamento) {
			this.departamento = departamento;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getNumeroTelefonoUniversidad() {
			return numeroTelefonoUniversidad;
		}

		public void setNumeroTelefonoUniversidad(String numeroTelefonoUniversidad) {
			this.numeroTelefonoUniversidad = numeroTelefonoUniversidad;
		}

		public String getNumeroTelefonoMovil() {
			return numeroTelefonoMovil;
		}

		public void setNumeroTelefonoMovil(String numeroTelefonoMovil) {
			this.numeroTelefonoMovil = numeroTelefonoMovil;
		}
	}

	private Connector connector;
	private String consoleOutputName;
	private ArrayList<Persona> listaPersonas;

	public Hrs(Connector connector, String consoleOutputName) {
		// TODO Auto-generated constructor stub
		this.connector = connector;
		this.consoleOutputName = consoleOutputName;
		this.listaPersonas = new ArrayList<Hrs.Persona>();
		this.listaPersonas
				.add(new Persona("345344", "Manolo", "Informatica", "manolo@gmail.com", "666666666", "666666666"));
		this.listaPersonas
				.add(new Persona("345343", "Antonio", "Informatica", "antonio@gmail.com", "766666666", "766666666"));
	}

	public void procesarInformacion() {
		connector.setFuncion(() -> {
			try {
				ArrayList<String> aux = new ArrayList<String>();
				System.out.println(
						"\n" + consoleOutputName + " obtiene " + this.getConnector().getPort().getBuffers().size()
								+ " llamadas privadas de las siguientes personas:");
				for (int i = 0; i < this.getConnector().getPort().getBuffers().size(); i++) {
					System.out.println("          " + this.getConnector().getPort().getBuffers().get(i));
					String numeroTelefono = Util.obtenerContenido(this.getConnector().getPort().getBuffers().get(i),
							"telefonoOrigen");
					for (Persona persona : this.getListaPersonas()) {
						if (persona.getNumeroTelefonoMovil().equals(numeroTelefono)
								|| persona.getNumeroTelefonoMovil().equals(numeroTelefono)) {

							String numeroRegistro = persona.getNumeroRegistroPersonal();
							String nombre = persona.getNombre();
							String email = persona.getEmail();

							System.out.println("     nombre: " + nombre + ", numero telefono: " + numeroTelefono
									+ ", numero registro: " + numeroRegistro + ", email: " + email);

							aux.add("<numeroRegistro>" + numeroRegistro + "</numeroRegistro><nombre>" + nombre
									+ "</nombre><email>" + email + "</email>");
						}
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

	public static void escribirFichero(String ruta, Hrs barista) {
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

	public ArrayList<Persona> getListaPersonas() {
		return listaPersonas;
	}

	public void setListaPersonas(ArrayList<Persona> listaPersonas) {
		this.listaPersonas = listaPersonas;
	}

}

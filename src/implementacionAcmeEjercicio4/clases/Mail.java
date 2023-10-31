package implementacionAcmeEjercicio4.clases;

import libreria.Connector;

public class Mail {

	private Connector connector;
	private static int contador = 1;

	public Mail(Connector connector) {
		// TODO Auto-generated constructor stub
		this.connector = connector;
	}

	public void prepararEmail() {
		connector.setFuncion(() -> {
			try {
				System.out.print(
						"\nMail realiza el email :\n\n" + this.getConnector().getPort().getBufferString() + "\n\n\n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			contador++;
		});
	}

	public Connector getConnector() {
		return connector;
	}

	public void setConnector(Connector connector) {
		this.connector = connector;
	}

}

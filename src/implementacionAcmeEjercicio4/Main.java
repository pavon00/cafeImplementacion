package implementacionAcmeEjercicio4;

import libreria.Process;
import implementacionAcmeEjercicio4.clases.Aplicacion;
import implementacionAcmeEjercicio4.clases.CallCenter;
import implementacionAcmeEjercicio4.clases.DebitSystem;
import implementacionAcmeEjercicio4.clases.Hrs;
import implementacionAcmeEjercicio4.clases.Mail;
import libreria.Connector;
import libreria.Connector.Tipo;
import libreria.threader.task.ContextEnricher;
import libreria.threader.task.Correlator;
import libreria.threader.task.Filter;
import libreria.threader.task.Replicator;
import libreria.threader.task.Splitter;
import libreria.threader.task.Translator;

public class Main {
	public static void main(String[] args) {
		Connector callCenterConnector = new Connector(Tipo.Entrada);
		Connector debitSystemConnector = new Connector(Tipo.Salida);
		Connector mailConnector = new Connector(Tipo.Salida);
		Connector aplicacionConnector = new Connector(Tipo.Sol);
		Connector hrsConnector = new Connector(Tipo.Sol);
		CallCenter callCenter = new CallCenter(callCenterConnector);
		getProcess(callCenterConnector, debitSystemConnector, mailConnector, aplicacionConnector, hrsConnector)
				.ejecutar();

		Aplicacion aplicacion = new Aplicacion(aplicacionConnector, "Aplicacion");
		aplicacion.procesarInformacion();
		Hrs hrs = new Hrs(hrsConnector, "Aplicacion");
		hrs.procesarInformacion();
		Mail mail = new Mail(mailConnector);
		mail.prepararEmail();
		DebitSystem debitSystem = new DebitSystem(debitSystemConnector);
		debitSystem.prepararPago();

		callCenter.leerFichero("callCenter.xml");

		callCenter.terminar();
		System.out.println("Terminar");
	}

	// añade los puertos de los conectores pasados por parametro y task a proceso y
	// lo devuelve
	public static Process getProcess(Connector callCenterConnector, Connector debitSystemConnector,
			Connector mailConnector, Connector aplicacionConnector, Connector hrsConnector) {
		Splitter splitter = new Splitter("//llamada");
		Replicator replicator1 = new Replicator();
		Replicator replicator2 = new Replicator();
		Replicator replicator3 = new Replicator();
		Translator translator1 = new Translator();
		Translator translator2 = new Translator();
		Translator translator3 = new Translator();
		Translator translator4 = new Translator();
		Correlator correlator1 = new Correlator();
		Correlator correlator2 = new Correlator();
		ContextEnricher contextEnricher1 = new ContextEnricher();
		ContextEnricher contextEnricher2 = new ContextEnricher();
		Filter filter = new Filter("cobrar", "si");
		Process p = Process.getInstance();
		Process.ESPERAR = false;
		/*
		 * try { p.anyadirSlot(order.getPort(), splitter); p.anyadirSlot(splitter,
		 * aggregator); p.anyadirSlot(aggregator, waiter.getPort()); } catch (Exception
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
		try {
			p.anyadirSlot(callCenterConnector.getPort(), splitter);
			p.anyadirSlot(splitter, replicator1);
			p.anyadirSlot(replicator1, translator1);
			p.anyadirSlot(replicator1, correlator1);
			p.anyadirSlot(translator1, aplicacionConnector.getPort());
			p.anyadirSlot(aplicacionConnector.getPort(), correlator1);
			p.anyadirSlot(correlator1, contextEnricher1);
			p.anyadirSlot(correlator1, contextEnricher1);
			p.anyadirSlot(contextEnricher1, filter);
			p.anyadirSlot(filter, replicator2);
			p.anyadirSlot(replicator2, translator2);
			p.anyadirSlot(replicator2, correlator2);
			p.anyadirSlot(translator2, hrsConnector.getPort());
			p.anyadirSlot(hrsConnector.getPort(), correlator2);
			p.anyadirSlot(correlator2, contextEnricher2);
			p.anyadirSlot(correlator2, contextEnricher2);
			p.anyadirSlot(contextEnricher2, replicator3);
			p.anyadirSlot(replicator3, translator3);
			p.anyadirSlot(replicator3, translator4);
			p.anyadirSlot(translator3, debitSystemConnector.getPort());
			p.anyadirSlot(translator4, mailConnector.getPort());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}

}

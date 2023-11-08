package implementacionCafe;

import libreria.Process;

import java.sql.SQLException;

import org.h2.tools.Server;

import implementacionCafe.clases.Barista;
import implementacionCafe.clases.Order;
import implementacionCafe.clases.Waiter;
import libreria.Connector;
import libreria.Connector.Tipo;
import libreria.threader.task.Aggregator;
import libreria.threader.task.ContextEnricher;
import libreria.threader.task.Correlator;
import libreria.threader.task.Distributor;
import libreria.threader.task.Merger;
import libreria.threader.task.Replicator;
import libreria.threader.task.Splitter;
import libreria.threader.task.Translator;

public class Main {
	public static void main(String[] args) throws SQLException {
		Connector orderConnector = new Connector(Tipo.Entrada);
		Connector waiterConnector = new Connector(Tipo.Salida);
		Connector BaristaColdConnector = new Connector(Tipo.Sol);
		Connector BaristaHotConnector = new Connector(Tipo.Sol);
		Server server = Server.createTcpServer().start();
		getProcess(orderConnector, waiterConnector, BaristaColdConnector, BaristaHotConnector).ejecutar();
		Order order = new Order(orderConnector);

		Waiter waiter = new Waiter(waiterConnector);
		waiter.escribirFichero("waiter.xml");
		Barista baristaHot = new Barista(BaristaHotConnector, "Barista Hot");
		Barista baristaCold = new Barista(BaristaColdConnector, "Barista Cold");
		baristaHot.procesarInformacion();
		baristaCold.procesarInformacion();

		order.leerFichero("order1.xml");
		order.leerFichero("order2.xml");
		order.leerFichero("order3.xml");
		order.leerFichero("order4.xml");
		order.leerFichero("order5.xml");
		order.leerFichero("order6.xml");
		order.leerFichero("order7.xml");
		order.leerFichero("order8.xml");
		order.leerFichero("order9.xml");

		order.terminar();

		try {
			waiterConnector.getPort().join();
			System.out.println("TERMINAR");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.stop();
	}

	//añade los puertos de los conectores pasados por parametro y task a proceso y lo devuelve
	public static Process getProcess(Connector orderConnector, Connector waiterConnector,
			Connector BaristaColdConnector, Connector BaristaHotConnector) {
		Splitter splitter = new Splitter("//drink");
		Distributor distributor = new Distributor();
		distributor.direccionar(1, "/drink[type='hot']");
		distributor.direccionar(0, "/drink[type='cold']");
		Replicator replicator1 = new Replicator();
		Replicator replicator2 = new Replicator();
		Translator translator1 = new Translator();
		Translator translator2 = new Translator();
		Correlator correlator1 = new Correlator();
		Correlator correlator2 = new Correlator();
		ContextEnricher contextEnricher1 = new ContextEnricher();
		ContextEnricher contextEnricher2 = new ContextEnricher();
		Merger merger = new Merger();
		Aggregator aggregator = new Aggregator("drinks", "cafe_order");
		Process p = Process.getInstance();
		Process.ESPERAR = false;
		/*
		 * try { p.anyadirSlot(order.getPort(), splitter); p.anyadirSlot(splitter,
		 * aggregator); p.anyadirSlot(aggregator, waiter.getPort()); } catch (Exception
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
		try {
			p.anyadirSlot(orderConnector.getPort(), splitter);
			p.anyadirSlot(splitter, distributor);

			p.anyadirSlot(distributor, replicator1);
			p.anyadirSlot(replicator1, translator1);
			p.anyadirSlot(translator1, BaristaColdConnector.getPort());
			p.anyadirSlot(replicator1, correlator1);
			p.anyadirSlot(BaristaColdConnector.getPort(), correlator1);
			p.anyadirSlot(correlator1, contextEnricher1);
			p.anyadirSlot(correlator1, contextEnricher1);
			p.anyadirSlot(contextEnricher1, merger);

			p.anyadirSlot(distributor, replicator2);
			p.anyadirSlot(replicator2, translator2);
			p.anyadirSlot(translator2, BaristaHotConnector.getPort());
			p.anyadirSlot(replicator2, correlator2);
			p.anyadirSlot(BaristaHotConnector.getPort(), correlator2);
			p.anyadirSlot(correlator2, contextEnricher2);
			p.anyadirSlot(correlator2, contextEnricher2);
			p.anyadirSlot(contextEnricher2, merger);

			p.anyadirSlot(merger, aggregator);
			p.anyadirSlot(aggregator, waiterConnector.getPort());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}

}

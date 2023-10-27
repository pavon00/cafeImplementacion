package main;

import libreria.Process;
import libreria.Connector;
import libreria.Connector.Tipo;
import libreria.threader.task.Aggregator;
import libreria.threader.task.ContextEnricher;
import libreria.threader.task.Correlator;
import libreria.threader.task.Distributor;
import libreria.threader.task.Merger;
import libreria.threader.task.Replicator;
import libreria.threader.task.Splitter;

public class Main {
	public static void main(String[] args) {
		Connector order = new Connector(Tipo.Entrada, "order1.xml");
		Connector waiter = new Connector(Tipo.Salida, "waiter.xml");
		Connector BaristaHot = new Connector(Tipo.Sol, "baristaHot.xml");
		Connector BaristaCold = new Connector(Tipo.Sol, "baristaCold.xml");
		Splitter splitter = new Splitter("//drink");
		Distributor distributor = new Distributor();
		distributor.direccionar(1, "/drink[type='hot']");
		distributor.direccionar(0, "/drink[type='cold']");
		Replicator replicator1 = new Replicator();
		Replicator replicator2 = new Replicator();
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
			p.anyadirSlot(order.getPort(), splitter);
			p.anyadirSlot(splitter, distributor);
			p.anyadirSlot(distributor, Process.crearArray(replicator1, replicator2));
			p.anyadirSlot(replicator1, BaristaHot.getPort());
			p.anyadirSlot(replicator2, BaristaCold.getPort());
			p.anyadirSlot(replicator1, correlator1);
			p.anyadirSlot(replicator2, correlator2);
			p.anyadirSlot(BaristaHot.getPort(), correlator1);
			p.anyadirSlot(BaristaCold.getPort(), correlator2);
			p.anyadirSlot(correlator1, contextEnricher1);
			p.anyadirSlot(correlator1, contextEnricher1);
			p.anyadirSlot(correlator2, contextEnricher2);
			p.anyadirSlot(correlator2, contextEnricher2);
			p.anyadirSlot(contextEnricher1, merger);
			p.anyadirSlot(contextEnricher2, merger);
			p.anyadirSlot(merger, aggregator);
			p.anyadirSlot(aggregator, waiter.getPort());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.ejecutar();
	}
}

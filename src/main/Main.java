package main;

import libreria.Process;
import libreria.Connector;
import libreria.Connector.Tipo;
import libreria.threader.task.Aggregator;
import libreria.threader.task.Distributor;
import libreria.threader.task.Splitter;

public class Main {
	public static void main(String[] args) {
		Connector order = new Connector(Tipo.Entrada, "order1.xml");
		Connector waiter = new Connector(Tipo.Salida, "waiter.xml");
		Splitter splitter = new Splitter("//drink");
		Aggregator aggregator = new Aggregator("drinks", "cafe_order");
		Distributor distributor = new Distributor();
		distributor.direccionar(0, "/drink[type='cold']");
		Process p = Process.getInstance();
		Process.ESPERAR = false;
		/*
		try {
			p.anyadirSlot(order.getPort(), splitter);
			p.anyadirSlot(splitter, aggregator);
			p.anyadirSlot(aggregator, waiter.getPort());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		try {
			p.anyadirSlot(order.getPort(), splitter);
			p.anyadirSlot(splitter, distributor);
			p.anyadirSlot(distributor, aggregator);
			p.anyadirSlot(aggregator, waiter.getPort());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.ejecutar();
	}
}

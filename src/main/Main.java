package main;

import libreria.Process;
import libreria.Connector;
import libreria.Connector.Tipo;
import libreria.threader.task.Aggregator;
import libreria.threader.task.Splitter;

public class Main {
	public static void main(String[] args) {
		Connector order = new Connector(Tipo.Entrada, "order1.xml");
		Connector waiter = new Connector(Tipo.Salida, "waiter.xml");
		Splitter splitter = new Splitter("//drink");
		Aggregator aggregator = new Aggregator("drinks", "cafe_order");
		Process p = Process.getInstance();
		Process.ESPERAR = true;
		try {
			p.anyadirSlot(order.getPort(), splitter);
			p.anyadirSlot(splitter, aggregator);
			p.anyadirSlot(aggregator, waiter.getPort());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.ejecutar();
	}
}

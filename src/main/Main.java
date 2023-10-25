package main;

import libreria.Application;
import libreria.Process;
import libreria.Application.Tipo;
import libreria.task.Aggregator;
import libreria.task.Splitter;

public class Main {
	public static void main(String[] args) {
		Application order = new Application(Tipo.Entrada, "order1.xml");
		Application waiter = new Application(Tipo.Salida, "waiter.xml");
		Splitter splitter = new Splitter("//drink");
		Aggregator aggregator = new Aggregator("cafe_order");
		Process p = Process.getInstance();
		Process.ESPERAR = false;
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

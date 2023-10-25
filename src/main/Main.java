package main;

import libreria.Application;
import libreria.Application.Tipo;

public class Main {
	public static void main(String[] args) {
		Application order = new Application(Tipo.Entrada, "order1.xml", null);
		Application waiter = new Application(Tipo.Salida, "waiter.xml", null);
		
	}
}

package libreria;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Process {
	private static Process INSTANCE;
	private ArrayList<List<Slot>> listaSlots;

	private Process() {
		listaSlots = new ArrayList<List<Slot>>();
	}

	public static Process getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Process();
		}

		return INSTANCE;
	}

	public void anyadirSlot(Port port, ArrayList<Task> tasks) {
		if (tasks != null && port != null) {
			List<Slot> slots = new ArrayList<Slot>();
			for (Task t : tasks) {
				if (t != null) {
					Slot s = new Slot();
					s.setPortEntrada(port);
					s.setTaskSalida(t);
					slots.add(s);
				}
			}
			this.listaSlots.add(slots);
		}
	}

	public void anyadirSlot(ArrayList<Task> tasks, Port port) {
		if (tasks != null && port != null) {
			List<Slot> slots = new ArrayList<Slot>();
			for (Task t : tasks) {
				if (t != null) {
					Slot s = new Slot();
					s.setPortSalida(port);
					s.setTaskEntrada(t);
					slots.add(s);
				}
			}
			this.listaSlots.add(slots);
		}
	}

	public void anyadirSlot(Task task, ArrayList<Port> ports) {
		if (task != null && ports != null) {
			List<Slot> slots = new ArrayList<Slot>();
			for (Port p : ports) {
				if (p != null) {
					Slot s = new Slot();
					s.setPortSalida(p);
					s.setTaskEntrada(task);
					slots.add(s);
				}
			}
			this.listaSlots.add(slots);
		}
	}

	public void anyadirSlot(ArrayList<Port> ports, Task task) {
		if (task != null && ports != null) {
			List<Slot> slots = new ArrayList<Slot>();
			for (Port p : ports) {
				if (p != null) {
					Slot s = new Slot();
					s.setPortEntrada(p);
					s.setTaskSalida(task);
					slots.add(s);
				}
			}
			this.listaSlots.add(slots);
		}
	}

	public static List<Slot> crearLista(Slot... elementos) {
		List<Slot> lista = new LinkedList<Slot>();
		for (Slot elemento : elementos) {
			lista.add(elemento);
		}
		return lista;
	}

	public static List<Task> crearLista(Task... elementos) {
		List<Task> lista = new LinkedList<Task>();
		for (Task elemento : elementos) {
			lista.add(elemento);
		}
		return lista;
	}

	public static List<Port> crearLista(Port... elementos) {
		List<Port> lista = new LinkedList<Port>();
		for (Port elemento : elementos) {
			lista.add(elemento);
		}
		return lista;
	}

}

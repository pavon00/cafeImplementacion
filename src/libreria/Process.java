package libreria;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Process {
	private static Process INSTANCE;
	private ArrayList<List<Slot>> listaSlots;

	private Process() {
		listaSlots = new ArrayList<List<Slot>>();
	}
	
	public void ejecutar() {
		for (List<Slot> list : listaSlots) {
			for (Slot slot : list) {
				slot.ejecutar();
			}
			
		}
	}

	public static Process getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Process();
		}

		return INSTANCE;
	}

	public void anyadirSlot(Port port, ArrayList<Task> tasks) throws ParserConfigurationException, SAXException, IOException {
		if (tasks != null && port != null) {
			List<Slot> slots = new ArrayList<Slot>();
			for (Task t : tasks) {
				if (t != null) {
					Slot s = new Slot(port, t);
					slots.add(s);
				}
			}
			this.listaSlots.add(slots);
		}
	}

	public void anyadirSlot(Task[] tasks, Port port) {
		if (tasks != null && port != null) {
			List<Slot> slots = new ArrayList<Slot>();
			for (Task t : tasks) {
				if (t != null) {
					Slot s = new Slot(t, port);
					slots.add(s);
				}
			}
			this.listaSlots.add(slots);
		}
	}

	public void anyadirSlot(Task task, Port[] ports) {
		if (task != null && ports != null) {
			List<Slot> slots = new ArrayList<Slot>();
			for (Port p : ports) {
				if (p != null) {
					Slot s = new Slot(task, p);
					slots.add(s);
				}
			}
			this.listaSlots.add(slots);
		}
	}

	public void anyadirSlot(Port[] ports, Task task) throws ParserConfigurationException, SAXException, IOException {
		if (task != null && ports != null) {
			List<Slot> slots = new ArrayList<Slot>();
			for (Port p : ports) {
				if (p != null) {
					Slot s = new Slot(p, task);
					slots.add(s);
				}
			}
			this.listaSlots.add(slots);
		}
	}

	public void anyadirSlot(Task task, Task[] tasks) {
		if (task != null && tasks != null) {
			List<Slot> slots = new ArrayList<Slot>();
			for (Task t : tasks) {
				if (t != null) {
					Slot s = new Slot(task, t);
					slots.add(s);
				}
			}
			this.listaSlots.add(slots);
		}
	}

	public void anyadirSlot(Task[] tasks, Task task) {
		if (task != null && tasks != null) {
			List<Slot> slots = new ArrayList<Slot>();
			for (Task t : tasks) {
				if (t != null) {
					Slot s = new Slot(t, task);
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

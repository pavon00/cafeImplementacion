package libreria;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import libreria.threader.ThreaderAdapter;
import libreria.threader.port.Port;
import libreria.threader.task.Task;

public class Process {
	private static Process INSTANCE;
	private ArrayList<List<Slot>> listaSlots;
	public static boolean ESPERAR;
	private ArrayList<Integer> listaSplitNElements;
	private boolean terminar, ejecutandoProceso;
	private ReentrantLock lock;
	private Condition colaTareas, colaProcesos;

	public Process() {
		lock = new ReentrantLock();
		setColaTareas(lock.newCondition());
		setColaProcesos(lock.newCondition());
		terminar = false;
		listaSlots = new ArrayList<List<Slot>>();
		listaSplitNElements = new ArrayList<Integer>();
	}

	private ThreaderAdapter getPrimero() {
		return listaSlots.get(0).get(0).getEntrada();
	}

	private ThreaderAdapter getUltimo() {
		int nUltimo = listaSlots.size() - 1;
		int nUltimoSlot = listaSlots.get(nUltimo).size() - 1;
		return listaSlots.get(nUltimo).get(nUltimoSlot).getSalida();
	}

	public void ejecutar() {
		getPrimero().setPrimero(true);
		getUltimo().setUltimo(true);
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

	public void anyadirSlot(Port port, Task[] tasks) throws ParserConfigurationException, SAXException, IOException {
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

	public void anyadirSlot(Task task, Port port) {
		if (task != null && port != null) {
			List<Slot> slots = new ArrayList<Slot>();
			Slot s = new Slot(task, port);
			slots.add(s);
			this.listaSlots.add(slots);
		}
	}

	public void anyadirSlot(Port port, Task task) throws ParserConfigurationException, SAXException, IOException {
		if (task != null && port != null) {
			List<Slot> slots = new ArrayList<Slot>();
			Slot s = new Slot(port, task);
			slots.add(s);
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

	public void anyadirSlot(Task task1, Task task2) {
		if (task1 != null && task2 != null) {
			List<Slot> slots = new ArrayList<Slot>();
			Slot s = new Slot(task1, task2);
			slots.add(s);
			this.listaSlots.add(slots);
		}
	}

	public static Slot[] crearArray(Slot... elementos) {
		List<Slot> lista = new LinkedList<Slot>();
		for (Slot elemento : elementos) {
			lista.add(elemento);
		}
		Slot[] array = new Slot[lista.size()];
		lista.toArray(array); // fill the array
		return array;
	}

	public static Task[] crearArray(Task... elementos) {
		List<Task> lista = new LinkedList<Task>();
		for (Task elemento : elementos) {
			lista.add(elemento);
		}
		Task[] array = new Task[lista.size()];
		lista.toArray(array); // fill the array
		return array;
	}

	public static Port[] crearArray(Port... elementos) {
		List<Port> lista = new LinkedList<Port>();
		for (Port elemento : elementos) {
			lista.add(elemento);
		}
		Port[] array = new Port[lista.size()];
		lista.toArray(array); // fill the array
		return array;
	}

	public ArrayList<Integer> getListaSplitNElements() {
		return listaSplitNElements;
	}

	public void setListaSplitNElements(ArrayList<Integer> listaSplitNElements) {
		this.listaSplitNElements = listaSplitNElements;
	}

	public boolean isTerminar() {
		return terminar;
	}

	public void setTerminar(boolean terminar) {
		this.terminar = terminar;
		if (terminar) {
			try {
				lock.lock();
			} finally {
				colaTareas.signalAll();
				lock.unlock();
			}
		}
	}

	public Condition getColaTareas() {
		return colaTareas;
	}

	public void setColaTareas(Condition condition) {
		this.colaTareas = condition;
	}

	public Condition getColaProcesos() {
		return colaProcesos;
	}

	public void setColaProcesos(Condition condition) {
		this.colaProcesos = condition;
	}

	public ReentrantLock getLock() {
		return lock;
	}

	public void setLock(ReentrantLock lock) {
		this.lock = lock;
	}

	public boolean isEjecutandoProceso() {
		return ejecutandoProceso;
	}

	public void setEjecutandoProceso(boolean ejecutandoProceso) {
		this.ejecutandoProceso = ejecutandoProceso;
	}

}

package libreria;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Process {
	private static Process INSTANCE;
	private Queue<List<Task>> colaTask;
    
    private Process() {  
    	this.setColaTask(new LinkedList<>());
    }
    
    public static Process getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Process();
        }
        
        return INSTANCE;
    }
    
    public void anyadirAColaTask(Task... elementos) {
    	colaTask.offer(crearLista(elementos));
    }
    
    public List<Task> obtenerGrupoTask() {
    	return colaTask.poll();
    }
    
    private List<Task> crearLista(Task... elementos) {
        List<Task> lista = new LinkedList<Task>();
        for (Task elemento : elementos) {
            lista.add(elemento);
        }
        return lista;
    }

	public Queue<List<Task>> getColaTask() {
		return colaTask;
	}

	public void setColaTask(Queue<List<Task>> colaTask) {
		this.colaTask = colaTask;
	}
}

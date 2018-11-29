/*
 * Correction du TME 7 : coroutines.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp3.ilp3tme7.interpreter;

import java.util.concurrent.Semaphore;

import com.paracamplus.ilp3.ilp3tme7.interpreter.interfaces.ICoroutineInstance;
import com.paracamplus.ilp3.interpreter.Interpreter;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.Invocable;

/*
 * Cette classe correspond à une value "instance de coroutine".
 *
 * Une telle valeur est crée par l'instruction "start" et sert d'argument
 * à l'instruction "resume".
 * 
 * Une instance de coroutine est associée à une "thread" Java, qui
 * l'exécute. 
 */

public class CoroutineInstance 
	extends Thread 
	implements ICoroutineInstance {

	protected Interpreter interpreter;
	protected Invocable coroutine;
	protected Object [] arguments;
	
	protected boolean finished = false;

	// Communication entre la coroutine et son appelant
	private Semaphore resumeSemaphore = new Semaphore(0);
	private Semaphore yieldSemaphore = new Semaphore(0);
	
	// Exception qui échappe de la coroutine
	protected EvaluationException exn = null; 

	
	// Crée une nouvelle instance d'une coroutine
	public CoroutineInstance(
			Interpreter interpreter, 
			Invocable coroutine,
			Object [] arguments) {
		this.interpreter = interpreter;
		this.coroutine = coroutine;
		this.arguments = arguments;
		start();
	}
	
	
	// Cette fonction est appelée dans une nouvelle thread
	public void run() {
		try {
			// Attente du premier resume
			resumeSemaphore.acquireUninterruptibly();
			// Exécution du point d'entrée
			coroutine.apply(interpreter, arguments);
		}
		catch (EvaluationException e) {
			// On se souvient de l'exception pour l'appelant
			exn = e;
		}
		
		// Fin de la coroutine
		finished = true;
		yieldSemaphore.release();
	}

	
	// L'appelant demande à la coroutine de continuer son exécution
	// Renvoie false si la coroutine s'est déjà terminée
	public boolean resumeCoroutine() throws EvaluationException {

		if (finished) return false;
		
		resumeSemaphore.release();
		yieldSemaphore.acquireUninterruptibly();
		
		// Si la coroutine lance une exception non rattrapée,
		// c'est l'appelant qui la reçoit
		if (exn != null) {
			throw exn;
		}
		
		return true;
	}
	
	
	// La coroutine s'interrompt
	public void yieldCoroutine() {
		yieldSemaphore.release();
		resumeSemaphore.acquireUninterruptibly();
	}
	
	
	// Renvoie la coroutine en cours d'exécution
	// ou null si la Thread en cours d'exécution n'est pas une coroutine
	public static CoroutineInstance currentCoroutine() {
		Thread t = Thread.currentThread();
		if (t instanceof CoroutineInstance) {
			return (CoroutineInstance) t;
		}
		return null;
	}
}

/*
 * Correction du TME 7 : coroutines.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp3.ilp3tme7.interpreter.interfaces;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;

/*
 * Interface de l'objet coroutine de l'interprète.
 * C'est une thread avec yield et resume explicites.
 */

public interface ICoroutineInstance extends Runnable {

	// L'appelant demande à la coroutine de continuer son exécution
	// Renvoie false si la coroutine s'est déjà terminée
	public boolean resumeCoroutine() throws EvaluationException;

	// La coroutine s'interrompt
	public void yieldCoroutine();
	
}

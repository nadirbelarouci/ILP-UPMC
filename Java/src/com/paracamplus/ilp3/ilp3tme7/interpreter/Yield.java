/*
 * Correction du TME 7 : coroutines.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp3.ilp3tme7.interpreter;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.primitive.Primitive;

/*
 * Implantation de la primitive resume dans l'interprète.
 */

public class Yield extends Primitive {

	public Yield() {
		super("yield");
	}
	
	public int getArity() {
		return 0;
	}
	
	public Object apply() throws EvaluationException {
		CoroutineInstance i = CoroutineInstance.currentCoroutine();
		if (i == null) {
    		throw new EvaluationException("yield outside a coroutine");
    	}
		i.yieldCoroutine();
        return Boolean.FALSE;
    }	
}	


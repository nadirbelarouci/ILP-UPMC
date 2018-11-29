/*
 * Correction du TME 7 : coroutines.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp3.ilp3tme7.interpreter;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.primitive.UnaryPrimitive;

/*
 * Implantation de la primitive resume dans l'interprète.
 */

public class Resume extends UnaryPrimitive {
	   
    public Resume() {
        super("resume");
    }
        
    public Object apply (Object value) throws EvaluationException {
    	if (! (value instanceof CoroutineInstance)) {
    		throw new EvaluationException("argument is not a coroutine");
    	}
    	CoroutineInstance c = (CoroutineInstance) value;
    	c.resumeCoroutine();
        return Boolean.FALSE;
    }
}

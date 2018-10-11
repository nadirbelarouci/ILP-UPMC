package com.paracamplus.ilp2.ilp2tme3.primitives;

import java.util.Vector;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.primitive.UnaryPrimitive;

public class VectorLength extends UnaryPrimitive {

	public VectorLength() {
	    super("vectorLength");
	}

  
@Override
public Object apply (Object vector) throws EvaluationException {
	if (vector instanceof Vector<?>) {
        return ((Vector<?>)vector).size();
    } else {
        throw new EvaluationException("Argument must be a vector");
    }
}
	
}
package com.paracamplus.ilp2.ilp2tme3.primitives;

import java.math.BigInteger;
import java.util.Vector;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;

public class MakeVector extends BinaryPrimitive {
	      
	public MakeVector() {
		    super("makeVector");
		}
	
	@Override
	public Object apply(Object size, Object filling) throws EvaluationException {
	if ( size instanceof BigInteger ) {
        int length = ((BigInteger) size).intValue();
        Vector<Object> vector = new Vector<>();
        vector.setSize(length);
        for ( int i=0 ; i<length ; i++ ) {
            vector.set(i, filling);
        }
        return vector;
    	} else {
        throw new EvaluationException("Incorrect length!");
    	}
	}
}
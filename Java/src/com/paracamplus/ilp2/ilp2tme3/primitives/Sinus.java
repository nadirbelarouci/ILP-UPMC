/* ******************************************************************
 * ILP -- Implantation d'un langage de programmation.
 * Copyright (C) 2004 <Christian.Queinnec@lip6.fr>
 * $Id:PrintStuff.java 405 2006-09-13 17:21:53Z queinnec $
 * GPL version>=2
 * ******************************************************************/
package com.paracamplus.ilp2.ilp2tme3.primitives;


import java.math.BigDecimal;
import java.math.BigInteger;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.primitive.UnaryPrimitive;


public class Sinus extends UnaryPrimitive {
      
	public Sinus() {
	    super("sinus");
	}

  
@Override
public Object apply (Object value) throws EvaluationException {
	    if (value instanceof BigDecimal)
                return Math.sin(((BigDecimal) value).doubleValue());
        else if (value instanceof BigInteger)
                return Math.sin(((BigInteger) value).doubleValue());
        else throw new EvaluationException("Invalid argument: number expected");
	}
	
}



   

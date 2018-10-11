package com.paracamplus.ilp2.ilp2tme3.primitives;

import java.math.BigInteger;
import java.util.Vector;


import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;

public class VectorGet extends BinaryPrimitive {
	      
		public VectorGet() {
			    super("vectorGet");
			}

		@Override
		public Object apply(Object vector, Object index)
				throws EvaluationException {
			 if (vector instanceof Vector<?>
	           && index instanceof BigInteger ) {
	              return ((Vector<?>) vector)
	                      .elementAt(((BigInteger)index).intValue());
	          } else {
	              throw new EvaluationException("Incorrect argument(s)!");
	          }
		}
		

	}
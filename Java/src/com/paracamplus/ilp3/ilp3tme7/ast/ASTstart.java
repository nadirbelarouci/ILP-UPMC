/*
 * Correction du TME 7 : coroutines.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp3.ilp3tme7.ast;

import com.paracamplus.ilp1.ast.ASTinvocation;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp3.ilp3tme7.interfaces.IASTstart;
import com.paracamplus.ilp3.ilp3tme7.interfaces.IASTvisitor;

/*
 * Implantation du nœud start, qui lance une coroutine.
 */

public class ASTstart extends ASTinvocation implements IASTstart {

	public ASTstart(IASTexpression function, IASTexpression[] arguments) {
		super(function, arguments);
	}
	
    public <Result, Data, Anomaly extends Throwable> 
    Result accept(IASTvisitor<Result, Data, Anomaly> visitor, Data data)
            throws Anomaly {
		/*
		 * Cast pour retrouver un visiteur ayant la bonne méthode visit...
		 */
    	return ((IASTvisitor<Result,Data,Anomaly>) visitor).visit(this, data); 
    }

}

/*
 * Correction du TME 7 : coroutines.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp3.ilp3tme7.ast;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp3.ilp3tme7.interfaces.IASTfactory;

/*
 * Ajoute ASTstart à la fabrique
 */

public class ASTfactory 
	extends com.paracamplus.ilp3.ast.ASTfactory 
	implements IASTfactory {

    public IASTinvocation newStart(IASTexpression function,
            IASTexpression[] arguments) {
    	return new ASTstart(function, arguments);
    }

}

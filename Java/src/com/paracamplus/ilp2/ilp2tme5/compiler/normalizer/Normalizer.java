/* *****************************************************************
 * ilp2 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp2
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ilp2tme5.compiler.normalizer;



import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.normalizer.INormalizationEnvironment;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTbreak;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTlabelledLoop;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTcontinue;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTvisitor;

public class Normalizer 
extends com.paracamplus.ilp2.compiler.normalizer.Normalizer 
implements 
 IASTvisitor<IASTexpression, INormalizationEnvironment, CompilationException> {

    public Normalizer (INormalizationFactory factory) {
    	super(factory);
    }
   
	@Override
	public IASTexpression visit(IASTcontinue iast, INormalizationEnvironment env)
			throws CompilationException {
		return iast;
	}

	@Override
	public IASTexpression visit(IASTbreak iast, INormalizationEnvironment env)
			throws CompilationException {
		return iast;
	}

	@Override
	public IASTexpression visit(IASTlabelledLoop iast,
			INormalizationEnvironment env) throws CompilationException {
		IASTexpression newcondition = iast.getCondition().accept(this, env);
        IASTexpression newbody = iast.getBody().accept(this, env);
        return ((INormalizationFactory)factory).newLabelledLoop(newcondition, newbody, iast.getLabel());
	}
    
}

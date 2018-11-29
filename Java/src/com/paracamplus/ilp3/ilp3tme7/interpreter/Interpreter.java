/*
 * Correction du TME 7 : coroutines.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp3.ilp3tme7.interpreter;

import java.util.List;
import java.util.Vector;

import com.paracamplus.ilp3.ilp3tme7.interfaces.IASTstart;
import com.paracamplus.ilp3.ilp3tme7.interfaces.IASTvisitor;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp1.interpreter.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.ILexicalEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.Invocable;

/*
 * Ajoute le nœud ASTstart à l'interprète.
 */

public class Interpreter
	extends  com.paracamplus.ilp3.interpreter.Interpreter
	implements IASTvisitor<Object, ILexicalEnvironment, EvaluationException> {
	
	public Interpreter(
			IGlobalVariableEnvironment globalVariableEnvironment,
			IOperatorEnvironment operatorEnvironment) {
		super(globalVariableEnvironment, operatorEnvironment);
	}
	
	/*
	 * Important : les nœuds IASTinvocation classiques mais aussi nos nouveaux
	 * nœuds IASTstart arrivent ici (car IASTstart dérive de IASTinvocation)
	 * Il faut donc faire un dispatch à la main avec instanceof...
	 */
	public Object visit(IASTinvocation iast, ILexicalEnvironment lexenv)
			throws EvaluationException {
		if (iast instanceof IASTstart) return visit((IASTstart)iast,lexenv);
		else return super.visit(iast,lexenv);
	}
	
	@Override
	public Object visit(IASTstart iast, ILexicalEnvironment lexenv)
			throws EvaluationException {
		// Évalue la fonction
        Object function = iast.getFunction().accept(this, lexenv);
        if (!(function instanceof Invocable)) {
        	throw new EvaluationException("Cannot apply "+function);        	
        }
        Invocable f = (Invocable)function;
        
        // Évalue les arguments
        List<Object> args = new Vector<Object>();
        for ( IASTexpression arg : iast.getArguments() ) {
        	Object value = arg.accept(this, lexenv);
        	args.add(value);
            }
        
        // Crée la coroutine
		return new CoroutineInstance(this, f, args.toArray());
	}		
		
}

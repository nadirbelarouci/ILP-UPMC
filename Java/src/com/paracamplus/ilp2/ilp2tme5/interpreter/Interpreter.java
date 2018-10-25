/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ilp2tme5.interpreter;

import com.paracamplus.ilp2.ilp2tme5.ExceptionBreak;
import com.paracamplus.ilp2.ilp2tme5.ExceptionContinue;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTbreak;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTlabelledLoop;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTcontinue;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTvisitor;
import com.paracamplus.ilp2.interfaces.IASTloop;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.ILexicalEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.IOperatorEnvironment;


public class Interpreter extends com.paracamplus.ilp2.interpreter.Interpreter
implements IASTvisitor<Object, ILexicalEnvironment, EvaluationException> {
    

    // 
    
    public Interpreter(IGlobalVariableEnvironment globalVariableEnvironment,
			IOperatorEnvironment operatorEnvironment) {
		super(globalVariableEnvironment, operatorEnvironment);
	}
    
   @Override
	public Object visit(IASTbreak iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
    	ExceptionBreak ex = new ExceptionBreak(iast.getLabel());
        throw new EvaluationException (ex);
    }
    
    @Override
	public Object visit(IASTcontinue iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
    	ExceptionContinue ex = new ExceptionContinue(iast.getLabel());
        throw new EvaluationException (ex);
    }

    
    @Override
	public Object visit(IASTloop iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        while ( true ) {
            Object condition = iast.getCondition().accept(this, lexenv);
            if ( condition instanceof Boolean ) {
                Boolean c = (Boolean) condition;
                if ( ! c ) {
                    break;
                }
            }
            try {
            iast.getBody().accept(this, lexenv);
            } catch (EvaluationException e) {
				if (e.getCause() != null
					&& e.getCause() instanceof ExceptionContinue) {
					continue;
				}
				if (e.getCause() != null
					&& e.getCause() instanceof ExceptionBreak) {
					break;
				}
				throw e;
			}
        }
        return Boolean.FALSE;
    }

	@Override
	public Object visit(IASTlabelledLoop iast, ILexicalEnvironment lexenv)
			throws EvaluationException {
		while ( true ) {
            Object condition = iast.getCondition().accept(this, lexenv);
            if ( condition instanceof Boolean ) {
                Boolean c = (Boolean) condition;
                if ( ! c ) {
                    break;
                }
            }
            try {
            iast.getBody().accept(this, lexenv);
            } catch (EvaluationException e) {
				if (e.getCause() != null
						&& e.getCause() instanceof ExceptionContinue
						&& (((ExceptionContinue)e.getCause()).getEtiquette() == null
								|| ((ExceptionContinue)e.getCause()).getEtiquette().equals(iast.getLabel()))) {
						continue;
					}
					if (e.getCause() != null
						&& e.getCause() instanceof ExceptionBreak
						&& (((ExceptionBreak)e.getCause()).getEtiquette() == null
								||((ExceptionBreak)e.getCause()).getEtiquette().equals(iast.getLabel()))) {
						break;
					}
					throw e;
				}
        }
        return Boolean.FALSE;
    }

}

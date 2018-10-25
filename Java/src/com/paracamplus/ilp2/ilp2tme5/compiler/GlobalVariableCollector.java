/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ilp2tme5.compiler;

import java.util.Set;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp2.ilp2tme5.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTbreak;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTlabelledLoop;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTcontinue;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp2.interfaces.IASTprogram;

public class GlobalVariableCollector extends com.paracamplus.ilp2.compiler.GlobalVariableCollector
implements IASTCvisitor<Set<IASTCglobalVariable>, 
                        Set<IASTCglobalVariable>, 
                        CompilationException> {
    
    @Override
	public Set<IASTCglobalVariable> analyze(IASTprogram program) 
            throws CompilationException {
        for ( IASTfunctionDefinition ifd : program.getFunctionDefinitions() ) {
            result = ifd.getBody().accept(this, result);
        }
        result = program.getBody().accept(this, result);
        return result;
    }

	@Override
	public Set<IASTCglobalVariable> visit(IASTlabelledLoop iast,
			Set<IASTCglobalVariable> data) throws CompilationException {
		result = iast.getCondition().accept(this, result);
        result = iast.getBody().accept(this, result);
        return result;
	}

	@Override
	public Set<IASTCglobalVariable> visit(IASTbreak iast,
			Set<IASTCglobalVariable> data) throws CompilationException {
		return result;
	}

	@Override
	public Set<IASTCglobalVariable> visit(IASTcontinue iast,
			Set<IASTCglobalVariable> data) throws CompilationException {
		return result;
	}

}

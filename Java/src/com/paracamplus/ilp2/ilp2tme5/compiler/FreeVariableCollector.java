/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ilp2tme5.compiler;


import java.util.HashSet;
import java.util.Set;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp2.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp2.ilp2tme5.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTbreak;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTlabelledLoop;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTcontinue;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;

public class FreeVariableCollector extends com.paracamplus.ilp2.compiler.FreeVariableCollector
implements IASTCvisitor<Void, Set<IASTClocalVariable>, CompilationException> {

    public FreeVariableCollector(IASTCprogram program) {
        super(program);
    }
    
    @Override
	public IASTCprogram analyze () 
            throws CompilationException {
        for ( IASTfunctionDefinition ifd : ((IASTCprogram) program).getFunctionDefinitions() ) {
            Set<IASTClocalVariable> newvars = new HashSet<>();
            visit(ifd, newvars);
        }
        Set<IASTClocalVariable> newvars = new HashSet<>();
        program.getBody().accept(this, newvars);
        return (IASTCprogram) program;
    }

	@Override
	public Void visit(IASTlabelledLoop iast, Set<IASTClocalVariable> variables)
			throws CompilationException {
		 iast.getCondition().accept(this, variables);
		 iast.getBody().accept(this, variables);
		 return null;
	}
	
	@Override
	public Void visit(IASTbreak iast, Set<IASTClocalVariable> variables)
			throws CompilationException {
		return null;
	}

	@Override
	public Void visit(IASTcontinue iast, Set<IASTClocalVariable> variables)
			throws CompilationException {
		return null;
	}
   
}

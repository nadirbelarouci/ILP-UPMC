/*
 * Correction du TME 7 : coroutines.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp3.ilp3tme7.compiler;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.compiler.normalizer.INormalizationEnvironment;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp3.ilp3tme7.compiler.interfaces.INormalizationFactory;
import com.paracamplus.ilp3.ilp3tme7.interfaces.IASTstart;
import com.paracamplus.ilp3.ilp3tme7.interfaces.IASTvisitor;

/*
 * Ajoute le support pour ASTstart, transformé en ASTCglobalStart.
 */

public class Normalizer 
    extends com.paracamplus.ilp3.compiler.normalizer.Normalizer
	implements IASTvisitor<IASTexpression, INormalizationEnvironment, CompilationException> {

		INormalizationFactory factory;
	
	   public Normalizer (INormalizationFactory factory) {
		   super(factory);
		   this.factory = factory;
	   }
	   
		/*
		 * Important : les nœuds IASTinvocation classiques mais aussi nos nouveaux
		 * nœuds IASTstart arrivent ici (car IASTstart dérive de IASTinvocation)
		 * Il faut donc faire un dispatch à la main avec instanceof...
		 */
		public IASTexpression visit(IASTinvocation iast, INormalizationEnvironment lexenv)
				throws CompilationException {
			if (iast instanceof IASTstart) return visit((IASTstart)iast,lexenv);
			else return super.visit(iast,lexenv);
		}

		
	 // Gestion du nœud IASTstart
	@Override
	public IASTexpression visit(IASTstart iast, INormalizationEnvironment env)
			throws CompilationException {

		// Appel récursif de Normalize sur les fils du nœud
        IASTexpression funexpr = iast.getFunction().accept(this, env);
        IASTexpression[] arguments = iast.getArguments();
        IASTexpression[] args = new IASTexpression[arguments.length];
        for ( int i=0 ; i<arguments.length ; i++ ) {
            args[i] = arguments[i].accept(this,  env);;
        }

        // On n'autorise que les fonctions globales 
        if (!(funexpr instanceof IASTCglobalVariable )) {
        	throw new CompilationException("coroutine is not a global function");
        }
        IASTCglobalVariable f = (IASTCglobalVariable) funexpr;
        
        // Transformation en ASTCGlobalStart
        return factory.newGlobalStart(f, args);
	}
	
}

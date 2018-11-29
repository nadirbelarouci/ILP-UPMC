/*
 * Correction du TME 7 : coroutines.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp3.ilp3tme7.compiler;

import com.paracamplus.ilp3.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp3.ilp3tme7.compiler.interfaces.IASTCglobalStart;
import com.paracamplus.ilp3.ilp3tme7.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp3.ilp3tme7.compiler.interfaces.INormalizationFactory;
import com.paracamplus.ilp3.ilp3tme7.interfaces.IASTstart;
import com.paracamplus.ilp1.compiler.AssignDestination;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp3.interfaces.IASTprogram;
import com.paracamplus.ilp1.interfaces.IASTvariable;

/*
 * Ajoute le suport pour les coroutines au compilateur.
 */

public class Compiler
	extends com.paracamplus.ilp3.compiler.Compiler
    implements IASTCvisitor<Void, Compiler.Context, CompilationException> {

	public Compiler(IOperatorEnvironment ioe,
			IGlobalVariableEnvironment igve) {
		super(ioe, igve);
	}
	
	
	// Normalisation étendue aux nœuds IASTstart
	@Override
    public IASTCprogram normalize(IASTprogram program)
            		throws CompilationException {
    	INormalizationFactory nf = new NormalizationFactory();
    	Normalizer normalizer = new Normalizer(nf);
    	IASTCprogram newprogram = normalizer.transform(program);
    	return newprogram;
    }

	
	@Override
	public Void visit(IASTstart iast, Context data) 
			throws CompilationException {
		/* Cas impossible : tous les nœuds IASTstart ont été remplacés par
		 * IASTCglobalStart par notre classe Normalize
		 */
		throw new CompilationException(
				"internal error: unexpected IASTstart node");
	}

	/*
	 * Le visiteur gère IASTinvocation, mais pas ses sous-classes.
	 * Compiler fait un dispatch à la main.
	 * On fait de même : on ajoute le cas IASTCglobalStart qu'on veut
	 * gérer, et on se rabat sur Compiler pour les autres cas.
	 */
	@Override
	public Void visit(IASTinvocation iast, Context context) 
			throws CompilationException {
		if (iast instanceof IASTCglobalStart) {
			return visit((IASTCglobalStart)iast, context);
		}
		else {
			return super.visit(iast,  context);
		}
	}
	

	/*
	 * L'appel à start fait simplement un ILP_start en passant en
	 * argument la fonction et sa liste d'arguments.
	 * Ce cas est similaire au cas général de IASTCglobalInvocation.
	 */
	@Override
	public Void visit(IASTCglobalStart iast, Context context)
			throws CompilationException {
		emit("{ \n");
		IASTexpression[] arguments = iast.getArguments();
		IASTvariable[] tmps = new IASTvariable[arguments.length];
		for ( int i=0 ; i<arguments.length ; i++ ) {
			IASTvariable tmp = context.newTemporaryVariable();
			emit("  ILP_Object " + tmp.getMangledName() + "; \n");
			tmps[i] = tmp;
		}
		for ( int i=0 ; i<arguments.length ; i++ ) {
			IASTexpression expression = arguments[i];
			IASTvariable tmp = tmps[i];
			Context c = context.redirect(new AssignDestination(tmp));
			expression.accept(this, c);
		}
		emit(context.destination.compile());
		emit("ILP_start(");
		emit("(void(*)())ilp__" + iast.getFunction().getMangledName());
		emit(", ");
		emit(arguments.length);
		for ( int i=0 ; i<arguments.length ; i++ ) {
			IASTvariable tmp = tmps[i];
			emit(", ");
			emit(tmp.getMangledName());
		}
		emit(");\n}\n");
		return null;        
	}

}

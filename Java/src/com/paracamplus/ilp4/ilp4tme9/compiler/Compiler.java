/*
 * Correction du TME 9 : Caches de méthodes
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp4.ilp4tme9.compiler;

import com.paracamplus.ilp1.compiler.AssignDestination;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp4.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp4.interfaces.IASTsend;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.Inamed;

public class Compiler
	extends com.paracamplus.ilp4.compiler.Compiler {

	
	public Compiler(IOperatorEnvironment ioe,
			IGlobalVariableEnvironment igve) {
		super(ioe, igve);
	}
	
	   public Void visit(IASTCprogram iast, Context context)
	            throws CompilationException {
		   cProgramSuffix = "\n"
	            + "static ILP_Object ilp_caught_program () {\n"
	            + "  struct ILP_catcher* current_catcher = ILP_current_catcher;\n"
	            + "  struct ILP_catcher new_catcher;\n\n"
	            + "  if ( 0 == setjmp(new_catcher._jmp_buf) ) {\n"
	            + "    ILP_establish_catcher(&new_catcher);\n"
	            + "    return ilp_program();\n"
	            + "  };\n"
	            + "  return ILP_current_exception;\n"
	            + "}\n\n"
	            + "int main (int argc, char *argv[]) \n"
	            + "{ \n"
	            + "  ILP_START_GC; \n"
	            + "  ILP_print(ilp_caught_program()); \n"
	            + "  ILP_newline(); \n"
	            + "  ILP_method_cache_statistics();\n" /* Ajout TME9 */
	            + "  return EXIT_SUCCESS; \n"
	            + "} \n";    		
		   return super.visit(iast,context);
	   }
	
 
	/*
	 * Nœud send modifié par l'ajout du cache à chaque site d'appel.
	 * 
	 * On recopie le code de Compiler, sauf aux endroits spécifiquement
	 * marqués comme modifiés.
	 */	
	@Override
	public Void visit(IASTsend iast, Context context)
			throws CompilationException {
		emit("{ \n");
		IASTvariable tmpMethod = context.newTemporaryVariable();
		emit("  ILP_general_function " + tmpMethod.getMangledName() + "; \n");
		IASTvariable tmpReceiver = context.newTemporaryVariable();
		emit("  ILP_Object " + tmpReceiver.getMangledName() + "; \n");
		Context c = context.redirect(new AssignDestination(tmpReceiver));

		IASTexpression[] arguments = iast.getArguments();
		IASTvariable[] tmps = new IASTvariable[arguments.length];
		for ( int i=0 ; i<arguments.length ; i++ ) {
			IASTvariable tmp = context.newTemporaryVariable();
			emit("  ILP_Object " + tmp.getMangledName() + "; \n");
			tmps[i] = tmp;
		}
	        
		iast.getReceiver().accept(this, c);
		for ( int i=0 ; i<arguments.length ; i++ ) {
			IASTexpression expression = arguments[i];
			IASTvariable tmp = tmps[i];
			Context c2 = context.redirect(new AssignDestination(tmp));
			expression.accept(this, c2);
		}

		/* Début modification TME9 */
		emit("ILP_find_method_cached(");
		emit(tmpMethod.getMangledName());
		emit(", ");
		emit(tmpReceiver.getMangledName());
		emit(", &ILP_object_");
		emit(Inamed.computeMangledName(iast.getMethodName()));
		emit("_method, ");
		emit(1 + arguments.length);
		emit(");\n");	
		/* Fin modification TME9 */

		emit(context.destination.compile());
		emit(tmpMethod.getName());
		emit("(NULL, ");
		emit(tmpReceiver.getMangledName());
		for ( int i = 0 ; i<arguments.length ; i++ ) {
			emit(", ");
			emit(tmps[i].getMangledName());
		}
		emit(");\n}\n");
		return null;
}
	   

}

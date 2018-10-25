/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ilp2tme5.compiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Set;

import com.paracamplus.ilp1.compiler.AssignDestination;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp2.ilp2tme5.compiler.FreeVariableCollector;
import com.paracamplus.ilp2.ilp2tme5.compiler.GlobalVariableCollector;
import com.paracamplus.ilp1.compiler.NoDestination;
import com.paracamplus.ilp1.compiler.VoidDestination;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp2.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp2.ilp2tme5.ast.ASTlabelledLoop;
import com.paracamplus.ilp2.ilp2tme5.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp2.ilp2tme5.compiler.normalizer.INormalizationFactory;
import com.paracamplus.ilp2.ilp2tme5.compiler.normalizer.NormalizationFactory;
import com.paracamplus.ilp2.ilp2tme5.compiler.normalizer.Normalizer;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTbreak;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTlabelledLoop;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTcontinue;
import com.paracamplus.ilp2.interfaces.IASTprogram;


public class Compiler extends com.paracamplus.ilp2.compiler.Compiler 
implements IASTCvisitor<Void, Compiler.Context, CompilationException> {
    
 
    public Compiler(IOperatorEnvironment ioe, IGlobalVariableEnvironment igve) {
		super(ioe, igve);
	}
    
    private ArrayList<String> ls = new ArrayList<String>();
    
	public void enterLoop() {
		ls.add(0, null);
	}

	public void enterLoop(String e) {
		ls.add(0,e);
	}

	public void exitLoop() {
		ls.remove(0);
	}

	public boolean inLoop() {
		return (ls.size() > 0);
	}

	public boolean inLoop(String label) {
		return ls.contains(label);
	}
	
    //

   @Override
    public IASTCprogram normalize(IASTprogram program) 
            throws CompilationException {
        INormalizationFactory nf = new NormalizationFactory();
        Normalizer normalizer = new Normalizer(nf);
        IASTCprogram newprogram = normalizer.transform(program);
        return newprogram;
    }

    @Override
	public String compile(IASTprogram program) 
            throws CompilationException {
        
        IASTCprogram newprogram = normalize(program);
        newprogram = ((IASTCprogram) optimizer.transform(newprogram));

        GlobalVariableCollector gvc = new GlobalVariableCollector();
        Set<IASTCglobalVariable> gvs = gvc.analyze(newprogram);
        newprogram.setGlobalVariables(gvs);
        
        FreeVariableCollector fvc = new FreeVariableCollector(newprogram);
        newprogram = (fvc.analyze());
      
        Context context = new Context(NoDestination.NO_DESTINATION);
        StringWriter sw = new StringWriter();
        try {
            out = new BufferedWriter(sw);
            visit(newprogram, context);
            out.flush();
        } catch (IOException exc) {
            throw new CompilationException(exc);
        }
        return sw.toString();
    }

	@Override
	public Void visit(IASTlabelledLoop iast, Context context)
			throws CompilationException {
		  emit("while ( 1 ) { \n");
		  	if (iast.getLabel() != null)
		    	emit( ASTlabelledLoop.getCgenLabel(iast.getLabel()) + "_RESTART: (void)0;\n");
		  	
	        IASTvariable tmp = context.newTemporaryVariable();
	        emit("  ILP_Object " + tmp.getMangledName() + "; \n");
	        Context c = context.redirect(new AssignDestination(tmp));
	        iast.getCondition().accept(this, c);
	        emit(" if ( ILP_isEquivalentToTrue(");
	        emit(tmp.getMangledName());
	        emit(") ) {\n");
	        Context cb = context.redirect(VoidDestination.VOID_DESTINATION);
			enterLoop(iast.getLabel());
	        iast.getBody().accept(this, cb);
	        exitLoop();
	        emit("\n} else { \n");
	        emit("    break; \n");
	        emit("\n}\n}\n");
	        if (iast.getLabel() != null)
		    	emit( ASTlabelledLoop.getCgenLabel(iast.getLabel()) + "_END: (void)0;\n");
	        whatever.accept(this, context);
	        return null;
	    }
	

	@Override
	public Void visit(IASTbreak iast, Context context)
			throws CompilationException {
		if (iast.getLabel() != null) {
				if (inLoop(iast.getLabel())) {
					emit("goto " + ASTlabelledLoop.getCgenLabel(iast.getLabel()) + "_END;");
				} else {
					throw new CompilationException("break en dehors de la boucle " + iast.getLabel());
				}
			} else if (inLoop()) {
				emit("break;");
			} else {
				throw new CompilationException("break en dehors d'une boucle");
			}
		return null;
	}

	@Override
	public Void visit(IASTcontinue iast, Context context)
			throws CompilationException {
		if (iast.getLabel() != null) {
				if (inLoop(iast.getLabel())) {
					emit("goto " + ASTlabelledLoop.getCgenLabel(iast.getLabel()) + "_RESTART;");
				} else {
					throw new CompilationException("continue en dehors de la boucle " + iast.getLabel());
				}
			} else if (inLoop()) {
				emit("continue;");
			} else {
				throw new CompilationException("continue en dehors d'une boucle");
			}
		return null;
	}
    //
   
    
  /*  public Void visit(IASTloop iast, Context context)
            throws CompilationException {
        emit("while ( 1 ) { \n");
        IASTvariable tmp = context.newTemporaryVariable();
        emit("  ILP_Object " + tmp.getMangledName() + "; \n");
        Context c = context.redirect(new AssignDestination(tmp));
        iast.getCondition().accept(this, c);
        emit("  if ( ILP_isEquivalentToTrue(");
        emit(tmp.getMangledName());
        emit(") ) {\n");
        Context cb = context.redirect(VoidDestination.VOID_DESTINATION);
        iast.getBody().accept(this, cb);
        emit("\n} else { \n");
        emit("    break; \n");
        emit("\n}\n}\n");
        whatever.accept(this, context);
        return null;
    }
    
*/
}

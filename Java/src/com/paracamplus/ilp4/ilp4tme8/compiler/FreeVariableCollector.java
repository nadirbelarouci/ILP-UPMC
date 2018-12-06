/*
 * Correction du TME 8 : accès réflexif aux champs.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp4.ilp4tme8.compiler;

import java.util.Set;

import com.paracamplus.ilp4.ilp4tme8.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASThasProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTreadProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTwriteProperty;
import com.paracamplus.ilp4.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;

/*
 * Ajout de nos nœuds au visiteur.
 */

public class FreeVariableCollector 
	extends com.paracamplus.ilp4.compiler.FreeVariableCollector 
	implements IASTCvisitor<Void, Set<IASTClocalVariable>, CompilationException> {

	public FreeVariableCollector(IASTCprogram program) {
		super(program);
	}

	/*
	 * Pas de traitement spécifique pour nos nouveaux nœuds.
	 * On se contente de visiter les arguments.
	 */

	@Override
	public Void visit(IASThasProperty iast, Set<IASTClocalVariable> data)
			throws CompilationException {
		iast.getTarget().accept(this, data);
		iast.getProperty().accept(this, data);
		return null;
	}

	@Override
	public Void visit(IASTreadProperty iast, Set<IASTClocalVariable> data)
			throws CompilationException {
		iast.getTarget().accept(this, data);
		iast.getProperty().accept(this, data);
		return null;
	}

	@Override
	public Void visit(IASTwriteProperty iast, Set<IASTClocalVariable> data)
			throws CompilationException {
		iast.getTarget().accept(this, data);
		iast.getProperty().accept(this, data);
		iast.getValue().accept(this, data);
		return null;
	}

}

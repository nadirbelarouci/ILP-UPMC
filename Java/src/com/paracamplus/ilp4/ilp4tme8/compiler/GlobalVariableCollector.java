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
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;

/*
 * Ajout de nos nœuds au visiteur.
 */

public class GlobalVariableCollector
	extends com.paracamplus.ilp4.compiler.GlobalVariableCollector 
	implements IASTCvisitor<
		Set<IASTCglobalVariable>, 
		Set<IASTCglobalVariable>, 
		CompilationException> {

	
	/*
	 * Pas de traitement spécifique pour nos nouveaux nœuds.
	 * On se contente de visiter les arguments.
	 */

	@Override
	public Set<IASTCglobalVariable> visit(IASThasProperty iast,
			Set<IASTCglobalVariable> data) throws CompilationException {
		data = iast.getTarget().accept(this,data);
		data = iast.getProperty().accept(this,data);
		return data;
	}

	@Override
	public Set<IASTCglobalVariable> visit(IASTreadProperty iast,
			Set<IASTCglobalVariable> data) throws CompilationException {
		data = iast.getTarget().accept(this,data);
		data = iast.getProperty().accept(this,data);
		return data;
	}

	@Override
	public Set<IASTCglobalVariable> visit(IASTwriteProperty iast,
			Set<IASTCglobalVariable> data) throws CompilationException {
		data = iast.getTarget().accept(this,data);
		data = iast.getProperty().accept(this,data);
		data = iast.getValue().accept(this,data);
		return data;
	}

}

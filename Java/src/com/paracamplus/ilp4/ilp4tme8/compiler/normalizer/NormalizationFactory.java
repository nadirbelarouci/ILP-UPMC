/*
 * Correction du TME 8 : accès réflexif aux champs.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp4.ilp4tme8.compiler.normalizer;

import com.paracamplus.ilp4.ilp4tme8.ast.ASThasProperty;
import com.paracamplus.ilp4.ilp4tme8.ast.ASTreadProperty;
import com.paracamplus.ilp4.ilp4tme8.ast.ASTwriteProperty;
import com.paracamplus.ilp1.interfaces.IASTexpression;

/*
 * Ajout de nos nœuds à la fabrique.
 * 
 * L'AST normalisé continue à utiliser ASThasProperty et co.
 * Il a été inutile de deriver des nœuds ASTChasProperty et co.
 */

public class NormalizationFactory 
extends com.paracamplus.ilp4.compiler.normalizer.NormalizationFactory 
implements INormalizationFactory {

	@Override
	public IASTexpression newExistsProperty(IASTexpression target,
			IASTexpression property) {
		return new ASThasProperty(target, property);
	}

	@Override
	public IASTexpression newReadProperty(IASTexpression target,
			IASTexpression property) {
		return new ASTreadProperty(target, property);
	}

	@Override
	public IASTexpression newWriteProperty(IASTexpression target,
			IASTexpression property, IASTexpression value) {
		return new ASTwriteProperty(target, property, value);
	}

}

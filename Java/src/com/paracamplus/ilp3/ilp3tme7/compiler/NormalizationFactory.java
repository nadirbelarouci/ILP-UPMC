/*
 * Correction du TME 7 : coroutines.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp3.ilp3tme7.compiler;

import com.paracamplus.ilp3.ilp3tme7.compiler.interfaces.INormalizationFactory;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.interfaces.IASTexpression;

/*
 * Ajoute le support pour ASTCglobalStart.
 */

public class NormalizationFactory
	extends com.paracamplus.ilp3.compiler.normalizer.NormalizationFactory
	implements INormalizationFactory {

	@Override
	public IASTexpression newGlobalStart(IASTCglobalVariable funexpr,
			IASTexpression[] arguments) {
		return new ASTCglobalStart(funexpr, arguments);
	}

}

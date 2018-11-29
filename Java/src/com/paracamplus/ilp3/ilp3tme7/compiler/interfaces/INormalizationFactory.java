/*
 * Correction du TME 7 : coroutines.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp3.ilp3tme7.compiler.interfaces;

import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.interfaces.IASTexpression;


/*
 * Ajoute IASTstart à l'interface de Normalisation.
 */

public interface INormalizationFactory 
	extends com.paracamplus.ilp3.compiler.normalizer.INormalizationFactory {

    IASTexpression newGlobalStart(
            IASTCglobalVariable funexpr,
            IASTexpression[] arguments);

}

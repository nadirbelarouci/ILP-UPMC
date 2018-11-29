/*
 * Correction du TME 7 : coroutines.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp3.ilp3tme7.interfaces;

import com.paracamplus.ilp1.interfaces.IASTexpression;

/*
 * Ajoute IASTstart à la fabrique.
 */

public interface IASTfactory extends com.paracamplus.ilp3.interfaces.IASTfactory {

	IASTexpression newStart(
            IASTexpression function,
            IASTexpression[] arguments);

}

/*
 * Correction du TME 7 : coroutines.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp3.ilp3tme7.compiler.interfaces;

import com.paracamplus.ilp3.ilp3tme7.interfaces.IASTvisitor;

// Ajoute IASTCglobalStart au visiteur

public interface IASTCvisitor<Result, Data, Anomaly extends Throwable>
    extends com.paracamplus.ilp3.compiler.interfaces.IASTCvisitor<Result, Data, Anomaly>,
    IASTvisitor<Result, Data, Anomaly> {

	Result visit(IASTCglobalStart iast, Data data) throws Anomaly;

}

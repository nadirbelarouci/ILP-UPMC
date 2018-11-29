/*
 * Correction du TME 7 : coroutines.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp3.ilp3tme7.interfaces;

/*
 * Ajoute IASTstart au visiteur.
 */

public interface IASTvisitor <Result, Data, Anomaly extends Throwable> 
	extends com.paracamplus.ilp3.interfaces.IASTvisitor<Result,Data,Anomaly> {
	
	Result visit(IASTstart iast, Data data) throws Anomaly;
	
}

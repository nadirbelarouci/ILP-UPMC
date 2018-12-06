/*
 * Correction du TME 8 : accès réflexif aux champs.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp4.ilp4tme8.interfaces;

//  Ajoute nos nœuds au visiteur.

public interface IASTvisitor <Result, Data, Anomaly extends Throwable> 
	extends com.paracamplus.ilp4.interfaces.IASTvisitor<Result,Data,Anomaly> {
	
	Result visit(IASThasProperty iast, Data data) throws Anomaly;
	Result visit(IASTreadProperty iast, Data data) throws Anomaly;
	Result visit(IASTwriteProperty iast, Data data) throws Anomaly;
	
}

/*
 * Correction du TME 8 : accès réflexif aux champs.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp4.ilp4tme8.compiler.interfaces;

import com.paracamplus.ilp4.ilp4tme8.interfaces.IASThasProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTreadProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTwriteProperty;

/* 
 * Ajout de nos nœuds au visiteur d'AST normalisé.
 */

public interface IASTCvisitor<Result, Data, Anomaly extends Throwable> 
	extends com.paracamplus.ilp4.compiler.interfaces.IASTCvisitor<Result, Data, Anomaly> {

	/*
	 * Pas de version ASTC de nos nœuds : on utilise encore les nœuds AST.
	 */
	Result visit(IASThasProperty iast, Data data) throws Anomaly;
	Result visit(IASTreadProperty iast, Data data) throws Anomaly;
	Result visit(IASTwriteProperty iast, Data data) throws Anomaly;
}

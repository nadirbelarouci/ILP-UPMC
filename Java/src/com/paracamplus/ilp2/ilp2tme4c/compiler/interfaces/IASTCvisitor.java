/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ilp2tme4c.compiler.interfaces;

import com.paracamplus.ilp2.ilp2tme4.interfaces.IASTunless;



public interface IASTCvisitor<Result, Data, Anomaly extends Throwable> 
extends com.paracamplus.ilp2.ilp2tme4.interfaces.IASTvisitor<Result, Data, Anomaly> {
	Result visit(IASTunless iast, Data data) throws Anomaly;
}

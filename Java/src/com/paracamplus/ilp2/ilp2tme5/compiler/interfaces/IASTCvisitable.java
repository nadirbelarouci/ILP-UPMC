/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ilp2tme5.compiler.interfaces;


public interface IASTCvisitable extends 
com.paracamplus.ilp1.compiler.interfaces.IASTCvisitable {
    <Result, Data, Anomaly extends Throwable> 
    Result accept(IASTCvisitor<Result, Data, Anomaly> visitor,
                  Data data) throws Anomaly;
}

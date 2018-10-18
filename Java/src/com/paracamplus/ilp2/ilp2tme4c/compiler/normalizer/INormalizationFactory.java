/* *****************************************************************
 * ilp2 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp2
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ilp2tme4c.compiler.normalizer;

import com.paracamplus.ilp1.interfaces.IASTexpression;


 public interface INormalizationFactory 
 	extends com.paracamplus.ilp2.compiler.normalizer.INormalizationFactory {

    IASTexpression newaMoinsQue (IASTexpression condition, 
                                  IASTexpression body);
     


}

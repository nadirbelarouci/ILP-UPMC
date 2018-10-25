/* *****************************************************************
 * ilp2 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp2
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ilp2tme5.compiler.normalizer;


import com.paracamplus.ilp2.ilp2tme5.compiler.ast.ASTCbreak;
import com.paracamplus.ilp2.ilp2tme5.compiler.ast.ASTClabelledLoop;
import com.paracamplus.ilp2.ilp2tme5.compiler.ast.ASTCcontinue;
import com.paracamplus.ilp1.interfaces.IASTexpression;

public class NormalizationFactory
extends com.paracamplus.ilp2.compiler.normalizer.NormalizationFactory
implements INormalizationFactory {
    
	
	@Override
	public IASTexpression newContinue(String et){
		return new ASTCcontinue(et);
	}
	     
	@Override
	public IASTexpression newBreak(String et) {
		return new ASTCbreak(et);
	}
	     
	@Override
	public IASTexpression newLabelledLoop(IASTexpression condition, 
	             IASTexpression body, String et) {
	      return new ASTClabelledLoop(condition, body, et);
	}
	     
}

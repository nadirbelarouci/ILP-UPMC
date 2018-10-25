/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ilp2tme5.ast;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTbreak;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTcontinue;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTfactory;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTlabelledLoop;


public class ASTfactory extends com.paracamplus.ilp2.ast.ASTfactory 
	implements IASTfactory{

	@Override
	public IASTbreak newBreak(String e) {
		return new ASTbreak(e);
	}
	
	@Override
	public IASTcontinue newContinue(String e) {
		return new ASTcontinue(e);
	}
	
	@Override
	public IASTlabelledLoop newLabelledLoop(
			IASTexpression condition,
			IASTexpression body, String et) {
		return new ASTlabelledLoop(condition, body, et);
	}

}

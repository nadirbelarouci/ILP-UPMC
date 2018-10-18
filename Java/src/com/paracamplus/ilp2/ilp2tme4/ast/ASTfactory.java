package com.paracamplus.ilp2.ilp2tme4.ast;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp2.ilp2tme4.interfaces.IASTfactory;



public class ASTfactory extends com.paracamplus.ilp2.ast.ASTfactory
implements IASTfactory {


	@Override
	public IASTexpression newUnless(IASTexpression condition,
			IASTexpression corps) {
		return new ASTunless (condition, corps);
	}

}

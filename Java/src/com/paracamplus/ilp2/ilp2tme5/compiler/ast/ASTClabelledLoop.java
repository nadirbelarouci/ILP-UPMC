/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ilp2tme5.compiler.ast;


import com.paracamplus.ilp2.ilp2tme5.ast.ASTlabelledLoop;
import com.paracamplus.ilp2.ilp2tme5.compiler.interfaces.IASTCvisitable;
import com.paracamplus.ilp2.ilp2tme5.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp1.interfaces.IASTexpression;

public class ASTClabelledLoop extends ASTlabelledLoop
implements IASTCvisitable {

	public ASTClabelledLoop(IASTexpression condition, IASTexpression body,
			String label) {
		super(condition, body, label);
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(
			com.paracamplus.ilp1.compiler.interfaces.IASTCvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return accept((IASTCvisitor<Result, Data, Anomaly>)visitor, data);
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(
			IASTCvisitor<Result, Data, Anomaly> visitor, Data data)
			throws Anomaly {
		return visitor.visit(this, data);
	}



  
}

package com.paracamplus.ilp2.ilp2tme5.ast;

import com.paracamplus.ilp1.ast.ASTinstruction;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTbreak;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTvisitable;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTvisitor;


public class ASTbreak extends ASTinstruction 
implements IASTbreak, IASTvisitable  {

	public ASTbreak(String label) {
		super();
		this.label = label;
	}

	private String label = null;
	
	@Override
	public String getLabel() {
		return label;
	}


	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(
			IASTvisitor<Result, Data, Anomaly> visitor, Data data)
			throws Anomaly {
		return visitor.visit(this, data);
	}

	public <Result, Data, Anomaly extends Throwable> Result accept(
			com.paracamplus.ilp2.interfaces.IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return accept((IASTvisitor<Result,Data,Anomaly>)visitor, data);
	}

	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(
			com.paracamplus.ilp1.interfaces.IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return accept((IASTvisitor<Result,Data,Anomaly>)visitor, data);
	}

}

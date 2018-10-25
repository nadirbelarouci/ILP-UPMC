package com.paracamplus.ilp2.ilp2tme5.ast;

import java.util.HashMap;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp2.ast.ASTloop;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTlabelledLoop;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTvisitable;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTvisitor;


public class ASTlabelledLoop extends ASTloop 
implements IASTlabelledLoop, IASTvisitable {

	public ASTlabelledLoop(IASTexpression condition,
			IASTexpression body,
			String label) {
		super(condition, body);
		this.label = label;
	}

	private String label;
	private static int guid = 0;
	private static HashMap<String, String> nuid = new HashMap<String, String>();
	
	@Override
	public String getLabel() {
		return label;
	}
	
	public static String getCgenLabel(String etiquette) {
		if (!nuid.containsKey(etiquette)) {
			guid++;
			String n = "__loop_lbl_" + guid ;
			nuid.put(etiquette, n);
		}
		return nuid.get(etiquette);
	}
	
	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(
			IASTvisitor<Result, Data, Anomaly> visitor, Data data)
			throws Anomaly {
		return visitor.visit(this, data);
	}

	@Override
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

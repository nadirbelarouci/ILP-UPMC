package com.paracamplus.ilp2.ilp2tme4.ast;

import com.paracamplus.ilp1.ast.ASTexpression;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp2.ilp2tme4.interfaces.IASTunless;
import com.paracamplus.ilp2.ilp2tme4.interfaces.IASTvisitor;

public class ASTunless extends ASTexpression 
	implements IASTunless {
	    
	    public ASTunless (IASTexpression condition, IASTexpression body) {
	        this.condition = condition;
	        this.body = body;
	    }
	    private final IASTexpression condition;
	    private final IASTexpression body;

		public IASTexpression getCondition() {
	        return condition;
	    }

		public IASTexpression getBody() {
	        return body;
	    }

		@Override
		public <Result, Data, Anomaly extends Throwable> Result accept(
				com.paracamplus.ilp1.interfaces.IASTvisitor<Result, Data, Anomaly> visitor,
				Data data) throws Anomaly {
			return accept(((IASTvisitor <Result, Data, Anomaly>) visitor), data);
		}

		@Override
		public <Result, Data, Anomaly extends Throwable> Result accept(
				IASTvisitor<Result, Data, Anomaly> visitor, Data data)
				throws Anomaly {
			return visitor.visit(this, data);
		}

	
	    
}
 /*
 * Correction du TME 8 : accès réflexif aux champs.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp4.ilp4tme8.ast;

import com.paracamplus.ilp4.ilp4tme8.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTvisitor;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTwriteProperty;
import com.paracamplus.ilp1.ast.ASTexpression;
import com.paracamplus.ilp1.interfaces.IASTexpression;

/*
 * Nœud writeProperty : écriture d'un champ dynamique.
 */

public class ASTwriteProperty extends ASTexpression
implements IASTwriteProperty {

    public ASTwriteProperty (
                          IASTexpression target,
                          IASTexpression property,
                          IASTexpression value) {
        this.property = property;
        this.target = target;
        this.value = value;
    }
    private final IASTexpression property;
    private final IASTexpression target;
    private final IASTexpression value;
    
    public IASTexpression getTarget() {
        return target;
    }

    public IASTexpression getProperty() {
        return property;
    }
    
    public IASTexpression getValue() {
        return value;
    }

    // Le visiteur doit en réalité être un IASTvisitorTME8 ou un IASTCvisitorTME8
    public <Result, Data, Anomaly extends Throwable> Result 
        accept(com.paracamplus.ilp1.interfaces.IASTvisitor<Result, Data, Anomaly> visitor, Data data)
                throws Anomaly {
    	if (visitor instanceof IASTvisitor) {
    		return ((IASTvisitor<Result,Data,Anomaly>)visitor).visit(this,data);
    	}
    	if (visitor instanceof IASTCvisitor) {
    		return ((IASTCvisitor<Result,Data,Anomaly>)visitor).visit(this,data);
    	}
    	throw new IllegalArgumentException(
    			"visitor argument must implement IASTvisitorTME8 ou IASTCvisitorTME8");
    }
}

package com.paracamplus.ilp2.ilp2tme5.interfaces;


import com.paracamplus.ilp1.interfaces.IASTexpression;

public interface IASTfactory extends com.paracamplus.ilp2.interfaces.IASTfactory {
    
    IASTcontinue newContinue(String etiquette);
	
	IASTbreak newBreak(String etiquette);

	IASTlabelledLoop newLabelledLoop(IASTexpression condition,
            IASTexpression body, String et);
    
}

package com.paracamplus.ilp2.ilp2tme4.ilp2tme4b.parser.ilpml;

import antlr4.ILPMLgrammar2tme4Parser.UnlessContext;

import com.paracamplus.ilp2.ilp2tme4.interfaces.IASTfactory;

public class ILPMLListener
extends com.paracamplus.ilp2.ilp2tme4.ilp2tme4a.parser.ilpml.ILPMLListener {
	
	public ILPMLListener(IASTfactory factory) {
		super(factory);
	}

	@Override
	public void exitUnless(UnlessContext ctx) {
		ctx.node = factory.newUnless(
				ctx.condition.node, 
				ctx.body.node);
	}

}

/*
 * Correction du TME 7 : coroutines.
 * Année 2015-2016
 *
 * Antoine Miné
 */


package com.paracamplus.ilp3.ilp3tme7.parser.xml;

import org.w3c.dom.Element;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.parser.ParseException;
import com.paracamplus.ilp3.ilp3tme7.interfaces.IASTfactory;

/*
 *  Ajoute le nœud ASTstart au parser.
 */

public class XMLParser extends com.paracamplus.ilp3.parser.xml.XMLParser {

	// Il nous faut une fabrique étendue, comprenant start
	protected IASTfactory factory;
	
	public XMLParser(IASTfactory factory) {
		super(factory);
        addMethod("costart", XMLParser.class);
		this.factory = factory;
	}
	
	public IASTfactory getFactory() {
		return factory;
	}
	
    public IASTexpression costart (Element e) throws ParseException {
        IASTexpression fun = narrowToIASTexpression(
                findThenParseChildContent(e, "function"));
        IASTexpression[] iasts = 
                findThenParseChildAsExpressions(e, "arguments");
        return getFactory().newStart(fun, iasts);
    }

}

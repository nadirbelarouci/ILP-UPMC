/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ilp2tme4.ilp2tme4c.parser.xml;

import org.w3c.dom.Element;

import com.paracamplus.ilp1.interfaces.IAST;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.parser.ParseException;
import com.paracamplus.ilp2.interfaces.IASTprogram;
import com.paracamplus.ilp2.ilp2tme4.interfaces.IASTfactory;

public class XMLParser extends com.paracamplus.ilp2.parser.xml.XMLParser {

	public XMLParser(com.paracamplus.ilp2.interfaces.IASTfactory factory) {
		super(factory);
        addMethod("unless", XMLParser.class);
	}

	@Override
	public IASTprogram getProgram() throws ParseException {
		IASTprogram program = super.getProgram();
		return program;
	}
		
	public IASTexpression unless (Element e) throws ParseException {
        IAST iastc = findThenParseChildContent(e, "condition");
        IASTexpression condition = narrowToIASTexpression(iastc);
        IASTexpression[] iaste = 
                findThenParseChildAsExpressions(e, "body");
        IASTexpression corps = getFactory().newSequence(iaste);
        return ((IASTfactory) getFactory()).newUnless (condition, corps);
    }
}


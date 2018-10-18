/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ilp2tme4a.parser.xml;

import org.w3c.dom.Element;

import com.paracamplus.ilp1.interfaces.IAST;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.parser.ParseException;
import com.paracamplus.ilp2.interfaces.IASTfactory;

public class XMLParser extends com.paracamplus.ilp2.parser.xml.XMLParser {

	public XMLParser(IASTfactory factory) {
		super(factory);
        addMethod("unless", XMLParser.class);
	}

	public IASTexpression unless (Element e) throws ParseException {
        IAST iastc = findThenParseChildContent(e, "condition");
        IASTexpression condition = narrowToIASTexpression(iastc);
        IASTexpression[] iaste = 
                findThenParseChildAsExpressions(e, "body");
        IASTexpression corps = getFactory().newSequence(iaste);
        IASTexpression nonCondition = factory.newUnaryOperation(factory.newOperator ("!"), condition);
        return factory.newAlternative(nonCondition, corps,factory.newBooleanConstant("false"));
    }
}


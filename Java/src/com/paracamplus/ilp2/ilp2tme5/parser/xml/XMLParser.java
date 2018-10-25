/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ilp2tme5.parser.xml;

import org.w3c.dom.Element;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.parser.ParseException;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTbreak;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTlabelledLoop;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTcontinue;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTfactory;


public class XMLParser extends com.paracamplus.ilp2.parser.xml.XMLParser {

	public XMLParser(IASTfactory factory) {
		super(factory);
        addMethod("handleContinue", XMLParser.class, "continue");
        addMethod("handleBreak", XMLParser.class, "break");
        addMethod("labelledLoop", XMLParser.class);
	}
    
	public IASTbreak handleBreak (Element e) 
            throws ParseException {
		String et = e.hasAttribute("label") ? e.getAttribute("label") : null;
		return ((IASTfactory) getFactory()).newBreak(et);
	}
	
	public  IASTcontinue handleContinue(Element e) 
	            throws ParseException {
		  String et = e.hasAttribute("label") ? e.getAttribute("label") : null;
	      return ((IASTfactory) getFactory()).newContinue(et);
	  }
	
	public IASTlabelledLoop labelledLoop(Element e) 
			throws ParseException {
		IASTexpression condition = narrowToIASTexpression(
                findThenParseChildContent(e, "condition"));
		IASTexpression[] expressions = 
                findThenParseChildAsExpressions(e, "body");
        IASTexpression body = getFactory().newSequence(expressions);
		String et = e.hasAttribute("label") ? e.getAttribute("label") : null;
		return ((IASTfactory) getFactory()).newLabelledLoop(condition, body, et);
	}
 
}

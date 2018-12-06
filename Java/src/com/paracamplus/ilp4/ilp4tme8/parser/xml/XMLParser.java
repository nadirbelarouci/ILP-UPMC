/*
 * Correction du TME 8 : accès réflexif aux champs.
 * Année 2015-2016
 *
 * Antoine Miné
 */


package com.paracamplus.ilp4.ilp4tme8.parser.xml;

import org.w3c.dom.Element;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.parser.ParseException;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTfactory;

/*
 *  Ajoute nos nœuds au parser XML.
 */

public class XMLParser extends com.paracamplus.ilp4.parser.xml.XMLParser {

	protected IASTfactory factoryTME8;
	
	public XMLParser(IASTfactory factory) {
		super(factory);
        addMethod("hasProperty", XMLParser.class);
        addMethod("readProperty", XMLParser.class);
        addMethod("writeProperty", XMLParser.class);
		factoryTME8 = factory;
	}
	
	public IASTfactory getFactoryTME8() {
		return factoryTME8;
	}

	public IASTexpression hasProperty (Element e)
			throws ParseException {
        IASTexpression target = narrowToIASTexpression(
                findThenParseChildContent(e, "target"));
        IASTexpression property = narrowToIASTexpression(
                findThenParseChildContent(e, "property"));
        return getFactoryTME8().newExistsProperty(target, property);
    }
    
	public IASTexpression readProperty (Element e)
			throws ParseException {
        IASTexpression target = narrowToIASTexpression(
                findThenParseChildContent(e, "target"));
        IASTexpression property = narrowToIASTexpression(
                findThenParseChildContent(e, "property"));
        return getFactoryTME8().newReadProperty(target, property);
    }
    
	public IASTexpression writeProperty (Element e)
			throws ParseException {
        IASTexpression target = narrowToIASTexpression(
                findThenParseChildContent(e, "target"));
        IASTexpression property = narrowToIASTexpression(
                findThenParseChildContent(e, "property"));
        IASTexpression value = narrowToIASTexpression(
                findThenParseChildContent(e, "value"));
        return getFactoryTME8().newWriteProperty(target, property, value);
    }  
}

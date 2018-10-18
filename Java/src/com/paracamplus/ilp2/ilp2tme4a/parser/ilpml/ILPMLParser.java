package com.paracamplus.ilp2.ilp2tme4a.parser.ilpml;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import antlr4.ILPMLgrammar2tme4Lexer;
import antlr4.ILPMLgrammar2tme4Parser;

import com.paracamplus.ilp2.ilp2tme4.interfaces.IASTfactory;
import com.paracamplus.ilp2.interfaces.IASTprogram;
import com.paracamplus.ilp1.parser.ParseException;

public class ILPMLParser
extends com.paracamplus.ilp2.parser.ilpml.ILPMLParser {
	
	public ILPMLParser(IASTfactory factory) {
		super(factory);
	}
		
	@Override
    public IASTprogram getProgram() throws ParseException {
		try {
			ANTLRInputStream in = new ANTLRInputStream(input.getText());
			// flux de caractères -> analyseur lexical
			ILPMLgrammar2tme4Lexer lexer = new ILPMLgrammar2tme4Lexer(in);
			// analyseur lexical -> flux de tokens
			CommonTokenStream tokens =	new CommonTokenStream(lexer);
			// flux tokens -> analyseur syntaxique
			ILPMLgrammar2tme4Parser parser = new ILPMLgrammar2tme4Parser(tokens);
			// démarage de l'analyse syntaxique
			ILPMLgrammar2tme4Parser.ProgContext tree = parser.prog();		
			// parcours de l'arbre syntaxique et appels du Listener
			ParseTreeWalker walker = new ParseTreeWalker();
			ILPMLListener extractor = new ILPMLListener((IASTfactory)factory);
			walker.walk(extractor, tree);	
			return tree.node;
		} catch (Exception e) {
			throw new ParseException(e);
		}
    }

}

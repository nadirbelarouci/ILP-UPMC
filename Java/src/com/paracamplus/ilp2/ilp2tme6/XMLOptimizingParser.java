package com.paracamplus.ilp2.ilp2tme6;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.normalizer.NormalizationEnvironment;
import com.paracamplus.ilp1.parser.ParseException;
import com.paracamplus.ilp2.ilp2tme4.ast.ASTfactory;
import com.paracamplus.ilp2.interfaces.IASTfactory;
import com.paracamplus.ilp2.interfaces.IASTprogram;
import com.paracamplus.ilp2.parser.xml.XMLParser;

// Cette classe locale étend le parseur pour lui faire appliquer une transformation
// de programme avant de retourner
class XMLOptimizingParser extends XMLParser {

	public XMLOptimizingParser(IASTfactory factory) {
		super(factory);
	}

    @Override
	public IASTprogram getProgram() throws ParseException {
    	try {
        	IASTprogram program = super.getProgram();
			program = (new RenameTransform(new ASTfactory())).visit(program, NormalizationEnvironment.EMPTY);
        	program = (new InlineTransform(new ASTfactory())).visit(program, NormalizationEnvironment.EMPTY);
        	return program;
		} catch (CompilationException e) {
			throw new ParseException(e.getMessage());
		}
    }
}

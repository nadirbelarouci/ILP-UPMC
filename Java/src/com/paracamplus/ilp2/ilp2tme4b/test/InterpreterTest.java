/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ilp2tme4b.test;

import java.io.File;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.test.InterpreterRunner;
import com.paracamplus.ilp1.parser.xml.IXMLParser;
import com.paracamplus.ilp2.ilp2tme4.ast.ASTfactory;
import com.paracamplus.ilp2.ilp2tme4.interfaces.IASTfactory;
import com.paracamplus.ilp2.ilp2tme4b.parser.xml.XMLParser;
import com.paracamplus.ilp2.ilp2tme4b.parser.ilpml.ILPMLParser;


@RunWith(Parameterized.class)
public class InterpreterTest extends com.paracamplus.ilp2.interpreter.test.InterpreterTest {
   
    protected static String[] samplesDirName = { "SamplesTME4", "SamplesILP2", "SamplesILP1" };
	protected static String grammarFile = "XMLGrammars/grammar2-tme4.rng";
    protected static String pattern = ".*";
    
    public InterpreterTest(final File file) {
    	super(file);
    }

    public void configureRunner(InterpreterRunner run) throws EvaluationException {
 	   	super.configureRunner(run);
 	   	// configuration du parseur
 	   	IASTfactory factory = new ASTfactory();
 	   	IXMLParser xmlparser = new XMLParser(factory);
 	   	xmlparser.setGrammar(new File(grammarFile));
 	   	run.setXMLParser(xmlparser);
        run.setILPMLParser(new ILPMLParser(factory));
  }
    
    @Parameters(name = "{0}")
    public static Collection<File[]> data() throws Exception {
    		return InterpreterRunner.getFileList(samplesDirName, pattern);
    }

}

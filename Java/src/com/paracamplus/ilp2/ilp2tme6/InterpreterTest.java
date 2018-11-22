/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ilp2tme6;



import java.io.File;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.test.InterpreterRunner;
import com.paracamplus.ilp1.parser.xml.IXMLParser;
import com.paracamplus.ilp2.ast.ASTfactory;
import com.paracamplus.ilp2.interfaces.IASTfactory;

@RunWith(Parameterized.class)
public class InterpreterTest extends com.paracamplus.ilp2.interpreter.test.InterpreterTest {
   
    public InterpreterTest(final File file) {
    	super(file);
    }    

    public void configureRunner(InterpreterRunner run) throws EvaluationException {
 	   	super.configureRunner(run);
 	   	// configuration du parseur
 	   	IASTfactory factory = new ASTfactory();
 	   	IXMLParser xmparser = new XMLOptimizingParser(factory);
 	   	xmparser.setGrammar(new File(XMLgrammarFile));
 	   	run.setXMLParser(xmparser);
        run.setILPMLParser(new ILPMLOptimizingParser(factory));
   }
    
    @Parameters(name = "{0}")
    public static Collection<File[]> data() throws Exception {
    		return InterpreterRunner.getFileList(samplesDirName, pattern);
    }

}

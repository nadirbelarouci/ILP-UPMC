/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.ilp4tme8.interpreter.test;

import java.io.File;
import java.io.StringWriter;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.paracamplus.ilp1.interpreter.GlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.OperatorEnvironment;
import com.paracamplus.ilp1.interpreter.OperatorStuff;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.interpreter.test.InterpreterRunner;
import com.paracamplus.ilp1.parser.xml.IXMLParser;
import com.paracamplus.ilp3.interpreter.GlobalVariableStuff;
import com.paracamplus.ilp4.ilp4tme8.ast.ASTfactory;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTfactory;
import com.paracamplus.ilp4.ilp4tme8.interpreter.Interpreter;
import com.paracamplus.ilp4.ilp4tme8.parser.ilpml.ILPMLParser;
import com.paracamplus.ilp4.ilp4tme8.parser.xml.XMLParser;
import com.paracamplus.ilp4.interpreter.ClassEnvironment;
import com.paracamplus.ilp4.interpreter.interfaces.IClassEnvironment;

@RunWith(Parameterized.class)
public class InterpreterTest extends com.paracamplus.ilp4.interpreter.test.InterpreterTest {
    	   
	protected static String[] samplesDirName = { "SamplesTME8", "SamplesILP4", "SamplesILP3", "SamplesILP2", "SamplesILP1" };
	protected static String pattern = ".*";
	protected static String grammarFile = "Java/src/com/paracamplus/ilp4/ilp4tme8/grammar/grammar4tme8.rng";

    public InterpreterTest(final File file) {
    	super(file);
    }
    
    public void configureRunner(InterpreterRunner run) throws EvaluationException {
    	super.configureRunner(run);
    	
    	// configuration du parseur
        IASTfactory factory = new ASTfactory();
        IXMLParser xMLParser = new XMLParser(factory);
        xMLParser.setGrammar(new File(grammarFile));
        run.setXMLParser(xMLParser);
        run.setILPMLParser(new ILPMLParser(factory));

        // configuration de l'interprète
        StringWriter stdout = new StringWriter();
        run.setStdout(stdout);
        IGlobalVariableEnvironment gve = new GlobalVariableEnvironment();
        GlobalVariableStuff.fillGlobalVariables(gve, stdout);
        IOperatorEnvironment oe = new OperatorEnvironment();
        OperatorStuff.fillUnaryOperators(oe);
        OperatorStuff.fillBinaryOperators(oe);
        IClassEnvironment ice = new ClassEnvironment(stdout);
        Interpreter interpreter = new Interpreter(gve, oe, ice);
        run.setInterpreter(interpreter);
    }
            
    @Parameters(name = "{0}")
    public static Collection<File[]> data() throws Exception {
    	return InterpreterRunner.getFileList(samplesDirName, pattern);
    }    	
}
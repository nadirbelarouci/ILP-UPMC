package com.paracamplus.ilp2.ilp2tme3;


import java.io.File;
import java.io.StringWriter;
import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import com.paracamplus.ilp1.interpreter.GlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.OperatorEnvironment;
import com.paracamplus.ilp1.interpreter.OperatorStuff;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.interpreter.test.InterpreterRunner;
import com.paracamplus.ilp2.ilp2tme3.primitives.GlobalVariableStuff;
import com.paracamplus.ilp2.interpreter.Interpreter;

public class InterpreterTest extends com.paracamplus.ilp2.interpreter.test.InterpreterTest{
    
    protected static String[] samplesDirName = { "SamplesTME3", "SamplesILP2", "SamplesILP1" };
    
    public InterpreterTest(final File file) {
    	super(file);
    }

    public void configureRunner(InterpreterRunner run) throws EvaluationException {
    	super.configureRunner(run);

    	// reconfiguration de l'interprète
        StringWriter stdout = new StringWriter();
        run.setStdout(stdout);
        IGlobalVariableEnvironment gve = new GlobalVariableEnvironment();
        GlobalVariableStuff.fillGlobalVariables(gve, stdout);
        IOperatorEnvironment oe = new OperatorEnvironment();
        OperatorStuff.fillUnaryOperators(oe);
        OperatorStuff.fillBinaryOperators(oe);
        Interpreter interpreter = new Interpreter(gve, oe);        
        run.setInterpreter(interpreter);
    }

    @Parameters(name = "{0}")
    public static Collection<File[]> data() throws Exception {
    	return InterpreterRunner.getFileList(samplesDirName, pattern);
    }    	
    
}

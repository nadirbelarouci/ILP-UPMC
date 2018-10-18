/* *****************************************************************
 * ilp2 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp2
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ilp2tme4.ilp2tme4c.test;

import java.io.File;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.GlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.GlobalVariableStuff;
import com.paracamplus.ilp1.compiler.OperatorEnvironment;
import com.paracamplus.ilp1.compiler.OperatorStuff;
import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.compiler.optimizer.IdentityOptimizer;
import com.paracamplus.ilp1.compiler.test.CompilerRunner;
import com.paracamplus.ilp1.parser.xml.IXMLParser;
import com.paracamplus.ilp2.ilp2tme4.ilp2tme4c.compiler.Compiler;
import com.paracamplus.ilp2.ilp2tme4.ilp2tme4c.parser.ilpml.ILPMLParser;
import com.paracamplus.ilp2.ilp2tme4.ilp2tme4c.parser.xml.XMLParser;
import com.paracamplus.ilp2.ilp2tme4.ast.ASTfactory;
import com.paracamplus.ilp2.ilp2tme4.interfaces.IASTfactory;

@RunWith(Parameterized.class)
public class CompilerTest extends com.paracamplus.ilp2.compiler.test.CompilerTest{
	    
	   protected static String[] samplesDirName = { "SamplesTME4", "SamplesILP2", "SamplesILP1" };
	   protected static String grammarFile = "XMLGrammars/grammar2-tme4.rng";
	   protected static String pattern = ".*";
	   
		public CompilerTest(final File file) {
			super(file);
	   }
	   
 	  @Override
	   public void configureRunner(CompilerRunner run) throws CompilationException {
		   super.configureRunner(run);
	       IASTfactory factory = new ASTfactory();
	       IXMLParser xmlparser = new XMLParser(factory);
	       xmlparser.setGrammar(new File(grammarFile));
	       run.setXMLParser(xmlparser);
	       run.setILPMLParser(new ILPMLParser(factory));
	       
	       IOperatorEnvironment ioe = new OperatorEnvironment();
	       OperatorStuff.fillUnaryOperators(ioe);
	       OperatorStuff.fillBinaryOperators(ioe);
	       IGlobalVariableEnvironment gve = new GlobalVariableEnvironment();
	       GlobalVariableStuff.fillGlobalVariables(gve);
	       Compiler compiler = new Compiler(ioe, gve);
	       compiler.setOptimizer(new IdentityOptimizer());
	       run.setCompiler(compiler);
	   }
	   
	   @Parameters(name = "{0}")
	   public static Collection<File[]> data() throws Exception {
	   		return CompilerRunner.getFileList(samplesDirName, pattern);
	   }

}

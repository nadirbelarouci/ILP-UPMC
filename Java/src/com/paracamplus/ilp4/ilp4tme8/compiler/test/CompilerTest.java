/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.ilp4tme8.compiler.test;

import java.io.File;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.GlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.OperatorEnvironment;
import com.paracamplus.ilp1.compiler.OperatorStuff;
import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.compiler.optimizer.IdentityOptimizer;
import com.paracamplus.ilp4.compiler.test.CompilerRunner;
import com.paracamplus.ilp1.parser.xml.IXMLParser;
import com.paracamplus.ilp3.compiler.GlobalVariableStuff;
import com.paracamplus.ilp4.ilp4tme8.ast.ASTfactory;
import com.paracamplus.ilp4.ilp4tme8.compiler.Compiler;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTfactory;
import com.paracamplus.ilp4.ilp4tme8.parser.ilpml.ILPMLParser;
import com.paracamplus.ilp4.ilp4tme8.parser.xml.XMLParser;

@RunWith(Parameterized.class)
public class CompilerTest extends com.paracamplus.ilp4.compiler.test.CompilerTest {
    
	protected static String[] samplesDirName = { "SamplesTME8", "SamplesILP4", "SamplesILP3", "SamplesILP2", "SamplesILP1" };
	protected static String pattern = ".*";
	protected static String grammarFile = "Java/src/com/paracamplus/ilp4/ilp4tme8/grammar/grammar4tme8.rng";
    protected static String scriptCommand = "Java/src/com/paracamplus/ilp4/ilp4tme8/C/compileThenRun.sh +gc";
 
	public CompilerTest(final File file) {
		super(file);
	}    

	@Override
   public void configureRunner(CompilerRunner run) throws CompilationException {
	  super.configureRunner(run);
  	// configuration du parseur
      IASTfactory factory = new ASTfactory();
      IXMLParser xMLParser = new XMLParser(factory);
      xMLParser.setGrammar(new File(grammarFile));
      run.setXMLParser(xMLParser);
      run.setILPMLParser(new ILPMLParser(factory));

      // configuration du compilateur
      IOperatorEnvironment ioe = new OperatorEnvironment();
      OperatorStuff.fillUnaryOperators(ioe);
      OperatorStuff.fillBinaryOperators(ioe);
      IGlobalVariableEnvironment gve = new GlobalVariableEnvironment();
      GlobalVariableStuff.fillGlobalVariables(gve);
      Compiler compiler = new Compiler(ioe, gve);
      compiler.setOptimizer(new IdentityOptimizer());
      run.setCompiler(compiler);
      
      run.setRuntimeScript(scriptCommand);
  }
  
  @Parameters(name = "{0}")
  public static Collection<File[]> data() throws Exception {
  	return CompilerRunner.getFileList(samplesDirName, pattern);
  }
  
}

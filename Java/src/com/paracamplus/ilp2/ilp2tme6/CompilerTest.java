/* *****************************************************************
 * ilp2 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp2
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ilp2tme6;

import java.io.File;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.test.CompilerRunner;
import com.paracamplus.ilp1.parser.xml.IXMLParser;
import com.paracamplus.ilp2.ast.ASTfactory;
import com.paracamplus.ilp2.interfaces.IASTfactory;

@RunWith(Parameterized.class)
public class CompilerTest extends com.paracamplus.ilp2.compiler.test.CompilerTest{
	    
		public CompilerTest(final File file) {
			super(file);
	   }
	    
	    @Override
	   public void configureRunner(CompilerRunner run) throws CompilationException {
		   super.configureRunner(run);
	       IASTfactory factory = new ASTfactory();
	       IXMLParser xmlparser = new XMLOptimizingParser(factory);
	       xmlparser.setGrammar(new File(XMLgrammarFile));
	       run.setXMLParser(xmlparser);
	       run.setILPMLParser(new ILPMLOptimizingParser(factory));
   }
	   
	   @Parameters(name = "{0}")
	   public static Collection<File[]> data() throws Exception {
	   		return CompilerRunner.getFileList(samplesDirName, pattern);
	   }

}

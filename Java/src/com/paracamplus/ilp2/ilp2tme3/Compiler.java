/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ilp2tme3;


import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IOperatorEnvironment;



public class Compiler extends com.paracamplus.ilp2.compiler.Compiler {
    
 
    public Compiler(IOperatorEnvironment ioe, IGlobalVariableEnvironment igve) {
		super(ioe, igve);
		cProgramPrefix = ""
	            + "#include <stdio.h> \n"
	            + "#include <stdlib.h> \n"
	            + "#include \"ilp.h\" \n\n"
	    		+ "#include \"ilpSin.h\" \n"
	    		+ "#include \"ilpSin.c\"\n"
	    		;
	}
    
    
}

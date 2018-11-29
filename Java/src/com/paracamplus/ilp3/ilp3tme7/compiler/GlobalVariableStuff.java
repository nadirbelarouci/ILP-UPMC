/*
 * Correction du TME 7 : coroutines.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp3.ilp3tme7.compiler;

import com.paracamplus.ilp1.compiler.Primitive;
import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;

/*
 * Ajoute à fillGlobalVariables les primitives nécessaires au TME.
 */

public class GlobalVariableStuff
	extends com.paracamplus.ilp3.compiler.GlobalVariableStuff {

	public static void fillGlobalVariables (IGlobalVariableEnvironment env) {
		// Appel au parent
		com.paracamplus.ilp3.compiler.GlobalVariableStuff.fillGlobalVariables(env);
		// Ajout des nouvelles primitives
        env.addGlobalFunctionValue(new Primitive("resume", "ILP_resume", 1));
        env.addGlobalFunctionValue(new Primitive("yield", "ILP_yield", 0));
	}
}

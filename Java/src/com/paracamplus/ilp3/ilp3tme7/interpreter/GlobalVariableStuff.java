/*
 * Correction du TME 7 : coroutines.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp3.ilp3tme7.interpreter;

import java.io.Writer;

import com.paracamplus.ilp1.interpreter.interfaces.IGlobalVariableEnvironment;

/*
 * Ajoute les primitives du TME à l'interprète.
 */

public class GlobalVariableStuff extends com.paracamplus.ilp3.interpreter.GlobalVariableStuff {

	   public static void fillGlobalVariables (
	            IGlobalVariableEnvironment env,
	            Writer out ) {
		   // Appel du parent
		   com.paracamplus.ilp3.interpreter.GlobalVariableStuff.fillGlobalVariables(env, out);
		   // Primitives supplémentaires
	        env.addGlobalVariableValue(new Yield());
	        env.addGlobalVariableValue(new Resume());
	 }
	   
}
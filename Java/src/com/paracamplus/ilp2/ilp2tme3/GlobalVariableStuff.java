/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp2.ilp2tme3;

import com.paracamplus.ilp1.compiler.Primitive;
import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;

public class GlobalVariableStuff {
    public static void fillGlobalVariables (IGlobalVariableEnvironment env) {
        env.addGlobalVariableValue("pi", "ILP_PI");
        env.addGlobalFunctionValue(
                new Primitive("print", "ILP_print", 1));
        env.addGlobalFunctionValue(
                new Primitive("newline", "ILP_newline", 0));
        env.addGlobalFunctionValue(
                new Primitive("throw", "ILP_throw", 1));
        env.addGlobalFunctionValue(
                new Primitive("sinus", "ILP_sinus", 1));
        
        env.addGlobalFunctionValue(
                new Primitive("makeVector", "ILP_makeVector", 2));
        env.addGlobalFunctionValue(
                new Primitive("vectorGet", "ILP_vectorGet", 2));
        env.addGlobalFunctionValue(
                new Primitive("vectorLength", "ILP_vectorLength", 1));
    }
}

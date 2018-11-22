package com.paracamplus.ilp2.ilp2tme6;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.normalizer.INormalizationEnvironment;
import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp2.ast.ASTfactory;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp2.interfaces.IASTprogram;

public class InlineTransform 
	extends RenameTransform {

	// Affiche des infos de débuggage sur la transformation
	private boolean debug = true;
	
	
	public InlineTransform(ASTfactory factory) {
		super(factory);
	}


	/*
	 * Champs utilisés dans les visiteurs.
	 * Ils sont resenseignés dans le visiteur point d'entrée, et constants
	 * lors de la visite de l'AST.
	 */
	protected CallAnalysis analysis;
	protected IASTfunctionDefinition[] functions;


	/*
	 * Point d'entrée.
	 */
	
	public IASTprogram visit(IASTprogram iast, INormalizationEnvironment data) 
			throws CompilationException {

		functions = iast.getFunctionDefinitions();
		
		// Calcul des fonctions récursives
		analysis = new CallAnalysis();
		analysis.visit(iast);

		/*
		 * Le parent s'occupe de visiter le programme et d'en retourner une
		 * copie, mais avec les fonctions inlinées.
		 */
		return super.visit(iast, data);
	}

	
	
	/*
	 * Visiteurs redéfinis.
	 */
	
	@Override
	public IASTexpression visit(IASTinvocation iast, INormalizationEnvironment data)
			throws CompilationException {
		
		// Recherche de la fonction 
		// en cas d'echec, le comportement du visiteur de copie est utilisé
		IASTfunctionDefinition function = null;
		if (!(iast.getFunction() instanceof IASTvariable)) {
			return super.visit(iast, data);
		}
		IASTvariable functionVariable = (IASTvariable)iast.getFunction();
		if (analysis.isRecursive(functionVariable)) {
			return super.visit(iast,data);
		}
		String functionName = functionVariable.getName();
		for (IASTfunctionDefinition f : functions) {
			if (f.getFunctionVariable().getName().equals(functionName)) {
				function = f;
			}
		}
		if (function == null) {
			return super.visit(iast,data);
		}
		if (function.getVariables().length != iast.getArguments().length) {
			return super.visit(iast,data);
		}
		
		if (debug) {
			System.out.println("inlining " + functionName);
		}
		
		// Création d'un bloc liant arguments formels et réels
		IASTvariable[] variables = function.getVariables();
		IASTexpression[] arguments = iast.getArguments();
		IASTblock.IASTbinding[] bindings = new IASTblock.IASTbinding[variables.length];
		for (int i=0; i < variables.length; i++) {
			// Ne pas oublier d'inliner l'argument réel de la fonction !
			IASTexpression argument = arguments[i].accept(this,  data);
			bindings[i] = factory.newBinding(variables[i], argument); 
		}
		// Ne pas oublier d'inliner le corps de la fonction
		IASTexpression body = function.getBody().accept(this,  data);
		return factory.newBlock(bindings, body);
	}

	
}

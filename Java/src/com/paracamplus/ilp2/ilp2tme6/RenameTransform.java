package com.paracamplus.ilp2.ilp2tme6;

import com.paracamplus.ilp1.compiler.normalizer.INormalizationEnvironment;
import com.paracamplus.ilp1.compiler.normalizer.NoSuchLocalVariableException;
import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTblock.IASTbinding;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp2.ast.ASTfactory;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp1.compiler.CompilationException;

public class RenameTransform 
extends CopyTransform<INormalizationEnvironment> {

	/* Constructeur */
	public RenameTransform(ASTfactory factory) {
		super(factory);
	}
	

	/*
	 * Génération de noms de variables uniques.
	 * 
	 * On utilise un compteur pour s'assurer de l'unicité.
	 */
	
	protected int counter  = 0;
	
	protected IASTvariable renameVariable(IASTvariable v) {
		counter++;
		String name = v.getName() + "__" + counter;
		return factory.newVariable(name);
	}
	
		
	/* 
	 * Visiteurs redéfinis.
	 * 
	 * On passe un  INormalizationEnvironment en argument pour maintenir
	 * l'association ancien nom de variable -> nouveau nom de variable.
	 */
	

	@Override
	public IASTexpression visit(IASTblock iast, INormalizationEnvironment data)
			throws CompilationException {
		IASTbinding[] oldbinding = iast.getBindings();
		IASTbinding[] binding =new IASTbinding[oldbinding.length];
		INormalizationEnvironment newenv = data;
		// Pour chaque binding
		for (int i = 0; i < oldbinding.length; i++) {
			// Ajout d'une association ancienne variable -> nouvelle variable
			IASTvariable oldv = oldbinding[i].getVariable();
			IASTvariable newv = renameVariable(oldv);
			// Visite de l'expression initialiseur, dans l'_ancien_ environnement
			IASTexpression exp = oldbinding[i].getInitialisation().accept(this, data);			
			// Création d'un nouveau binding
			binding[i] = factory.newBinding(newv, exp);
			// Extension de l'environnement
			newenv = newenv.extend(oldv, newv);
		}
		// Visite du corps, dans le _nouvel_ environnement.
		IASTexpression body = iast.getBody().accept(this, newenv);
		return factory.newBlock(binding, body);
	}


	@Override
	public IASTexpression visit(IASTvariable iast, INormalizationEnvironment data)
			throws CompilationException {
		try {
			// Cas d'une variable locale : remplacement
			return data.renaming(iast);
		}
		catch (NoSuchLocalVariableException v) {
			// Cas d'une variable globale ou primitive : variable inchangée
			return iast;
		}
	}
	
	public IASTfunctionDefinition visit(IASTfunctionDefinition iast, INormalizationEnvironment data) 
			throws CompilationException {
		// Nom de fonction, inchangé
		IASTvariable functionVariable  = (IASTvariable) iast.getFunctionVariable().accept(this,data);
		// Mise à jour de l'environnement
		IASTvariable[] oldvariables = iast.getVariables();
		IASTvariable[] variables = new IASTvariable[oldvariables.length];
		INormalizationEnvironment newenv = data;
		// Pour chaque argument formel de la fonction
		for (int i = 0; i < oldvariables.length; i++) {
			// Ajout d'une association ancienne variable -> nouvelle variable
			IASTvariable oldv = oldvariables[i];
			IASTvariable newv = renameVariable(oldv);
			variables[i] = newv;
			// Extensiond de l'environnement
			newenv = newenv.extend(oldv, newv);
		}
		IASTexpression body = iast.getBody().accept(this, newenv);
		return factory.newFunctionDefinition(functionVariable, variables, body);
	}
	
}

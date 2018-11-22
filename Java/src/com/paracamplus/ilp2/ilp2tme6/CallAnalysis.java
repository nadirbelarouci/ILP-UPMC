package com.paracamplus.ilp2.ilp2tme6;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.interfaces.IASTalternative;
import com.paracamplus.ilp1.interfaces.IASTbinaryOperation;
import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTblock.IASTbinding;
import com.paracamplus.ilp1.interfaces.IASTboolean;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTfloat;
import com.paracamplus.ilp1.interfaces.IASTinteger;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp1.interfaces.IASToperator;
import com.paracamplus.ilp1.interfaces.IASTsequence;
import com.paracamplus.ilp1.interfaces.IASTstring;
import com.paracamplus.ilp1.interfaces.IASTunaryOperation;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp2.interfaces.IASTassignment;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp2.interfaces.IASTloop;
import com.paracamplus.ilp2.interfaces.IASTprogram;
import com.paracamplus.ilp2.interfaces.IASTvisitor;

public class CallAnalysis implements IASTvisitor<Void, Set<String>, CompilationException> {

	// Affiche des infos de débuggage sur l'analyse
	private boolean debug = true;
	
	
	/* 
	 * Méthodes du visiteur.
	 * 
	 * On passe un ensemble en argument. Celui-ci est modifié en place pour
	 * que, au retour, l'ensemble contienne les variables utilisées dans
	 * les appels.
	 */
	
	@Override
	public Void visit(IASTalternative iast, Set<String> data)
			throws CompilationException {
		// Simple visite des sous-nœuds
		iast.getCondition().accept(this,data);
		iast.getConsequence().accept(this, data);
		if (iast.getAlternant() != null) {
			iast.getAlternant().accept(this, data);
		}
		return null;
	}

	@Override
	public Void visit(IASTbinaryOperation iast, Set<String> data)
			throws CompilationException {
		// Simple visite des sous-nœuds
		iast.getLeftOperand().accept(this,data);
		iast.getRightOperand().accept(this,data);
		return null;
	}

	@Override
	public Void visit(IASTblock iast, Set<String> data)
			throws CompilationException {
		// Visite du corps sur un ensemble frais
		Set<String> set = new HashSet<>();
		iast.getBody().accept(this, set);
		// Supression des variables liées dans l'ensemble
		for (IASTbinding b : iast.getBindings()) {
			IASTvariable v = b.getVariable();
			set.remove(v.getName());
			// Visite de l'initialisation dans l'ancien ensemble
			b.getInitialisation().accept(this, data);
		}
		// Ajout du contenu de l'ensemble
		data.addAll(set);
		return null;
	}

	@Override
	public Void visit(IASTboolean iast, Set<String> data)
			throws CompilationException {
		// Rien à faire
		return null;
	}

	@Override
	public Void visit(IASTfloat iast, Set<String> data)
			throws CompilationException {
		// Rien à faire
		return null;
	}

	@Override
	public Void visit(IASTinteger iast, Set<String> data)
			throws CompilationException {
		// Rien à faire
		return null;
	}

	@Override
	public Void visit(IASTinvocation iast, Set<String> data)
			throws CompilationException {
		// Visite des expressions argument réels
		for (IASTexpression e : iast.getArguments()) {
			e.accept(this, data);
		}
		// Détermination de la fonction appelée
		/* LIMITATION
		 * On suppose que l'expression dénotant la fonction appelée est
		 * réduite à une variable, qui dénote une fonction globale...
		 */
		IASTexpression function = iast.getFunction();
		if (function instanceof IASTvariable) {
			data.add(((IASTvariable)function).getName()); 
		}
		else {
			throw new CompilationException("unsupported function call");
		}
		return null;
	}

	@Override
	public Void visit(IASToperator iast, Set<String> data)
			throws CompilationException {
		// Rien à faire
		return null;
	}

	@Override
	public Void visit(IASTsequence iast, Set<String> data)
			throws CompilationException {
		// Simple visite des sous-nœuds
		for (IASTexpression e : iast.getExpressions()) {
			e.accept(this, data);
		}
		return null;
	}

	@Override
	public Void visit(IASTstring iast, Set<String> data)
			throws CompilationException {
		// Rien à faire
		return null;
	}

	@Override
	public Void visit(IASTunaryOperation iast, Set<String> data)
			throws CompilationException {
		// Simple visite des sous-nœuds
		iast.getOperand().accept(this, data);
		return null;
	}

	@Override
	public Void visit(IASTvariable iast, Set<String> data)
			throws CompilationException {
		// Rien à faire
		return null;
	}

	@Override
	public Void visit(IASTassignment iast, Set<String> data)
			throws CompilationException {
		// Simple visite des sous-nœuds
		iast.getExpression().accept(this,data);
		return null;
	}

	@Override
	public Void visit(IASTloop iast, Set<String> data)
			throws CompilationException {
		// Simple visite des sous-nœuds
		iast.getCondition().accept(this,data);
		iast.getBody().accept(this,data);
		return null;
	}
	
	
	/* Visiteur supplémentaire. */
	
	
	public Void visit(IASTfunctionDefinition iast, Set<String> data)
			throws CompilationException {
		// Visite du corps sur un ensemble frais
		Set<String> set = new HashSet<>();
		iast.getBody().accept(this, set);
		// Supression des arguments formels
		for (IASTvariable v : iast.getVariables()) {
			set.remove(v.getName());
		}
		// Ajout du contenu de l'ensemble
		data.addAll(set);
		return null;
	}

	
	/*
	 *  Ensemble des fonctions récursives.
	 *  Calculé par le visiteur IASTprogram.
	 *  Utilisé par isRecursive.
	 */
	
	protected Set<String> recursive = null;

	public boolean isRecursive(IASTvariable var) {
		return recursive.contains(var.getName());
	}

	
	/*
	 * Visite du programme complet et calcul de recursive.
	 */
	
	public void visit(IASTprogram p)
		throws CompilationException {
		// Ensemble des fonctions appelées par chaque fonction
		Map<String, Set<String>> calls = new HashMap<>();
		// Visite de chaque fonction
		for (IASTfunctionDefinition f : p.getFunctionDefinitions()) {
			Set<String> set = new HashSet<>();
			visit(f, set);
			calls.put(f.getFunctionVariable().getName(), set);
		}
		
		// Détection des cycles (algorithme naïf)
		recursive = new HashSet<>();
		// pour chaque variable v
		for (String v : calls.keySet()) {
			// variables accessibles depuis v, par recherche en profondeur
			Set<String> reachable = new HashSet<>();
			Stack<String> todo = new Stack<>();
			// initialisation
			todo.push(v);
			// tant qu'il reste des nœuds à traiter
			while (!todo.empty()) {
				String cur = todo.pop();
				if (reachable.contains(cur)) {
					continue;
				}
				reachable.add(cur);
				// pour chaque successeur next
				Set<String> nexts = calls.get(cur);
				if (nexts != null) {
					for (String next : nexts) {
						// cycle detecté
						if (v.equals(next)) {
							recursive.add(v);
						}
						// marquage des successeurs
						todo.push(next);
					}
				}
			}
		}
		
		// Debug
		if (debug) {
			for (String f : calls.keySet()) {
				for (String g : calls.get(f)) {
					System.out.println(f + " calls " + g);
				}
			}
			for (String f : recursive) {
				System.out.println(f + " is possibly recursive");
			}
		}
	}

}

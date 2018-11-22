package com.paracamplus.ilp2.ilp2tme6;

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
import com.paracamplus.ilp2.ast.ASTfactory;
import com.paracamplus.ilp2.interfaces.IASTassignment;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp2.interfaces.IASTloop;
import com.paracamplus.ilp2.interfaces.IASTprogram;
import com.paracamplus.ilp2.interfaces.IASTvisitor;

public class CopyTransform<Data>
implements IASTvisitor<IASTexpression, Data, CompilationException> {

	protected ASTfactory factory;
	
	/* Constructeur. */
	
	public CopyTransform(ASTfactory factory) {
		this.factory = factory;
	}

	
	/* Méthodes du visiteur. */

	@Override
	public IASTexpression visit(IASTassignment iast, Data data)
			throws CompilationException {
		IASTvariable variable = (IASTvariable) iast.getVariable().accept(this,data);
		IASTexpression value = iast.getExpression().accept(this, data);
		return factory.newAssignment(variable, value);
	}

	@Override
	public IASTexpression visit(IASTloop iast, Data data)
			throws CompilationException {
		IASTexpression condition = iast.getCondition().accept(this,data);
		IASTexpression body = iast.getBody().accept(this,data);
		return factory.newLoop(condition, body);
	}

	@Override
	public IASTexpression visit(IASTalternative iast, Data data)
			throws CompilationException {
		IASTexpression condition = iast.getCondition().accept(this, data);
		IASTexpression consequence = iast.getConsequence().accept(this,data);
		IASTexpression alternant = iast.getAlternant();
		if (alternant != null) {
			alternant = alternant.accept(this,data); 
		}
		return factory.newAlternative(condition, consequence, alternant);
	}

	@Override
	public IASTexpression visit(IASTbinaryOperation iast, Data data)
			throws CompilationException {
		IASTexpression leftOperand = iast.getLeftOperand().accept(this,data);
		IASTexpression rightOperand = iast.getRightOperand().accept(this,data);
		IASToperator operator = iast.getOperator();
		return factory.newBinaryOperation(operator, leftOperand, rightOperand);
	}

	@Override
	public IASTexpression visit(IASTblock iast, Data data)
			throws CompilationException {
		IASTbinding[] oldbinding = iast.getBindings();
		IASTbinding[] binding =new IASTbinding[oldbinding.length];
		IASTexpression body = iast.getBody().accept(this,data);
		for (int i = 0; i < oldbinding.length; i++) {
			IASTvariable v = (IASTvariable) oldbinding[i].getVariable().accept(this,data);
			IASTexpression exp = oldbinding[i].getInitialisation().accept(this, data);
			binding[i] = factory.newBinding(v, exp);
		}
		return factory.newBlock(binding, body);
	}

	@Override
	public IASTexpression visit(IASTboolean iast, Data data)
			throws CompilationException {
		return factory.newBooleanConstant(iast.getValue().toString());
	}

	@Override
	public IASTexpression visit(IASTfloat iast, Data data)
			throws CompilationException {
		return factory.newFloatConstant(iast.getValue().toString());
	}

	@Override
	public IASTexpression visit(IASTinteger iast, Data data)
			throws CompilationException {
		return factory.newIntegerConstant(iast.getValue().toString());
	}

	@Override
	public IASTexpression visit(IASTinvocation iast, Data data)
			throws CompilationException {
		IASTexpression function = iast.getFunction().accept(this, data);
		IASTexpression[] oldarguments = iast.getArguments();
		IASTexpression[] arguments = new IASTexpression[oldarguments.length];
		for (int i = 0; i< oldarguments.length; i++) {
			arguments[i] = oldarguments[i].accept(this, data);
		}
		return factory.newInvocation(function, arguments);
	}

	@Override
	public IASTexpression visit(IASToperator iast, Data data)
			throws CompilationException {
		// Ne devrait pas arriver !
		return null;
	}

	@Override
	public IASTexpression visit(IASTsequence iast, Data data)
			throws CompilationException {
		IASTexpression[] oldasts = iast.getExpressions();
		IASTexpression[] asts = new IASTexpression[oldasts.length];
		for (int i = 0; i < oldasts.length; i++) {
			asts[i] = oldasts[i].accept(this, data);
		}
		return factory.newSequence(asts);
	}

	@Override
	public IASTexpression visit(IASTstring iast, Data data)
			throws CompilationException {
		return factory.newStringConstant(iast.getValue());
	}

	@Override
	public IASTexpression visit(IASTunaryOperation iast, Data data)
			throws CompilationException {
		IASToperator operator = iast.getOperator();
		IASTexpression operand = iast.getOperand().accept(this,data);
		return factory.newUnaryOperation(operator, operand);
	}

	@Override
	public IASTexpression visit(IASTvariable iast, Data data)
			throws CompilationException {
		return factory.newVariable(iast.getName());
	}
	
	
	/* Méthodes supplémentaires du visiteur. */
	
	public IASTfunctionDefinition visit(IASTfunctionDefinition iast, Data data) 
			throws CompilationException {
		IASTvariable functionVariable  = (IASTvariable) iast.getFunctionVariable().accept(this,data);
		IASTvariable[] oldvariables = iast.getVariables();
		IASTvariable[] variables = new IASTvariable[oldvariables.length];
		IASTexpression body = iast.getBody().accept(this, data);
		for (int i = 0; i < oldvariables.length; i++) {
			variables[i] = (IASTvariable) oldvariables[i].accept(this,data);
		}
		return factory.newFunctionDefinition(functionVariable, variables, body);
	}
	
	public IASTprogram visit(IASTprogram iast, Data data) 
			throws CompilationException {
		IASTexpression expression = iast.getBody().accept(this, data);
		IASTfunctionDefinition[] oldfunctions = iast.getFunctionDefinitions();
		IASTfunctionDefinition[] functions = new IASTfunctionDefinition[oldfunctions.length];
		for (int i = 0; i < oldfunctions.length; i++) {
			functions[i] = visit(oldfunctions[i], data);
		}
		return factory.newProgram(functions, expression);
	}

}

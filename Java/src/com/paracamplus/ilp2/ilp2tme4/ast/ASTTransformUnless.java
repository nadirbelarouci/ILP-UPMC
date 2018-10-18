package com.paracamplus.ilp2.ilp2tme4.ast;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.interfaces.IASTalternative;
import com.paracamplus.ilp1.interfaces.IASTbinaryOperation;
import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTboolean;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTfloat;
import com.paracamplus.ilp1.interfaces.IASTinteger;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp1.interfaces.IASToperator;
import com.paracamplus.ilp2.interfaces.IASTprogram;
import com.paracamplus.ilp1.interfaces.IASTsequence;
import com.paracamplus.ilp1.interfaces.IASTstring;
import com.paracamplus.ilp1.interfaces.IASTunaryOperation;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp2.ilp2tme4.interfaces.IASTunless;
import com.paracamplus.ilp2.ilp2tme4.interfaces.IASTvisitor;
import com.paracamplus.ilp1.interfaces.IASTblock.IASTbinding;
import com.paracamplus.ilp2.interfaces.IASTassignment;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp2.interfaces.IASTloop;

public class ASTTransformUnless implements 
 IASTvisitor<IASTexpression, Void, CompilationException> {

    public ASTTransformUnless (com.paracamplus.ilp2.interfaces.IASTfactory oldFactory) {
        this.factory = oldFactory;
    }
    protected final com.paracamplus.ilp2.interfaces.IASTfactory factory;

    public IASTprogram transformUnless (IASTprogram program) 
            throws CompilationException {
        IASTfunctionDefinition[] functions = program.getFunctionDefinitions();
        IASTfunctionDefinition[] funs = 
                new IASTfunctionDefinition[functions.length];
        for ( int i=0 ; i<functions.length ; i++ ) {
            IASTfunctionDefinition function = functions[i];
            IASTfunctionDefinition newfunction = visit(function, null);
            funs[i] = newfunction;
        }
        
        IASTexpression body = program.getBody();
        IASTexpression newbody = body.accept(this, null);
        return factory.newProgram(funs, newbody);
    }
    
    @Override
	public IASTexpression visit(IASTalternative iast, Void env)
            throws CompilationException {
        IASTexpression c = iast.getCondition().accept(this, env);
        IASTexpression t = iast.getConsequence().accept(this, env);
        if ( iast.isTernary() ) {
            IASTexpression a = iast.getAlternant().accept(this, env);
            return factory.newAlternative(c, t, a);
        } else {
            IASTexpression whatever = factory.newBooleanConstant("false");
            return factory.newAlternative(c, t, whatever);
        }
    }
    
    @Override
	public IASTexpression visit(IASTboolean iast, Void env)
            throws CompilationException {
        return iast;
    }
    
    @Override
	public IASTexpression visit(IASTinteger iast, Void env)
            throws CompilationException {
        return iast;
    }

    @Override
	public IASTexpression visit(IASTfloat iast, Void env)
            throws CompilationException {
        return iast;
    }

    @Override
	public IASTexpression visit(IASTstring iast, Void env)
            throws CompilationException {
        return iast;
    }

    @Override
	public IASTexpression visit(IASTsequence iast, Void env)
            throws CompilationException {
        IASTexpression[] expressions = iast.getExpressions();
        IASTexpression[] exprs = new IASTexpression[expressions.length];
        for ( int i=0 ; i< expressions.length ; i++ ) {
            exprs[i] = expressions[i].accept(this, env);
        }
        if ( iast.getExpressions().length == 1 ) {
            return exprs[0];
        } else {
            return factory.newSequence(exprs);
        }
    }

    @Override
	public IASTvariable visit(IASTvariable iast, Void env)
            throws CompilationException {
        return iast;
    }
    

    @Override
	public IASTexpression visit(IASTunaryOperation iast, Void env)
            throws CompilationException {
        IASToperator operator = iast.getOperator();
        IASTexpression operand = iast.getOperand().accept(this, env);
        return factory.newUnaryOperation(operator, operand);
    }

    @Override
	public IASTexpression visit(IASTbinaryOperation iast, Void env)
            throws CompilationException {
        IASToperator operator = iast.getOperator();
        IASTexpression left = iast.getLeftOperand().accept(this, env);
        IASTexpression right = iast.getRightOperand().accept(this, env);
        return factory.newBinaryOperation(operator, left, right);
    }
    
    @Override
	public IASTexpression visit(IASToperator iast, Void env)
            throws CompilationException {
        throw new RuntimeException("Already processed via Operation");
    }

    @Override
	public IASTexpression visit(IASTblock iast, Void env)
            throws CompilationException {
        Void newenv = env;
        IASTbinding[] bindings = iast.getBindings();
        IASTblock.IASTbinding[] newbindings = 
                new IASTblock.IASTbinding[bindings.length];
        for ( int i=0 ; i<bindings.length ; i++ ) {
            IASTbinding binding = bindings[i];
            IASTexpression expr = binding.getInitialisation();
            IASTexpression newexpr = expr.accept(this, env);
            IASTvariable variable = binding.getVariable();
            newbindings[i] = factory.newBinding(variable, newexpr);
        }
        IASTexpression newbody = iast.getBody().accept(this, newenv);
        return factory.newBlock(newbindings, newbody);
    }


    @Override
	public IASTexpression visit(IASTassignment iast, Void env)
            throws CompilationException {
        IASTvariable variable = iast.getVariable();
        IASTvariable newvariable = visit(variable, env);
        IASTexpression expression = iast.getExpression();
        IASTexpression newexpression = expression.accept(this, env);
        return factory.newAssignment(newvariable, newexpression);
    }

    
	public IASTfunctionDefinition visit(
            IASTfunctionDefinition iast,
            Void env) throws CompilationException {
    	IASTvariable[] variables = iast.getVariables();
        Void newenv = env;
        IASTexpression newbody = iast.getBody().accept(this, newenv);
        IASTvariable functionVariable = iast.getFunctionVariable();
        return factory.newFunctionDefinition(
                functionVariable, variables, newbody);
    }


    @Override
	public IASTexpression visit(IASTinvocation iast, 
                                Void env)
            throws CompilationException {
        IASTexpression funexpr = iast.getFunction().accept(this, env);
        IASTexpression[] arguments = iast.getArguments();
        IASTexpression[] args = new IASTexpression[arguments.length];
        for ( int i=0 ; i<arguments.length ; i++ ) {
            IASTexpression argument = arguments[i];
            IASTexpression arg = argument.accept(this, env);
            args[i] = arg;
        }
        return factory.newInvocation(funexpr, args);
    	}
    

    @Override
	public IASTexpression visit(IASTloop iast, Void env)
            throws CompilationException {
        IASTexpression newcondition = iast.getCondition().accept(this, env);
        IASTexpression newbody = iast.getBody().accept(this, env);
        return factory.newLoop(newcondition, newbody);
    }
    
    @Override
	public IASTexpression visit(IASTunless iast, Void env)
            throws CompilationException {
    	  IASTexpression c = iast.getCondition().accept(this, env);
          IASTexpression t = iast.getBody().accept(this, env);
          IASTexpression whatever = factory.newBooleanConstant("false");
          return factory.newAlternative(c, whatever, t);
    }
}
    
/*
 * Correction du TME 8 : accès réflexif aux champs.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp4.ilp4tme8.interpreter;

import java.util.List;
import java.util.Vector;

import com.paracamplus.ilp4.ilp4tme8.interfaces.IASThasProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTreadProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTvisitor;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTwriteProperty;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp4.interfaces.IASTinstantiation;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp4.interpreter.interfaces.IClass;
import com.paracamplus.ilp4.interpreter.interfaces.IClassEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.ILexicalEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.IOperatorEnvironment;

/*
 * Ajoute nos nouveaux nœuds.
 */

public class Interpreter 
	extends com.paracamplus.ilp4.interpreter.Interpreter 
	implements IASTvisitor<Object, ILexicalEnvironment, EvaluationException>
	{

	public Interpreter(
			IGlobalVariableEnvironment globalVariableEnvironment,
			IOperatorEnvironment operatorEnvironment,
			IClassEnvironment classEnvironment) {
		super(globalVariableEnvironment, operatorEnvironment, classEnvironment);
	}

	
	/*
	 *  Visite des nouveaux nœuds.
	 */
	
	@Override
	public Object visit(IASThasProperty iast, ILexicalEnvironment lexenv)
			throws EvaluationException {
		// Évaluation des arguments
        Object target = iast.getTarget().accept(this, lexenv);
        Object property = iast.getProperty().accept(this, lexenv);
        
        // Vérification de type
        if (!(target instanceof ILP9Instance))
        	throw new EvaluationException("Target is not an instance "+ target);
        if (!(property instanceof String))
        	throw new EvaluationException("Property is not a string "+ property);
        ILP9Instance itarget = (ILP9Instance)target;
        String sproperty = (String)property;
        
        // Accès à l'objet
        return new Boolean(itarget.hasProperty(sproperty));
	}

	@Override
	public Object visit(IASTreadProperty iast, ILexicalEnvironment lexenv)
			throws EvaluationException {
		// Évaluation des arguments
        Object target = iast.getTarget().accept(this, lexenv);
        Object property = iast.getProperty().accept(this, lexenv);
        
        // Vérification de type
        if (!(target instanceof ILP9Instance))
        	throw new EvaluationException("Target is not an instance "+ target);
        if (!(property instanceof String))
        	throw new EvaluationException("Property is not a string "+ property);
        ILP9Instance itarget = (ILP9Instance)target;
        String sproperty = (String)property;

        // Accès à l'objet
        return itarget.readProperty(sproperty);
	}

	@Override
	public Object visit(IASTwriteProperty iast, ILexicalEnvironment lexenv)
			throws EvaluationException {
		// Évaluation des arguments
        Object target = iast.getTarget().accept(this, lexenv);
        Object property = iast.getProperty().accept(this, lexenv);
        Object value = iast.getValue().accept(this, lexenv);
        
        // Vérification de type
        if (!(target instanceof ILP9Instance))
        	throw new EvaluationException("Target is not an instance "+ target);
        if (!(property instanceof String))
        	throw new EvaluationException("Property is not a string "+ property);
        ILP9Instance itarget = (ILP9Instance)target;
        String sproperty = (String)property;

        // Accès à l'objet
        return itarget.writeProperty(sproperty, value);
	}

	
	/*
	 * Mise à jour de la création d'instance, pour créer nos instances
	 * étendues au lieu de ILP9Intance.
	 */
	
	@Override
    public Object visit(IASTinstantiation iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        IClass clazz = getClassEnvironment().getILP9Class(iast.getClassName());
        List<Object> args = new Vector<Object>();
        for ( IASTexpression arg : iast.getArguments() ) {
            Object value = arg.accept(this, lexenv);
            args.add(value);
        }
        return new ILP9Instance(clazz, args.toArray());
    }    

}

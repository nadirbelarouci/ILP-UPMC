/*
 * Correction du TME 8 : accès réflexif aux champs.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp4.ilp4tme8.interpreter.interfaces;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;

/*
 * Enrichit l'interface des instances de classes avec des méthodes
 * pour l'accès aux champs dynamiques.
 * 
 * Les méthodes read et write de IInstance gardent leur sémantique :
 * accès iniquement aux champs déclarés dans les classes.
 */

public interface IInstance
	extends com.paracamplus.ilp4.interpreter.interfaces.IInstance {
	
	public boolean hasProperty(String fieldName)
        throws EvaluationException;
	
	public Object readProperty(String fieldName) 
		throws EvaluationException;
	
	public Object writeProperty(String fieldName, Object value)
		throws EvaluationException;

}	
	

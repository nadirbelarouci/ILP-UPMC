/*
 * Correction du TME 8 : accès réflexif aux champs.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp4.ilp4tme8.interfaces;

import com.paracamplus.ilp1.interfaces.IASTexpression;

/*
 * Ajout de nos nœuds à la fabrique.
 */

public interface IASTfactory extends com.paracamplus.ilp4.interfaces.IASTfactory {

	IASTexpression newExistsProperty(
			IASTexpression target,
			IASTexpression property);
	
	IASTexpression newReadProperty(
			IASTexpression target,
			IASTexpression property);
	
	IASTexpression newWriteProperty(
			IASTexpression target,
			IASTexpression property,
			IASTexpression value);
	
}

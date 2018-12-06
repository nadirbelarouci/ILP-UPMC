/*
 * Correction du TME 8 : accès réflexif aux champs.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp4.ilp4tme8.interfaces;

import com.paracamplus.ilp1.interfaces.IASTexpression;

/*
 * Interface de hasProperty.
 */

public interface IASThasProperty extends IASTproperty {
	IASTexpression getTarget();
    IASTexpression getProperty();
}

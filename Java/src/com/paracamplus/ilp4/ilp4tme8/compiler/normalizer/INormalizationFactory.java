/*
 * Correction du TME 8 : accès réflexif aux champs.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp4.ilp4tme8.compiler.normalizer;

import com.paracamplus.ilp1.interfaces.IASTexpression;

/*
 * Ajoute nos nœuds à la fabrique.
 */

public interface INormalizationFactory
	extends com.paracamplus.ilp4.compiler.normalizer.INormalizationFactory {

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

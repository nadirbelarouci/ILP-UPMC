/*
 * Correction du TME 8 : accès réflexif aux champs.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp4.ilp4tme8.interfaces;

import com.paracamplus.ilp1.interfaces.IASTexpression;

/*
 * Interface de 'marquage' (tag interface).
 * 
 * Tous les nœuds en rapport avec les champs dynamiques sont 
 * marqués par cette interface, ce qui permet de le reconnaître plus
 *  facilement (en particulier avec un instanceof).
 */

public interface IASTproperty extends IASTexpression {

}

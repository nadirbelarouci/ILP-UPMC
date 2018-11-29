/*
 * Correction du TME 7 : coroutines.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp3.ilp3tme7.compiler;

import com.paracamplus.ilp3.ilp3tme7.compiler.interfaces.IASTCglobalStart;
import com.paracamplus.ilp2.compiler.ast.ASTCglobalInvocation;
import com.paracamplus.ilp1.interfaces.IASTexpression;

/*
 * Spécialisation du nœud ASTstart où la fonction est de type 
 * IASTCglobalVariable.
 * Basé sur le principe de ASTCglobalInvocation vs.  ASTinvocation.
 */

public class ASTCglobalStart
  extends ASTCglobalInvocation 
  implements IASTCglobalStart {

	public ASTCglobalStart(IASTexpression function, IASTexpression[] arguments) {
		super(function, arguments);
	}

}

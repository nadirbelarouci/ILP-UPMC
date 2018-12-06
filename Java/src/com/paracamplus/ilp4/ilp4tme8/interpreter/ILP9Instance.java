/*
 * Correction du TME 8 : accès réflexif aux champs.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp4.ilp4tme8.interpreter;

import java.util.HashMap;
import java.util.Map;

import com.paracamplus.ilp4.ilp4tme8.interpreter.interfaces.IInstance;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp4.interpreter.interfaces.IClass;

/*
 * Extension du type d'instance d'objet.
 * 
 * On utilise une table d'association dynamique pour stocker les 
 * champs non déclarés dans la classe (ni hérités) ; ceux-ci 
 * sont ajoutés par IASTwriteProperty.

 * Les champs déclarés restent gérés par ILP9Instance. 
 * Ils peuvent être accédés à la fois par IASTfieldRead, IASTfieldWrite et
 *  par IASThasProperty, IASTreadProperty, IASTwriteProperty.
 * 
 * Les champs non déclarés ne sont pas accessibles par IASTfieldRead,
 * IASTfieldWrite.
 */

public class ILP9Instance 
  extends com.paracamplus.ilp4.interpreter.ILP9Instance
  implements IInstance {

	protected Map<String,Object> properties;	
	
	public ILP9Instance(IClass clazz, Object[] fields)
			throws EvaluationException {
		super(clazz, fields);
		// le champs properties est alloué de manière paresseuse
	}


	/*
	 * On réutilise les fonctions read et write d'ILP9Instance, en exploitant
	 * le fait qu'elle renvoient une exception si le champ n'est pas déclaré
	 * (ou hérité) dans la classe de l'objet.
	 */
	
	public boolean hasProperty(String fieldName)
        throws EvaluationException {
		try {
			classOf().getOffset(fieldName);
			// si on arrive ici sans exception, c'est que le champ est déclaré
			return true;
		}
		catch (EvaluationException e) {
			// champ non déclaré => test de champ dynamique
			return (properties != null) && properties.containsKey(fieldName);
		}
	}
	
	public Object readProperty(String fieldName) 
		throws EvaluationException {
		try {
			return read(fieldName);
		}
		catch (EvaluationException e) {
			// champ non déclaré => lecture de champ dynamique
			if ((properties == null || !properties.containsKey(fieldName)))
				throw new EvaluationException(
						"Object has no property " + fieldName);
			return properties.get(fieldName);
		}
	}
	
	public Object writeProperty(String fieldName, Object value)
		throws EvaluationException {
		try {
			return write(fieldName, value);
		}
		catch (EvaluationException e) {
			// champ non déclaré
			// => création ou mise à jour de champ dynamique
			if (properties==null) {
				// création paresseuse de la table de champs dynamiques
				properties = new HashMap<>();
			}
			Object r = properties.put(fieldName, value);
			if (r==null) {
				// Cas où la propriété n'existait pas avant => on renvoie false
				return new Boolean(false);
			}
			return r;
		}
	}
	
}

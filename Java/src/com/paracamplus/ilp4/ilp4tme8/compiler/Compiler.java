/*
 * Correction du TME 8 : accès réflexif aux champs.
 * Année 2015-2016
 *
 * Antoine Miné
 */

package com.paracamplus.ilp4.ilp4tme8.compiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Set;

import com.paracamplus.ilp4.ilp4tme8.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp4.ilp4tme8.compiler.normalizer.INormalizationFactory;
import com.paracamplus.ilp4.ilp4tme8.compiler.normalizer.NormalizationFactory;
import com.paracamplus.ilp4.ilp4tme8.compiler.normalizer.Normalizer;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASThasProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTreadProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTwriteProperty;
import com.paracamplus.ilp1.compiler.AssignDestination;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.NoDestination;
import com.paracamplus.ilp4.compiler.interfaces.IASTCclassDefinition;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp4.compiler.interfaces.IASTCmethodDefinition;
import com.paracamplus.ilp4.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp4.interfaces.IASTprogram;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.Inamed;

/*
 * Ajoute le support aux nouveaux nœuds dans le compilateur.
 * 
 * Aussi : adaptation du code généré au changement de structure
 * des ILP_Object.
 * 
 */

public class Compiler 
	extends com.paracamplus.ilp4.compiler.Compiler
	implements IASTCvisitor<Void, Compiler.Context, CompilationException> {

	public Compiler(IOperatorEnvironment ioe,
			IGlobalVariableEnvironment igve) {
		super(ioe, igve);
	}

	
	/*
	 * Utilisation du nouveau Normalizer.
	 */
	public IASTCprogram normalize(IASTprogram program, 
            IASTCclassDefinition objectClass) 
            		throws CompilationException {
		INormalizationFactory nf = new NormalizationFactory();
		Normalizer normalizer = new Normalizer(nf, objectClass);
		IASTCprogram newprogram = normalizer.transform(program);
		return newprogram;
	}

	
	/*
	 * Utilisation des nouveaux collecteurs de variables
	 */	
	public String compile(IASTprogram program, 
			IASTCclassDefinition objectClass) 
					throws CompilationException {
	
		IASTCprogram newprogram = normalize(program, objectClass);
		newprogram = (IASTCprogram) optimizer.transform(newprogram);

		GlobalVariableCollector gvc = new GlobalVariableCollector();
		Set<IASTCglobalVariable> gvs = gvc.analyze(newprogram);
		newprogram.setGlobalVariables(gvs);

		FreeVariableCollector fvc = new FreeVariableCollector(newprogram);
		newprogram = fvc.analyze();

		Context context = new Context(NoDestination.NO_DESTINATION);
		StringWriter sw = new StringWriter();
		try {
			out = new BufferedWriter(sw);
			visit(newprogram, context);
			out.flush();
		} catch (IOException exc) {
			throw new CompilationException(exc);
		}
		return sw.toString();
	}


	/*
	 * Génération de code pour nos nouveaux nœuds.
	 * 
	 */
	
	@Override
	public Void visit(IASThasProperty iast, Context context)
			throws CompilationException {
        emit("{ \n");

        // Compilation des arguments
        IASTvariable tmpInstance = context.newTemporaryVariable();
        IASTvariable tmpProperty = context.newTemporaryVariable();
        emit("  ILP_Object " + tmpInstance.getMangledName() + "; \n");
        emit("  ILP_Object " + tmpProperty.getMangledName() + "; \n");
        Context cInstance = context.redirect(new AssignDestination(tmpInstance));
        Context cProperty = context.redirect(new AssignDestination(tmpProperty));
        iast.getTarget().accept(this, cInstance);
        iast.getProperty().accept(this, cProperty);

        // Emission du code
        emit(context.destination.compile());
        emit("ILP_hasProperty(");
        emit(tmpInstance.getMangledName());
        emit(", ");
        emit(tmpProperty.getMangledName());
        emit(");\n");
        emit("}\n");
        return null;
	}

	@Override
	public Void visit(IASTreadProperty iast, Context context)
			throws CompilationException {
        emit("{ \n");

        // Compilation des arguments
        IASTvariable tmpInstance = context.newTemporaryVariable();
        IASTvariable tmpProperty = context.newTemporaryVariable();
        emit("  ILP_Object " + tmpInstance.getMangledName() + "; \n");
        emit("  ILP_Object " + tmpProperty.getMangledName() + "; \n");
        Context cInstance = context.redirect(new AssignDestination(tmpInstance));
        Context cProperty = context.redirect(new AssignDestination(tmpProperty));
        iast.getTarget().accept(this, cInstance);
        iast.getProperty().accept(this, cProperty);

        // Emission du code
        emit(context.destination.compile());
        emit("ILP_readProperty(");
        emit(tmpInstance.getMangledName());
        emit(", ");
        emit(tmpProperty.getMangledName());
        emit(");\n");
        emit("}\n");
        return null;
	}

	@Override
	public Void visit(IASTwriteProperty iast, Context context)
			throws CompilationException {
        emit("{ \n");

        // Compilation des arguments
        IASTvariable tmpInstance = context.newTemporaryVariable();
        IASTvariable tmpProperty = context.newTemporaryVariable();
        IASTvariable tmpValue = context.newTemporaryVariable();
        emit("  ILP_Object " + tmpInstance.getMangledName() + "; \n");
        emit("  ILP_Object " + tmpProperty.getMangledName() + "; \n");
        emit("  ILP_Object " + tmpValue.getMangledName() + "; \n");
        Context cInstance = context.redirect(new AssignDestination(tmpInstance));
        Context cProperty = context.redirect(new AssignDestination(tmpProperty));
        Context cValue = context.redirect(new AssignDestination(tmpValue));
        iast.getTarget().accept(this, cInstance);
        iast.getProperty().accept(this, cProperty);
        iast.getValue().accept(this, cValue);

        // Emission du code
        emit(context.destination.compile());
        emit("ILP_writeProperty(");
        emit(tmpInstance.getMangledName());
        emit(", ");
        emit(tmpProperty.getMangledName());
        emit(", ");
        emit(tmpValue.getMangledName());
        emit(");\n");
        emit("}\n");
        return null;
	}
	
	
	/*
	 * La structure de ILP_Object ayant changé, il nous faut redéfinir
	 * toutes les méthodes qui émettent des ILP_Object constants
	 * dans le code C généré.
	 */

	// Recopié de Compiler, sauf ce qui indiqué explicitement comme changé
	@Override
	protected void emitClosure(IASTfunctionDefinition iast, Context context)
            throws CompilationException {
        emit("struct ILP_Closure ");
        emit(iast.getMangledName());
        emit("_closure_object = { \n");
        emit("   &ILP_object_Closure_class, NULL, \n"); // Changement TME8
        emit("   { { ilp__");
        emit(iast.getMangledName());
        emit(", \n");
        emit("       " + iast.getVariables().length + ", \n");
        emit("       { NULL } } } \n");
        emit("}; \n");      
    }

	// Recopié de Compiler, sauf ce qui indiqué explicitement comme changé
	@Override
    public Void visit(IASTCclassDefinition iast, Context context)
            throws CompilationException {
        String lastFieldName = "NULL";
        int inheritedFieldsCount = 0;
        if ( ! "Object".equals(iast.getSuperClassName()) ) {
            IASTCclassDefinition superClass = iast.getSuperClass();
            String[] fieldNames = superClass.getTotalFieldNames();
            inheritedFieldsCount = fieldNames.length;
            if ( inheritedFieldsCount > 0 ) {
                String fieldName = fieldNames[inheritedFieldsCount - 1];
                String mangledFieldName = Inamed.computeMangledName(fieldName);
                lastFieldName = "&ILP_object_" + mangledFieldName + "_field";
            }
        }
        String[] fieldNames = iast.getProperFieldNames();
        for ( int i=0 ; i<fieldNames.length ; i++ ) {
            String mangledFieldName = Inamed.computeMangledName(fieldNames[i]);
            emit("\nstruct ILP_Field ILP_object_");
            emit(mangledFieldName);
            emit("_field = {\n  &ILP_object_Field_class, NULL, \n     { { "); // Changement TME8
            emit("(ILP_Class) &ILP_object_");
            emit(iast.getMangledName());
            emit("_class,\n   ");
            emit(lastFieldName);
            emit(",\n    \"");
            emit(mangledFieldName);
            emit("\",\n  ");
            emit(i + inheritedFieldsCount);
            emit(" } }\n};\n");
            lastFieldName = "&ILP_object_" + mangledFieldName + "_field";
        }

        emit("\nstruct ILP_Class");
        emit(iast.getTotalMethodDefinitionsCount());
        emit(" ILP_object_");
        emit(iast.getMangledName());
        emit("_class = {\n  &ILP_object_Class_class, NULL, \n  { { "); // Changement TME8
        emit("(ILP_Class) &ILP_object_");
        emit(iast.getSuperClass().getMangledName());
        emit("_class,\n         \"");
        emit(iast.getMangledName());
        emit("\",\n         ");
        emit(inheritedFieldsCount + fieldNames.length);
        emit(",\n         ");
        emit(lastFieldName);
        emit(",\n         ");
        emit(iast.getTotalMethodDefinitionsCount());
        emit(",\n { ");
        for ( IASTCmethodDefinition md : iast.getTotalMethodDefinitions() ) {
            emit(md.getCName());
            emit(", \n");
        }
        emit(" } } }\n};\n");
        
        IASTCmethodDefinition[] methods = iast.getNewProperMethodDefinitions();
        for ( int i = 0 ; i<methods.length ; i++ ) {
            IASTCmethodDefinition method = methods[i];
            if ( ! alreadyGeneratedMethodObject.containsKey(method.getName()) ) {
                emit("\nstruct ILP_Method ILP_object_");
                emit(Inamed.computeMangledName(method.getMethodName()));
                emit("_method = {\n  &ILP_object_Method_class, NULL, \n  { { "); // Changement TME8
                emit("(struct ILP_Class*) &ILP_object_");
                emit(iast.getMangledName());
                emit("_class,\n  \"");
                emit(method.getMethodName());
                emit("\",\n  ");
                emit(method.getVariables().length);
                emit(",  /* arité */\n  ");
                emit(iast.getMethodOffset(method));
                emit(" /* offset */ \n    } }\n};\n");
            }
        }
        return null;
    }

}

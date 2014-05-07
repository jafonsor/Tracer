package ist.meic.pa;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.expr.ConstructorCall;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;

public class TracingEditor extends ExprEditor {
	
	private static String signatureOf(MethodCall call) {
		String signature;
		try {
			signature = call.getMethod().getName();

		
			signature += "(";
	
			CtClass[] parameterTypes = call.getMethod().getParameterTypes();
			if(parameterTypes.length > 0) {
				signature += parameterTypes[0].getName();
				for(int i = 1; i < parameterTypes.length; i++) {
					signature += parameterTypes[i];
				}
			}
			
			signature += ")";
		} catch (NotFoundException e) {
			throw new RuntimeException(e);
		}
		return signature;
	}

	public void edit(NewExpr call) {
		/*
		try {
		//	call.replace("{"
		//			+ "$_ = $proceed($$);"
		//			+ "System.out.println($_ + \"." + call.getClassName() + "\");" + "}");
		} catch (CannotCompileException e) {
			throw new RuntimeException(e);
		}*/
	}
	
	public void edit(MethodCall call) {
		try {
			call.replace("{"
					+ "$_ = $proceed($$);"
					+ "ist.meic.pa.History.logMethodCall(($w)$_, \"" + signatureOf(call) + "\",($w)$args);"
					+ "}"
			);
		} catch (CannotCompileException e) {
			throw new RuntimeException(e);
		}
	}
}

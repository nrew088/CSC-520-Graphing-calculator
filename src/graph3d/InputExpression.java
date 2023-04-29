package graph3d;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mariuszgromada.math.mxparser.Function;

public class InputExpression {
	// Name:         The f in f(x,y,z) = ... or the v in v = ... notations
	// Parameters:   The x,y,z in f(x,y,z) = ... notation
	// Dependencies: All parameters and any variables referenced in the RHS
	//               that are not in the parameter list
	// Variables:    All names that are currently in use
	
	String name;
	TreeSet<String> parameters = new TreeSet<String>();
	LinkedList<String> dependencies = new LinkedList<String>();
	Function parsedFunction;
	// Expression parsedExpression;

	public static TreeSet<String> variables = new TreeSet<String>();
	
	final public static TreeSet<String> specialVars = new TreeSet<String>
		(Arrays.asList("x", "y", "z"));
	
	// TODO: allow empty expression and fix update() code to handle it
	final private Pattern exprPat = Pattern.compile
		("(?<v>[a-zA-Z_]+)\\s*\\(?(?<p>[a-zA-Z_,]+)*\\)?\\s*=\\s*(?<e>.*)");
	
	public InputExpression() {
		
	}
	
	// 1: Evaluate all InputExpressions referenced in the dependencies
	// 2: Substitute them by name and figure out the rest of step 2
	public double evaluate() {
		return 1;
	}
	
	// Update dependencies and other internal data, but don't evaluate
	public void update(String rawExpr) {
		// Example: f(x,y) = x^2 + y^2
		// Example: z = x^2 + y^2
		System.out.println(rawExpr);
		
		Matcher m = exprPat.matcher(rawExpr);
		
		if(m.matches()) {
			System.out.println("Match success!");
			System.out.println("Name   : " + m.group("v"));
			System.out.println("Params : " + m.group("p"));
			System.out.println("Expr   : " + m.group("e"));
			
			String
				inName   = m.group("v"),
				inParams = m.group("p"),
				inExpr   = m.group("e");
			// Hooray!
			
			// Update the list of variables visible everywhere
			if(inName != name) {
				if(name != null && variables.contains(name))
					variables.remove(name);
				variables.add(name = inName);
			}
			
			parameters = new TreeSet<String>(Arrays.asList(
				inParams == null ? new String[]{} : inParams.split(",")
			));
			
			// Reassess dependencies
			dependencies.clear();
			
			// 1: Parameter dependenciess
			for(String p : parameters)
				dependencies.add(p);
			
			// 2: Special variable dependencies
			for(String v : specialVars)
			if (inExpr.contains(v)    )
				dependencies.add(v);
			
			// 3: Referenced variable dependencies
			for(String n : variables)
			if (inExpr.contains(n)  )
				dependencies.add(n);
			
			System.out.println("Name         : " + name);
			System.out.println("Parameters   : " + parameters);
			System.out.println("Dependencies : " + dependencies);
			System.out.println("Expression   : " + inExpr);
			System.out.println("Variables    : " + variables);
			
			String moddedExpr = inExpr;
			int counter = 1;
			for(String d : dependencies)
				moddedExpr = moddedExpr.replaceAll(d, "par(" + counter++ + ")");
			
			parsedFunction = new Function(name + "(...) = " + moddedExpr);
			
			System.out.println("mXparser function expression: " + parsedFunction.getFunctionExpressionString());
			
			// String exprTest = name + "(2,2)";
			// Expression test = new Expression(exprTest, parsedFunction);
			// System.out.println("mXparser result(2,2): " + test.calculate());
			// test.setExpressionString(name + "(3,3)");
			// System.out.println("mXparser result(3,3): " + test.calculate());
			// test.
			// System.out.println(getScene());
		} else {
			// Oh no... :'(
			// (this is where we trigger the error dialog)
			System.out.println("Invalid expression");
		}
	}
}

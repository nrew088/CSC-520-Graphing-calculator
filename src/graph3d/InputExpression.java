package graph3d;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

public class InputExpression {
	// Name:         The f in f(x,y,z) = ... or the v in v = ... notations
	// Parameters:   The x,y,z in f(x,y,z) = ... notation
	// Dependencies: All parameters and any variables referenced in the RHS
	//               that are not in the parameter list
	// Variables:    All names that are currently in use
	
	public String name;
	public LinkedList<String> dependencies = new LinkedList<String>();
	public Boolean isRenderable = false;
	public RenderGrid grid = new RenderGrid(50);
	
	public TreeSet<String> parameters = new TreeSet<String>();
	
	// public static TreeMap<String,InputField> variables =
		// new TreeMap<String,InputField>();
	
	public static final LinkedList<String> specialVars =
		new LinkedList<String>(Arrays.asList("x", "y", "z"));
	
	// 1: Evaluate all InputExpressions referenced in the dependencies
	// 2: Substitute them by name and figure out the rest of step 2
	public double evaluate(Function f, TreeMap<String,Double> known) {
		Double knownVal = known.get(name);
		// If the current InputExpression's "known" value is null, i.e.
		// undefined, set its "known" value as NaN so that the key-value pair
		// is present in the map.
		if(knownVal == null)
			known.put(name, Double.NaN);
		else
		// If the current InputExpression's "known" value is NaN, then a prior
		// recursion is evaluating it and we have a cyclic reference. Throw a
		// CyclicDefinitionException (once it's implemented).
		if(knownVal == Double.NaN)
			throw new RuntimeException("Cyclic reference detected");
		
		for(String d : dependencies) {
			Double depVal = known.get(d);
			if(depVal == null) {
				depVal = InputField.definitions.get(d).expression.evaluate(f, known);
				known.put(d, depVal);
			}
		}
		
		// Now that all dependencies are "known", calculate name's value and
		// return it
		double myVal;
		
		LinkedList<String> knownVals = new LinkedList<>();
		for(String d : dependencies)
			knownVals.add("" + known.get(d));
		
		// Move update into InputField?
		InputField.definitions.get(name).update();
		
		Expression e = new Expression(
			name + "(" + String.join(",", knownVals) + ")", grid.func
		);
		myVal = e.calculate();
		return myVal;
	}
	
	// Update dependencies and other internal data, but don't evaluate
	public void update(String rawExpr) {}
	
	
	
	// TODO: Restore this here or keep implementation in InputField?
	// public void setName(String newName) {
	// 	// Add to exposed variables list if new name is neither null nor empty
	// 	if(newName != null) {
	// 		if(newName.length() > 0) {
	// 			System.out.println("Adding " + newName);
	// 			// InputField.definitions.put(name = newName, )
	// 			variables.put(name = newName, this);
	// 		}
	// 	}
		
	// 	// Remove from exposed variables list if new name is null
	// 	else {
	// 		System.out.println("Removing " + name);
	// 		variables.remove(name);
	// 		name = newName;
	// 	}
	// }
	
	
	
	public void updateDependencies(String newExpr) {
		dependencies.clear();
		
		for(String p : parameters)       // 1: Parameters
			dependencies.add(p);
		
		for(String v : specialVars)      // 2: Special variables
		if (newExpr.contains(v)   )
			dependencies.add(v);
		
		TreeMap<String,InputField> fields = InputField.definitions;
		for(String n : fields.keySet())  // 3: Referenced variables
		if (newExpr.contains(n) )
			dependencies.add(n);
		
		// Ignore variable names that aren't defined anywhere?
	}
}
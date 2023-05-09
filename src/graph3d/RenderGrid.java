package graph3d;

import java.util.LinkedList;
import java.util.TreeMap;

import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

import javafx.collections.ObservableFloatArray;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class RenderGrid extends MeshView {
	private final TriangleMesh mesh = new TriangleMesh();
	private int resolution;
	public Function func = new Function("f(...) = 0");
	private String[] funcArgs = new String[]{};
	
	RenderGrid(int res) {
		super();
		regenerate(res);
		setMesh(mesh);
		setMaterial(new PhongMaterial(Color.RED.deriveColor(0, 1, 1, 0.75)));
		setCullFace(CullFace.NONE);
	}
	
	public void setResolution(int newRes) {
		regenerate(newRes);
		update();
	}
	
	public void setFunction(String name, String expr, String... deps) {
		func = new Function(name, expr, deps);
		funcArgs = deps;
		update();
	}
	
	private void regenerate(int res) {
		resolution = res;
		float lt = -0.5f, rb = 0.5f, inc = 1f/resolution;
		int vCount = resolution+1;
		mesh.getTexCoords().addAll(0,0);
		
		for(float i = lt; i <= rb+0.5*inc; i+=inc)    // Iterate top to bottom
		for(float j = lt; j <= rb+0.5*inc; j+=inc) {  // Iterate left to right
			mesh.getPoints().addAll(i,0f,j);
		}
		
		for(int yn = 0; yn < resolution; yn++)    // Iterate top to bottom
		for(int xn = 0; xn < resolution; xn++) {  // Iterate left to right
			int yp = yn+1, xp = xn+1;
			mesh.getFaces().addAll(
				vCount*yn+xn,0, vCount*yp+xn,0, vCount*yn+xp,0,
				vCount*yp+xp,0, vCount*yn+xp,0, vCount*yp+xn,0
			);
		}
	}
	
	// private double evaluate(TreeMap<String,Double> known) {
	// 	System.out.println("Evaluating " + func.getFunctionName());
		
	// 	Boolean
	// 		isDefined = InputExpression.variables.keySet().contains(exprName),
	// 		isDifferent = exprName != func.getFunctionName();
		
	// 	if(isDefined && isDifferent)
	// 		return InputExpression.variables.get(exprName).evaluate(known);
		
	// 	return 0;
		
	// 	return 0;
	// }
	
	private void update() {
		ObservableFloatArray coords = mesh.getPoints();
		String fName = func.getFunctionName();
		Expression e = new Expression("", func);
		TreeMap<String,Double> knownVals = new TreeMap<>(){};
		
		// Thread workThread = new Thread();
		
		for(int i = 0; i < coords.size(); i += 3) {
			float
				x = coords.get(i+0),    // JavaFX and the graph use different
				y = coords.get(i+2);//, // coordinate systems; we can transform
				// z = coords.get(i+1); // the space by how we get array values
			
			LinkedList<String> argList = new LinkedList<>();
			for(String a : funcArgs) {
				double theValue;
				switch(a) {
					case "x": theValue = x; break;
					case "y": theValue = y; break;
					
					// For now assume z is independent
					case "z": theValue = 0f; break;
					
					default:
						InputExpression argExpr =
							InputField.definitions.get(a).expression;
						knownVals.clear();
						knownVals.put("x", (double) x);
						knownVals.put("y", (double) y);
						theValue = argExpr.evaluate(func, knownVals);
				}
				argList.add("" + theValue);
			}
			
			e.setExpressionString(
				fName + "(" + String.join(",", argList) + ")"
			);
			
			// i+1 is the index for z, and in JavaFX down is positive
			coords.set(i+1, (float)-e.calculate());
		}
	}
}
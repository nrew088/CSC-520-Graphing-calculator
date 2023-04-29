package graph3d;

import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class RenderGrid extends MeshView {
	private final TriangleMesh mesh = new TriangleMesh();
	
	RenderGrid(int resolution) {
		super();
		regenerate(resolution);
		setMesh(mesh);
		setMaterial(new PhongMaterial(Color.RED));
		setCullFace(CullFace.NONE);
	}
	
	public void setResolution(int newRes) {
		regenerate(newRes);
	}
	
	public void apply(Expression e, Function f) {
		
	}
	
	private void regenerate(int res) {
		float lt = -0.5f, rb = 0.5f, inc = 1f/res;
		int vCount = res+1;
		mesh.getTexCoords().addAll(0,0);
		
		for(float i = lt; i <= rb+0.5*inc; i+=inc)    // Iterate top to bottom
		for(float j = lt; j <= rb+0.5*inc; j+=inc) {  // Iterate left to right
			mesh.getPoints().addAll(i,0f,j);
		}
		
		for(int yn = 0; yn < res; yn++)    // Iterate top to bottom
		for(int xn = 0; xn < res; xn++) {  // Iterate left to right
			int yp = yn+1, xp = xn+1;
			mesh.getFaces().addAll(
				vCount*yn+xn,0, vCount*yp+xn,0, vCount*yn+xp,0,
				vCount*yp+xp,0, vCount*yn+xp,0, vCount*yp+xn,0
			);
		}
	}
}

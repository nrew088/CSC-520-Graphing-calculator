package Graph3D;

import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class RenderGrid extends MeshView {
	private final TriangleMesh mesh = new TriangleMesh();
	
	/* 
	 * @param size 
	 * @param c  color of want to disply
	 */
	RenderGrid(int size, int c) {
		super();
		int resolution = 10;
		regenerate(resolution, size);
		setMesh(mesh);
		if(c==1) {setMaterial(new PhongMaterial(Color.rgb(120, 120, 120, 0.5)));} //設定透明度
		else if (c==2) {setMaterial(new PhongMaterial(Color.LIGHTGRAY));} //設定透明度
		else {setMaterial(new PhongMaterial(Color.RED));}

		setCullFace(CullFace.NONE);
	}
	
	public void setResolution(int newRes, int newSize) {
		regenerate(newRes, newSize);
	}
	
	public void apply(Expression e, Function f) {
		
	}
	
	private void regenerate(int res, int size) {
		float lt = -0.5f, rb = 0.5f, inc = 1f/res;
		int vCount = res+1;
		mesh.getTexCoords().addAll(0,0);
		
		for(float i = lt; i <= rb+0.5*inc; i+=inc)    // Iterate top to bottom
		for(float j = lt; j <= rb+0.5*inc; j+=inc) {  // Iterate left to right
			mesh.getPoints().addAll(size*i,0f,size*j);
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

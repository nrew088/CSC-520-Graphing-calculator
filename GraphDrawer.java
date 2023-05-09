package Graph3D;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class GraphDrawer{
	
public Group drawGraph(String functionString){
    	Group group = new Group();
        // create material out of the noise image
        PhongMaterial material = new PhongMaterial(); 
        material.setDiffuseColor(Color.BLUE);          // dark blue edge
        int size = 400;
        // create pyramid with diffuse map
        float h = -100;                    // Height
        float s = -200;                    // Side

        TriangleMesh pyramidMesh = new TriangleMesh();

        pyramidMesh.getTexCoords().addAll(1,1,1,0,0,1,0,0);

        pyramidMesh.getPoints().addAll(
                0,    0,    0,            // Point 0 - Top
                0,    h,    -s/2,         // Point 1 - Front
                -s/2, h,    0,            // Point 2 - Left
                s/2,  h,    0,            // Point 3 - Back
                0,    h,    s/2           // Point 4 - Right
        );
        pyramidMesh.getFaces().addAll(
        	      0,0,  2,0,  1,0,          // Front left face
        	      0,0,  1,0,  3,0,          // Front right face
        	      0,0,  3,0,  4,0,          // Back right face
        	      0,0,  4,0,  2,0,          // Back left face
        	      4,0,  1,0,  2,0,          // Bottom rear face
        	      4,0,  3,0,  1,0           // Bottom front face
        	    ); 
//      pyramidMesh.getPoints().addAll(
//	    0,    s,    0,         // Point 0 - bottom
//	    0,    h,    -s/2,      // Point 1 - Front
//	    -s/2, h,    0,         // Point 2 - Left
//	    s/2,  h,    0,         // Point 3 - Back
//	    0,    h,    s/2        // Point 4 - Right
//	);
//pyramidMesh.getFaces().addAll(
//	  1,0,  2,0,  0,0,  // Front left face (flipped)
//	  3,0,  1,0,  0,0,  // Front right face (flipped)
//	  4,0,  3,0,  0,0,  // Back right face (flipped)
//	  4,0,  0,0,  2,0,  // Back left face (flipped)
//	  4,0,  1,0,  2,0,  // Bottom rear face
//	  4,0,  3,0,  1,0   // Bottom front face
//);

        	    MeshView pyramid = new MeshView(pyramidMesh);
        	    pyramid.setDrawMode(DrawMode.FILL);
        	    pyramid.setTranslateY(-0.1*size);

        	    // apply material
        	    pyramid.setMaterial(material);

        	    group.getChildren().add(pyramid);
        	    return group;
    }
}
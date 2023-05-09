package Graph3D;


import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class  xyzCoord {
	
	public static Group getAxes(double scale){
    	Cylinder axisX = new Cylinder(3, 800);
    	axisX.getTransforms().addAll(new Rotate(90, Rotate.Z_AXIS), new Translate(0, 0, 0));
    	axisX.setMaterial(new PhongMaterial(Color.RED));

    	Cylinder axisY = new Cylinder(3, 800);
    	axisY.getTransforms().addAll(new Rotate(-90, Rotate.X_AXIS), new Translate(0, 0, 0));
    	axisY.setMaterial(new PhongMaterial(Color.GREEN));

    	Cylinder axisZ = new Cylinder(3, 600);
    	axisZ.getTransforms().addAll(new Translate(0, 0, 0));
    	axisZ.setMaterial(new PhongMaterial(Color.BLUE));

        Group group = new Group(axisX, axisY, axisZ);
//        group.getTransforms().add(new Scale(scale, scale, scale));
        return group;
	}
	
	
}
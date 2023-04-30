package Graph3D;


import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import Graph3D.Axis;

/**
 * Create axis walls
 * @param size
 * @return
 */
public class Cube extends Group {
	
   public static Group createCube(int size) {
        Group cube = new Group();

        // size of the cube
//        Color color = Color.LIGHTGRAY;
        Color color = Color.rgb(255, 255, 255, 0.2); //設定透明度
        List<Axis> cubeFaces = new ArrayList<>();
        Axis r;

        // back face
        r = new Axis(size);
        r.setFill(color.deriveColor(0.0, 1.0, (1 - 0.5 * 1), 1.0));
        r.setTranslateX(-0.5 * size);
        r.setTranslateY(-0.5 * size);
        r.setTranslateZ(0.5 * size);

        cubeFaces.add(r);

        // bottom face
        r = new Axis(size);
        r.setFill(color.deriveColor(0.0, 1.0, (1 - 0.4 * 1), 1.0));
        r.setTranslateX(-0.5 * size);
        r.setTranslateY(0);
        r.setRotationAxis(Rotate.X_AXIS);
        r.setRotate(90);

        cubeFaces.add(r);

        // right face
        r = new Axis(size);
        r.setFill(color.deriveColor(0.0, 1.0, (1 - 0.3 * 1), 1.0));
        r.setTranslateX(-1 * size);
        r.setTranslateY(-0.5 * size);
        r.setRotationAxis(Rotate.Y_AXIS);
        r.setRotate(90);

        // left face
        r = new Axis(size);
        r.setFill(color.deriveColor(0.0, 1.0, (1 - 0.2 * 1), 1.0));
        r.setTranslateX(0);
        r.setTranslateY(-0.5 * size);
        r.setRotationAxis(Rotate.Y_AXIS);
        r.setRotate(90);

        cubeFaces.add(r);
        
        // top face
        r = new Axis(size);
        r.setFill(color.deriveColor(0.0, 1.0, (1 - 0.1 * 1), 1.0));
        r.setTranslateX(-0.5 * size);
        r.setTranslateY(-1 * size);
        r.setRotationAxis(Rotate.X_AXIS);
        r.setRotate(90);


        // front face
        r = new Axis(size);
        r.setFill(color.deriveColor(0.0, 1.0, (1 - 0.1 * 1), 1.0));
        r.setTranslateX(-0.5 * size);
        r.setTranslateY(-0.5 * size);
        r.setTranslateZ(-0.5 * size);
        
        cube.getChildren().addAll(cubeFaces);

        return cube;
    }
}
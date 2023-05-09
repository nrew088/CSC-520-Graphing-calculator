package Graph3D;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.layout.StackPane;



public class ViewPort extends StackPane {
    private SubScene graphScene;
    private final Group graphGroup;
	private double mousePosX, mousePosY;
	private double mouseOldX, mouseOldY;
    private final Rotate rotateX = new Rotate(20, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(-45, Rotate.Y_AXIS);
    private int size = 400;

    public ViewPort(Group graphGroup) {
    	this.graphGroup = graphGroup;
    
        graphGroup.getChildren().add(xyzCoord.getAxes(0.5));
        graphGroup.getChildren().add(new RenderGrid(size*2, 1));
        System.out.print("testing");
        graphGroup.getTransforms().addAll(rotateX, rotateY);

        graphScene = new SubScene(graphGroup, 800, 800, true, SceneAntialiasing.BALANCED);
        
        graphScene.setFill(Color.LIGHTGRAY);
        graphScene.setCamera(new PerspectiveCamera(true)); 
        graphScene.getCamera().setTranslateZ(-1500);
        graphScene.getCamera().setFarClip(8000);
        graphScene.getCamera().setNearClip(1);

     // Add the graph scene to the ViewPort's children
        getChildren().add(graphScene);
        
        setMargin(graphScene, new Insets(10)); // for stackpane
        setAlignment(graphScene, Pos.CENTER);  // for stackpane
        
        graphScene.setOnMousePressed(me -> {
        	mouseOldX = me.getSceneX();
        	mouseOldY = me.getSceneY();
        });
        
        graphScene.setOnMouseDragged(me -> {
        	mousePosX = me.getSceneX();
        	mousePosY = me.getSceneY();
        	rotateX.setAngle(rotateX.getAngle() - (mousePosY - mouseOldY));
        	rotateY.setAngle(rotateY.getAngle() + (mousePosX - mouseOldX));
        	mouseOldX = mousePosX;
        	mouseOldY = mousePosY;
        });
    }

    public void setGraphGroup(Group graphGroup) {
        graphScene.setRoot(graphGroup);
    }

    
    public void startDrag(double x, double y) {
        mouseOldX = x;
        mouseOldY = y;
    }
    
    public void drag(double x, double y) {
        rotateX.setAngle(rotateX.getAngle() - (y - mouseOldY));
        rotateY.setAngle(rotateY.getAngle() + (x - mouseOldX));
        mouseOldX = x;
        mouseOldY = y;
    }
    
}

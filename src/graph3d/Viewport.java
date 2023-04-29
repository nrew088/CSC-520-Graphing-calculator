package graph3d;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.robot.Robot;
import javafx.scene.transform.Rotate;

public class Viewport extends VBox implements Initializable {
	
	private Point2D origin, p1;
	
	double xOrigin, yOrigin, x1, y1;
	double scale;
	Robot mouseAnchor = new Robot();
	PerspectiveCamera cam = new PerspectiveCamera(true);
	
	// Rotation pivot points are the inverse of the camera's default
	// translation. Remember to update yaw and pitch when moving the camera?
	Rotate
		yaw   = new Rotate(0,0,0,2,Rotate.Y_AXIS),
		pitch = new Rotate(0,0,0,2,Rotate.X_AXIS);
	
	public Viewport() throws Exception {
		super();
		
		FXMLLoader loader = new FXMLLoader
			(getClass().getResource("/xml/Viewport.fxml"));
		
		loader.setController(this);
		loader.setRoot(this);
		loader.load();
		
		cam.setTranslateZ(-2);
		cam.getTransforms().addAll(yaw, pitch);
		
		Group root3D = new Group();
		SubScene subScene = new SubScene
			(root3D, 0, 0, true, SceneAntialiasing.BALANCED);
		subScene.setCamera(cam);
		getChildren().add(subScene);
		
		setOnMousePressed(this::beginRotation);
		setOnMouseDragged(this::rotateCamera);
		setOnMouseReleased(this::endRotation);
		
		// 3D OBJECTS
		root3D.getChildren().add(new RenderGrid(20));
		
		subScene.widthProperty ().bind(widthProperty ());
		subScene.heightProperty().bind(heightProperty());
		subScene.setManaged(false);
	}
	
	public void initialize(URL u, ResourceBundle rb) {
		// Initialize stuff...?
	}
	
	private void beginRotation(MouseEvent event) {
		Bounds vpBounds = localToScreen(getBoundsInParent());
		origin = new Point2D(event.getScreenX(), event.getScreenY());
		
		// Sometimes the cursor flickers because Robot.mouseMove() will happen
		// before setCursor(). Is it because JavaFX defaults to 60Hz and my
		// monitor is 144Hz? Synchronizing the two methods didn't make any
		// difference.
		setCursor(Cursor.NONE);
		mouseAnchor.mouseMove(p1 = new Point2D(
			vpBounds.getMinX() + 0.5*vpBounds.getWidth (),
			vpBounds.getMinY() + 0.5*vpBounds.getHeight()
		));
	}
	
	private void rotateCamera(MouseEvent event) {
		Point2D dp = new Point2D
			(event.getScreenX(), event.getScreenY()).subtract(p1);
		yaw  .setAngle(yaw  .getAngle()+0.3*dp.getX());
		pitch.setAngle(pitch.getAngle()-0.3*dp.getY());
		mouseAnchor.mouseMove(p1);
	}
	
	private void endRotation(MouseEvent event) {
		mouseAnchor.mouseMove(origin);
		setCursor(null);
	}
}

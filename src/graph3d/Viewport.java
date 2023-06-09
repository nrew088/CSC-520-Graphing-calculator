package graph3d;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Viewport extends VBox implements Initializable {
	
	private Point2D origin, p1;
	
	// private double scale; // TODO
	private Robot mouseAnchor = new Robot();
	private PerspectiveCamera cam = new PerspectiveCamera(true);
	private Group root3D = new Group();
	
	// Rotation pivot points are the inverse of the camera's default
	// translation. Remember to update yaw and pitch when moving the camera?
	private Rotate
		yaw   = new Rotate(0,0,0,2.125,Rotate.Y_AXIS),
		pitch = new Rotate(0,0,0,2.125,Rotate.X_AXIS);
	
	public Viewport() throws Exception {
		super();
		
		FXMLLoader loader = new FXMLLoader
			(getClass().getResource("/xml/Viewport.fxml"));
		
		loader.setController(this);
		loader.setRoot(this);
		loader.load();
		
		SubScene subScene = new SubScene
			(root3D, 0, 0, true, SceneAntialiasing.BALANCED);
		subScene.setCamera(cam);
		getChildren().addAll(subScene);
		
		setOnMousePressed(this::beginRotation);
		setOnMouseDragged(this::rotateCamera);
		setOnMouseReleased(this::endRotation);
		
		subScene.widthProperty ().bind(widthProperty ());
		subScene.heightProperty().bind(heightProperty());
		subScene.setManaged(false);
		
		cam.setTranslateZ(-2.125);
		cam.getTransforms().addAll(yaw, pitch);
	}
	
	public void initialize(URL u, ResourceBundle rb) {
		root3D.getChildren().add(getAxes(1.0));
		// Initialize stuff...?
	}
	
	public Group getAxes(double scale){
		Cylinder axisX = new Cylinder(0.005, 1);
		axisX.getTransforms().addAll(new Rotate(90, Rotate.Z_AXIS));
		axisX.setMaterial(new PhongMaterial(Color.RED));
		
		Cylinder axisY = new Cylinder(0.005, 1);
		axisY.getTransforms().addAll(new Rotate(-90, Rotate.X_AXIS));
		axisY.setMaterial(new PhongMaterial(Color.GREEN));
		
		Cylinder axisZ = new Cylinder(0.005, 1);
		axisZ.getTransforms().addAll(new Translate(0, 0, 0));
		axisZ.setMaterial(new PhongMaterial(Color.BLUE));
		
		Group group = new Group(axisX, axisY, axisZ);
		
		return group;
	}
	
	private void beginRotation(MouseEvent event) {
		Bounds vpBounds = localToScreen(getBoundsInLocal());
		origin = new Point2D(event.getScreenX(), event.getScreenY());
		
		// Sometimes the cursor flickers because Robot.mouseMove() will happen
		// before setCursor(). Is it because JavaFX defaults to 60Hz and my
		// monitor is 144Hz? Synchronizing the two methods didn't make any
		// difference.
		setCursor(Cursor.NONE);
		
		// Need to take floor of width and height because for odd-numbered
		// sizes x and y are non-integers.
		p1 = new Point2D(
			vpBounds.getMinX() + Math.floor(0.5*vpBounds.getWidth ()),
			vpBounds.getMinY() + Math.floor(0.5*vpBounds.getHeight())
		);
		mouseAnchor.mouseMove(p1);
	}
	
	private void rotateCamera(MouseEvent event) {
		Point2D dp = new Point2D
			(event.getScreenX(), event.getScreenY()).subtract(p1);
		
		double dTheta = 0.3*dp.getX(), dPhi = -0.3*dp.getY();
		
		if(dTheta != 0.0)
			yaw.setAngle(yaw.getAngle()+dTheta);
		
		if(pitch.getAngle() <  90.0 && dPhi > 0.0)
			pitch.setAngle(Math.min(pitch.getAngle()+dPhi,  90.0));
		else
		if(pitch.getAngle() > -90.0 && dPhi < 0.0)
			pitch.setAngle(Math.max(pitch.getAngle()+dPhi, -90.0));
		
		mouseAnchor.mouseMove(p1);
	}
	
	private void endRotation(MouseEvent event) {
		mouseAnchor.mouseMove(origin);
		setCursor(null);
	}
	
	public void render() {
		VBox inputContainer = (VBox) getScene().lookup("#inputContainer");
		for(Node n : inputContainer.getChildren()) {
			InputField field = (InputField) n;
			
			RenderGrid grid = field.expression.grid;
			Boolean
				isRenderable = field.expression.isRenderable,
				isInScene = root3D.getChildren().contains(grid);
			
			
			if( isRenderable && !isInScene)
				root3D.getChildren().add(grid);
			else
			if(!isRenderable &&  isInScene)
				root3D.getChildren().remove(grid);
			// else if(not renderable and not in scene) : do nothing
			// else if( is renderable and  is in scene) : do nothing
		}
	}
}

package graph3d;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

// import lib.out.MEPclasses.com.expression.parser.Parser;
import org.mariuszgromada.math.mxparser.Function;
import org.mariuszgromada.math.mxparser.License;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.mXparser;


public class Graph3D extends Application implements Initializable {
	
	private Scene scene;
	
	@FXML private SplitPane mainPane;
	@FXML private SplitPane.Divider mainDivider;
	@FXML private VBox inputContainer;
	@FXML private Viewport graphViewport;
	
	public void start(Stage primaryStage) throws Exception {
		// Non-Commercial Use Confirmation for mXparser
		License.iConfirmNonCommercialUse("Peppino Spaghetti");
		
		// Scene setup
		BorderPane root = FXMLLoader.load(getClass().getResource("/xml/Graph3D.fxml"));
		scene = new Scene(root, 1280, 720);
		
		// Event handlers
		scene.setOnKeyPressed(event -> {
			if(event.getCode() == KeyCode.ESCAPE)
				System.exit(0);
		});
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Graph3D");
		primaryStage.show();
	}
	
	public void initialize(URL a, ResourceBundle b) {
		SplitPane.setResizableWithParent(inputContainer, false);
		mainDivider = mainPane.getDividers().get(0);
		InputField.fields = inputContainer.getChildren().iterator();
	}
}

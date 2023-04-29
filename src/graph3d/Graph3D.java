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
		Parent root = FXMLLoader.load(getClass().getResource("/xml/Graph3D.fxml"));
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
		
		/*\
		*** User enters this string: f(x,y) = log(x) + 2^y
		*** - Get parameter indices of x and y inside f()
		*** 
		*** In mXparser:
		*** - Create a new (variadic) Function. Pass the constructor a String
		***   of "f(...)" + [input RHS]. Replace all instances of "x", "y",
		***   and "z" with "par([index])" in [input RHS].
		*** - Run through the render grid and create a new Expression at each
		***   point, supplying "f(u,v)" and the Function as arguments. 
		\*/
		
		// Function f = new Function("f1(...) = log10(par(1)) + 2^par(2)");
		
		// Expression es[][] = new Expression[5][5];
		
		// for(int i = 0; i < 5; i++)
		// 	for(int j = 0; j < 5; j++) {
		// 		es[i][j] = new Expression("f1("+i+","+j+")", f);
		// 		double result = es[i][j].calculate();
		// 		System.out.println(result);
		// 	}
	}
}

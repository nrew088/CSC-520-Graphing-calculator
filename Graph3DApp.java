package Graph3D;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;
import javafx.scene.paint.Color;


public class Graph3DApp extends Application {
	
    public static void main(String[] args) {
    	launch(args);
    }
	
    @Override
    public void start(Stage primaryStage) {
    	
    	BorderPane root = new BorderPane();
    	// Create a MenuToolbar and add it to the top of the BorderPane
    	MenuToolBar menuToolbar = new MenuToolBar();
        root.setTop(menuToolbar);
        
        
        Group graphGroup = new Group();
        ViewPort viewPort = new ViewPort(graphGroup);
        viewPort.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        viewPort.setGraphGroup(graphGroup);
        root.setRight(viewPort);
       
        InputField inputField = new InputField(graphGroup);
        inputField.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        
        inputField.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        root.setLeft(inputField);
               
        // Add a listener to the loggedIn property of the MenuToolBar, to close the application when it's false
        menuToolbar.loggedInProperty().addListener((obs, wasLoggedIn, isNowLoggedIn) -> {
            if (isNowLoggedIn) {
                inputField.setVisible(true);
                viewPort.setVisible(true);
            } else {
                inputField.setVisible(false);
                viewPort.setVisible(false);
            }
        });
        
        //Hide the inputField and viewPort initially
        inputField.setVisible(false);
        viewPort.setVisible(false);
        
        Scene scene = new Scene(root, 1200, 800, true, SceneAntialiasing.BALANCED);
        primaryStage.setScene(scene);
        primaryStage.setTitle("3D Grapher");
        primaryStage.show(); 
        
    }    
}
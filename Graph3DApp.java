package Graph3D;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

// import for graph
import javafx.scene.paint.Color;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.scene.layout.StackPane;



import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import Graph3D.RenderGrid;
//import Graph3D.Cube;
import Graph3D.xyzCoord;

public class Graph3DApp extends Application {
	
    public static void main(String[] args) {
    	launch(args);
    }
    /***** begin of the my main coding *****/    
    private BorderPane root = new BorderPane();
    private SubScene graphScene;
    
    // variables for mouse interaction
	private double mousePosX, mousePosY;
	private double mouseOldX, mouseOldY;
	private final Rotate rotateX = new Rotate(20, Rotate.X_AXIS);
	private final Rotate rotateY = new Rotate(-45, Rotate.Y_AXIS);
	
    // size of graph
	int size = 400;
	
    @Override
    public void start(Stage primaryStage) {
        
        // Create a ToolBar and add buttons
        MenuButton menuButton = new MenuButton("Menu");
        MenuItem importMenuItem = new MenuItem("Import File");
        MenuItem saveMenuItem = new MenuItem("Save File");
        MenuItem loginMenuItem = new MenuItem("Login/Logout");
        menuButton.getItems().addAll(importMenuItem, saveMenuItem, loginMenuItem);


        // Add the ToolBar to the top of the BorderPane
        ToolBar toolBar = new ToolBar(menuButton);
        root.setTop(toolBar);

        StackPane graphPane = new StackPane();
        
        // create axis walls
//        Group graphGroup = Cube.createCube(size);
        Group graphGroup = new Group();
        
        // Initialize graph pane
        graphGroup.getChildren().add(xyzCoord.getAxes(0.5));
        graphGroup.getChildren().add(new RenderGrid(size*2, 1));
        
        // initial rotation
        graphGroup.getTransforms().addAll(rotateX, rotateY);
        
        graphScene = new SubScene(graphGroup, 1000, 720, true, SceneAntialiasing.BALANCED);
        
        //graphScene bind with boarderpane
        graphScene.widthProperty().bind(root.widthProperty().multiply(0.6));
        graphScene.heightProperty().bind(root.heightProperty().multiply(0.9));

        // Create a PerspectiveCamera object
        graphScene.setFill(Color.LIGHTGRAY);
        graphScene.setCamera(new PerspectiveCamera(true)); 
        graphScene.getCamera().setTranslateZ(-1500);
        graphScene.getCamera().setFarClip(8000);
        graphScene.getCamera().setNearClip(1);


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
        
//        makeZoomable(graphPane);   
        root.setRight(graphScene);  
        
        // Left panel for user input function
        VBox inputBox = new VBox(10);
        inputBox.setAlignment(Pos.TOP_LEFT);
        inputBox.setPadding(new Insets(20));
        Label label = new Label("z = 0");
        TextField inputField = new TextField();
        inputField.setPrefWidth(300);
        inputField.setPromptText("z = 0");
        
        inputField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    // Perform action when enter key is pressed
                	graphGroup.getChildren().add(drawGraph(""));
                }
            }
        });
        
        Button drawButton = new Button("Clean Graph");            
        drawButton.setOnAction(e -> {
        	graphGroup.getChildren().clear();
            graphGroup.getChildren().add(xyzCoord.getAxes(0.5));
            graphGroup.getChildren().add(new RenderGrid(size*2, 1));        	
        });

        Label label2 = new Label("Enter a function in terms of x, y:");
        TextField inputField2 = new TextField();
        inputField2.setPrefWidth(300);
        inputField2.setPromptText("");

        Button drawButton2 = new Button("Draw Graph");
        drawButton2.setOnAction(e -> {
        	Group drawGroup2 = drawGraph("");
        });
        
        inputBox.getChildren().addAll(label, inputField, drawButton, label2, inputField2, drawButton2);
        root.setLeft(inputBox);

        // Set constraints for top node
        BorderPane.setMargin(toolBar, new Insets(10));
        BorderPane.setAlignment(toolBar, Pos.CENTER);

        // Set constraints for left node
        BorderPane.setMargin(inputBox, new Insets(10));
        BorderPane.setAlignment(inputBox, Pos.TOP_LEFT);

        // Set constraints for center node
        BorderPane.setMargin(graphScene, new Insets(10));

        // Set constraints for right node
        BorderPane.setMargin(graphScene, new Insets(10));
        BorderPane.setAlignment(graphScene, Pos.CENTER);
      
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setTitle("3D Graphing Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();      

    }    

	/**
     * new PhongMaterial() : 設定Phong Material
     * setSpecularColor(): 設定反射光的顏色
     * setDiffuseColor(): 設定漫射光的顏色
     * pyramid.setMaterial(material): 設定物體表面的材質
     **/   
    private Group drawGraph(String functionString) {
    	Group group = new Group();
        // create material out of the noise image
        PhongMaterial material = new PhongMaterial(); 
        material.setDiffuseColor(Color.BLUE);          // dark blue edge
        
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
package Graph3D;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableFloatArray;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Point3D;
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
import javafx.scene.transform.Translate;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import java.util.Random;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Box;

//the third try add 
import javafx.scene.shape.CullFace;
import javafx.scene.DepthTest;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.paint.Paint;
import javafx.scene.input.ScrollEvent;
import javafx.event.EventHandler;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;



public class Graph3DApp extends Application {
	
    public static void main(String[] args) {
    	launch(args);
    }
    /***** begin of the my main coding *****/    
    private Stage primaryStage;
    private BorderPane root = new BorderPane();
    private SubScene graphScene;
    private String functionString = "z = sin(sqrt(x^2 + y^2))/sqrt(x^2 + y^2)";
//    private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
//    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
    private final Translate translate = new Translate(0, 0, -1000);
    private final javafx.scene.PerspectiveCamera camera = new javafx.scene.PerspectiveCamera();
    
	private Group graphGroup = new Group();
//	private BorderPane root = new BorderPane();
//	private SubScene graphScene;
	// declare functionString as an instance variable
//	private String functionString;  
//	private PerspectiveCamera camera = new PerspectiveCamera(false);
	
    @Override
    public void start(Stage primaryStage) {
    	// Initialize the BorderPane object
        this.primaryStage = primaryStage;
        
        // Create a ToolBar and add buttons
        MenuButton menuButton = new MenuButton("Menu");
        MenuItem importMenuItem = new MenuItem("Import File");
        MenuItem saveMenuItem = new MenuItem("Save File");
        MenuItem loginMenuItem = new MenuItem("Login/Logout");
        menuButton.getItems().addAll(importMenuItem, saveMenuItem, loginMenuItem);


        // Add the ToolBar to the top of the BorderPane
        ToolBar toolBar = new ToolBar(menuButton);
        root.setTop(toolBar);

        // Create the 3D graph panel on the right side
        Group graphGroup = new Group();
        graphScene = new SubScene(graphGroup, 800, 800, true, SceneAntialiasing.BALANCED);
        //graphScene bind with boarderpane
        graphScene.widthProperty().bind(root.widthProperty().multiply(0.6));
        graphScene.heightProperty().bind(root.heightProperty().multiply(0.9));

        // Create a PerspectiveCamera object
        graphScene.setFill(Color.LIGHTGRAY);
        camera.setTranslateZ(-50);
        camera.setFieldOfView(60);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        graphScene.setCamera(camera);
        camera.getTransforms().addAll(new Rotate(30, Rotate.Y_AXIS), new Rotate(-20, Rotate.X_AXIS), new Translate(0, 0, 0));
        
        // Initialize the "root" variable before setting the right side of the BorderPane
        root.setRight(graphScene);      
        
        // Left panel for user input function
        VBox inputBox = new VBox(10);
        inputBox.setAlignment(Pos.TOP_LEFT);
        inputBox.setPadding(new Insets(20));
        Label label = new Label("Enter a function in terms of x, y:");
        TextField inputField = new TextField();
        inputField.setPrefWidth(300);
        inputField.setPromptText("x^2 + y^2 +z^2 = 1");

        Button drawButton = new Button("Draw Graph");
        drawButton.setOnAction(e -> {
        	drawGraph("x*x + y*y + z*z", graphGroup);
        });

        Label label2 = new Label("Enter a function in terms of x, y:");
        TextField inputField2 = new TextField();
        inputField2.setPrefWidth(300);
        inputField2.setPromptText("x^2 + y^2 +z^2 = 1");

        Button drawButton2 = new Button("Draw Graph");
        drawButton2.setOnAction(e -> {
        	drawGraph("x*x + y*y + z*z", graphGroup);
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

        Scene scene = new Scene(root, 1000, 1000);
        primaryStage.setTitle("3D Graphing Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();      

    }
    
    // display sphere
    private void drawGraph(String functionString, Group graphGroup) {
//    	Group graphGroup = (Group) graphScene.getRoot();
    	
    	// Clear the graphGroup of any existing nodes
        graphGroup.getChildren().clear();
        
        // Create a sphere and add it to the center of the graph panel
        Sphere sphere = new Sphere(200);
        sphere.setTranslateX(graphScene.getWidth() / 2);
        sphere.setTranslateY(graphScene.getHeight() / 2);
        sphere.setTranslateZ(0);
        graphGroup.getChildren().add(sphere);
        
        // Add the graphScene to the BorderPane
        root.setRight(graphScene);
        
//         Rest of the code to draw the graph goes here...
    }
/***** end of the my main coding *****/


    /**
     * new PhongMaterial() : 設定Phong Material
     * setSpecularColor(): 設定反射光的顏色
     * setDiffuseColor(): 設定漫射光的顏色
     * pyramid.setMaterial(material): 設定物體表面的材質
     **/   
//    private Group drawGraph() {
//    	Group group = new Group();
//        // create material out of the noise image
//        PhongMaterial material = new PhongMaterial(); 
//        material.setSpecularColor(Color.BLACK);
//        material.setDiffuseColor(Color.rgb(178, 190, 255, 0.3)); 
//        // The alpha channel controls the transparency of a color, where 0 means fully transparent and 1 means fully opaque
////        material.setSpecularColor(Color.TRANSPARENT); // Set the specular color to transparent to disable the lighting effect
//        material.setSpecularColor(Color.DARKBLUE);           // dark blue edge
//        
//        // create pyramid with diffuse map
//        float h = 300;                    // Height
//        float s = 300;                    // Side
//
//        TriangleMesh pyramidMesh = new TriangleMesh();
//
//        pyramidMesh.getTexCoords().addAll(1,1,1,0,0,1,0,0);
//
//        pyramidMesh.getPoints().addAll(
//                0,    0,    0,            // Point 0 - Top
//                0,    h,    -s/2,         // Point 1 - Front
//                -s/2, h,    0,            // Point 2 - Left
//                s/2,  h,    0,            // Point 3 - Back
//                0,    h,    s/2           // Point 4 - Right
//        );
//        pyramidMesh.getFaces().addAll(
//        	      0,0,  2,0,  1,0,          // Front left face
//        	      0,0,  1,0,  3,0,          // Front right face
//        	      0,0,  3,0,  4,0,          // Back right face
//        	      0,0,  4,0,  2,0,          // Back left face
//        	      4,0,  1,0,  2,0,          // Bottom rear face
//        	      4,0,  3,0,  1,0           // Bottom front face
//        	    ); 
//
//        	    MeshView pyramid = new MeshView(pyramidMesh);
//        	    pyramid.setDrawMode(DrawMode.FILL);
//        	    pyramid.setTranslateY(-250);
//
//        	    // apply material
//        	    pyramid.setMaterial(material);
//
//        	    group.getChildren().add(pyramid);
//        	    return group;
//    }
/***** end of the my second try it show pyramid and successfully *****/
    
    
/** this is thrid try it will show the xy axis  **/    
    // size of graph
    int size = 400;

    // variables for mouse interaction
//    private double mousePosX, mousePosY;
//    private double mouseOldX, mouseOldY;
//    private final Rotate rotateX = new Rotate(20, Rotate.X_AXIS);
//    private final Rotate rotateY = new Rotate(-45, Rotate.Y_AXIS);
//
//    @Override
//    public void start(Stage primaryStage) {
//
//        // create axis walls
//        Group cube = createCube(size);
//
//        // initial cube rotation
//        cube.getTransforms().addAll(rotateX, rotateY);
//
//        // add objects to scene
//        StackPane root = new StackPane();
//        root.getChildren().add(cube);


//        
///***     這是我嘗試在 cube 加東西    大成功 *****/
//      float h = size/2;                    // Height
//      float s = size;                    // Side

//      TriangleMesh pyramidMesh = new TriangleMesh();

//      pyramidMesh.getTexCoords().addAll(1,1,1,0,0,1,0,0);
//
//      pyramidMesh.getPoints().addAll(
//      0,    0,    0,            // Point 0 - Top
//      0,    h,    -s/2,         // Point 1 - Front
//      -s/2, h,    0,            // Point 2 - Left
//      s/2,  h,    0,            // Point 3 - Back
//      0,    h,    s/2           // Point 4 - Right
//);
////The vertices should be defined in a counterclockwise order when viewed from the outside of the shape
//pyramidMesh.getFaces().addAll(
//	      0,0,  2,0,  1,0,          // Front left face
//	      0,0,  1,0,  3,0,          // Front right face
//	      0,0,  3,0,  4,0,          // Back right face
//	      0,0,  4,0,  2,0,          // Back left face
//	      4,0,  1,0,  2,0,          // Bottom rear face
//	      4,0,  3,0,  1,0           // Bottom front face
//	    ); 
//      
//      pyramidMesh.getPoints().addAll(
//    		    0,    s,    0,         // Point 0 - bottom
//    		    0,    h,    -s/2,      // Point 1 - Front
//    		    -s/2, h,    0,         // Point 2 - Left
//    		    s/2,  h,    0,         // Point 3 - Back
//    		    0,    h,    s/2        // Point 4 - Right
//    		);
//      pyramidMesh.getFaces().addAll(
//    		  1,0,  2,0,  0,0,  // Front left face (flipped)
//    		  3,0,  1,0,  0,0,  // Front right face (flipped)
//    		  4,0,  3,0,  0,0,  // Back right face (flipped)
//    		  4,0,  0,0,  2,0,  // Back left face (flipped)
//    		  4,0,  1,0,  2,0,  // Bottom rear face
//    		  4,0,  3,0,  1,0   // Bottom front face
//    	);

//PhongMaterial material = new PhongMaterial();
//material.setSpecularColor(Color.WHITE);
//
//MeshView pyramidView = new MeshView(pyramidMesh);
//pyramidView.setDrawMode(DrawMode.FILL);
//pyramidView.setTranslateY(-0.1*size)); // 這是調整Y軸，不設定就是躺平
// apply material
//pyramid.setMaterial(material);
//cube.getChildren().addAll(pyramidView);

/****    這是我嘗試在 cube 加東西      *****/

       
        
//        // scene
//        Scene scene = new Scene(root, 1600, 900, true, SceneAntialiasing.BALANCED);
//        scene.setCamera(new PerspectiveCamera());
//
//        scene.setOnMousePressed(me -> {
//            mouseOldX = me.getSceneX();
//            mouseOldY = me.getSceneY();
//        });
//        scene.setOnMouseDragged(me -> {
//            mousePosX = me.getSceneX();
//            mousePosY = me.getSceneY();
//            rotateX.setAngle(rotateX.getAngle() - (mousePosY - mouseOldY));
//            rotateY.setAngle(rotateY.getAngle() + (mousePosX - mouseOldX));
//            mouseOldX = mousePosX;
//            mouseOldY = mousePosY;
//
//        });
//
//        makeZoomable(root);
//
//        primaryStage.setResizable(false);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//
//
//    }
//
//    /**
//     * Axis wall
//     */
//    public static class Axis extends Pane {
//
//        Rectangle wall;
//        double minY;
//
//        public Axis(double size) {
//
//            // wall
//            // first the wall, then the lines => overlapping of lines over walls
//            // works
//            wall = new Rectangle(size, size);
//            getChildren().add(wall);
//
//            // grid
//            double zTranslate = 0;
//            double lineWidth = 1.0;
////            Color gridColor = Color.WHITE;
//            Color gridColor = Color.DARKCYAN;
//            for (int y = 0; y <= size; y += size / 10) {
//
//                Line line = new Line(0, 0, size, 0);
//                line.setStroke(gridColor);
//                line.setFill(gridColor);
//                line.setTranslateY(y);  // 畫上橫線
//                line.setTranslateZ(zTranslate);
//                line.setStrokeWidth(lineWidth);
//
//                getChildren().addAll(line);
//                
//                if (y < minY) {
//                    minY = y; // update minimum y-value
//                }
//
//            }
//            
//
//            for (int x = 0; x <= size; x += size / 10) {
//
//                Line line = new Line(0, 0, 0, size);
//                line.setStroke(gridColor);
//                line.setFill(gridColor);
//                line.setTranslateX(x);
//                line.setTranslateZ(zTranslate);
//                line.setStrokeWidth(lineWidth);
//
//                getChildren().addAll(line);
//
//            }
//
//        }
//
//        public void setFill(Paint paint) {
//            wall.setFill(paint);
//        }
//
//    }
//
//    public void makeZoomable(StackPane control) {
//
//        final double MAX_SCALE = 20.0;
//        final double MIN_SCALE = 0.1;
//
//        control.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
//
//            @Override
//            public void handle(ScrollEvent event) {
//
//                double delta = 1.2;
//
//                double scale = control.getScaleX();
//
//                if (event.getDeltaY() < 0) {
//                    scale /= delta;
//                } else {
//                    scale *= delta;
//                }
//
//                scale = clamp(scale, MIN_SCALE, MAX_SCALE);
//
//                control.setScaleX(scale);
//                control.setScaleY(scale);
//
//                event.consume();
//
//            }
//
//        });
//
//    }
//
//    /**
//     * Create axis walls
//     * @param size
//     * @return
//     */
//    private Group createCube(int size) {
//
//        Group cube = new Group();
//
//        // size of the cube
////        Color color = Color.DARKCYAN;
//        Color color = Color.rgb(255, 255, 255, 0.2); //設定透明度
//        List<Axis> cubeFaces = new ArrayList<>();
//        Axis r;
//
//        // back face
//        r = new Axis(size);
//        r.setFill(color.deriveColor(0.0, 1.0, (1 - 0.5 * 1), 1.0));
//        r.setTranslateX(-0.5 * size);
//        r.setTranslateY(-0.5 * size);
//        r.setTranslateZ(0.5 * size);
//
//        cubeFaces.add(r);
//
//        // bottom face
//        r = new Axis(size);
//        r.setFill(color.deriveColor(0.0, 1.0, (1 - 0.4 * 1), 1.0));
//        r.setTranslateX(-0.5 * size);
//        r.setTranslateY(0);
//        r.setRotationAxis(Rotate.X_AXIS);
//        r.setRotate(90);
//
//        cubeFaces.add(r);
//
//        // right face
//        r = new Axis(size);
//        r.setFill(color.deriveColor(0.0, 1.0, (1 - 0.3 * 1), 1.0));
//        r.setTranslateX(-1 * size);
//        r.setTranslateY(-0.5 * size);
//        r.setRotationAxis(Rotate.Y_AXIS);
//        r.setRotate(90);
//
//        // left face
//        r = new Axis(size);
//        r.setFill(color.deriveColor(0.0, 1.0, (1 - 0.2 * 1), 1.0));
//        r.setTranslateX(0);
//        r.setTranslateY(-0.5 * size);
//        r.setRotationAxis(Rotate.Y_AXIS);
//        r.setRotate(90);
//
//        cubeFaces.add(r);
//        
//        // top face
//        r = new Axis(size);
//        r.setFill(color.deriveColor(0.0, 1.0, (1 - 0.1 * 1), 1.0));
//        r.setTranslateX(-0.5 * size);
//        r.setTranslateY(-1 * size);
//        r.setRotationAxis(Rotate.X_AXIS);
//        r.setRotate(90);
//
//
//        // front face
//        r = new Axis(size);
//        r.setFill(color.deriveColor(0.0, 1.0, (1 - 0.1 * 1), 1.0));
//        r.setTranslateX(-0.5 * size);
//        r.setTranslateY(-0.5 * size);
//        r.setTranslateZ(-0.5 * size);
//
//
//        cube.getChildren().addAll(cubeFaces);
//
//        return cube;
//    }
//
  
    
    
    
}
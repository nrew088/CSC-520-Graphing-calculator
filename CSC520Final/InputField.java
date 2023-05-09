package Graph3D;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class InputField extends VBox {
	private int size = 400;
	
    public InputField(Group graphGroup) {
        setSpacing(10);
        setAlignment(Pos.TOP_LEFT);
        setPadding(new Insets(20));

        HBox inputBox1 = new HBox();
        Label label1 = new Label("EX: z = x^2 + y^2");
        TextField inputField1 = new TextField();
        inputField1.setPrefWidth(300);
        inputField1.setPromptText("");
        Button cleanButton1 = new Button("Clean");
        cleanButton1.setOnAction(e -> {
            graphGroup.getChildren().clear();
            graphGroup.getChildren().add(xyzCoord.getAxes(0.5));
            graphGroup.getChildren().add(new RenderGrid(size*2, 1));          
        });
        inputBox1.getChildren().addAll(cleanButton1, inputField1);      
        inputField1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    // Perform action when enter key is pressed
                	GraphDrawer graphDrawer = new GraphDrawer();
                    graphGroup.getChildren().add(graphDrawer.drawGraph(""));
                }
            }
        });
        
        HBox inputBox2 = new HBox();
        Label label2 = new Label("EX: z = x^2 + y^2");
        TextField inputField2 = new TextField();
        inputField2.setPrefWidth(300);
        inputField2.setPromptText("");
        Button cleanButton2 = new Button("Clean");
        cleanButton2.setOnAction(e -> {
            graphGroup.getChildren().clear();
            graphGroup.getChildren().add(xyzCoord.getAxes(0.5));
            graphGroup.getChildren().add(new RenderGrid(size*2, 1));          
        });
        inputBox2.getChildren().addAll(cleanButton2, inputField2);      
        inputField2.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    // Perform action when enter key is pressed
                	GraphDrawer graphDrawer = new GraphDrawer();
                    graphGroup.getChildren().add(graphDrawer.drawGraph(""));
                }
            }
        });
        
        getChildren().addAll(label1, inputBox1, label2, inputBox2);
    }
}

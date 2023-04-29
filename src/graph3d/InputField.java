// Custom components MUST go inside a package or else the java compiler can't
// find them. Package names correspond to project subfolders relative to the
// src directory. So far, this package doesn't need to be specified in
// launch.json's build settings.

package graph3d;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class InputField 
extends HBox implements Initializable {
	
	@FXML Button button;
	@FXML TextField textField;
	boolean released;
	
	InputExpression expression = new InputExpression();
	// RenderGrid grid;
	
	static public Iterator<Node> fields;
	
	public InputField() throws Exception {
		super();
		
		FXMLLoader loader = new FXMLLoader
			(getClass().getResource("/xml/InputField.fxml"));
		
		loader.setController(this);
		loader.setRoot(this);
		loader.load();
	}
	
	public void initialize(URL u, ResourceBundle rb) {
		HBox.setHgrow(button, Priority.NEVER);
		HBox.setHgrow(textField, Priority.ALWAYS);
		textField.setOnKeyReleased(this::onRelease);
		released = true;
	}
	
	// Event handlers referenced in FXML must be public!!!!!!!!!!!!!!!!!!!!!!!
	public void removeField(ActionEvent event) {
		System.out.println("TODO: Implement removeField");
	}
	
	public void initRender(ActionEvent event) {
		// This event will keep firing if a key is held down. How can the
		// event handler be removed and reattached in onRelease?
		if(released) {
			System.out.println(event);
			released = false;
			// TODO: Graph3D.render()
			expression.update(textField.getText());
			Viewport vp = (Viewport) getScene().lookup("#graphViewport");
		}
	}
	
	public void onRelease(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) {
			released = true;
		}
	}
}
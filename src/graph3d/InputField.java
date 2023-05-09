// Custom components MUST go inside a package or else the java compiler can't
// find them. Package names correspond to project subfolders relative to the
// src directory. So far, this package doesn't need to be specified in
// launch.json's build settings.

package graph3d;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.VBox;

public class InputField 
extends HBox implements Initializable {
	
	static TreeMap<String, InputField> definitions = new TreeMap<>();
	
	@FXML Button button;
	@FXML TextField textField;
	boolean released;
	
	InputExpression expression = new InputExpression();
	
	static public int fieldIdx = 0;
	
	// TODO: allow empty expression and fix update() code to handle it
	private final Pattern exprPat = Pattern.compile
		("(?<v>[a-zA-Z_]+)\\s*\\(?(?<p>[a-zA-Z_,]+)*\\)?\\s*=\\s*(?<e>.*)");
	
	// z(x,y) = x^2 + y^2
	
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
		textField.focusedProperty().addListener(this::onFocus);
		textField.setOnKeyPressed(this::onPress);
		textField.setOnKeyReleased(this::onRelease);
		released = true;
	}
	
	public void onFocus(ObservableValue<? extends Boolean> o, Boolean oldVal, Boolean NewVal) {
		VBox inputContainer = (VBox) getScene().lookup("#inputContainer");
		ObservableList<Node> children = inputContainer.getChildren();
		fieldIdx = children.indexOf(this);
	}
	
	public void onPress(KeyEvent event) {
		try {
			switch(event.getCode()) {
				case UP         : navigateUp()   ; break ;
				case DOWN       : navigateDown() ; break ;
				case BACK_SPACE : maybeDelete()  ; break ;
				default:;
			}
		} catch (Exception e) {}
	}
	
	private void navigateUp() {
		VBox inputContainer = (VBox) getScene().lookup("#inputContainer");
		ObservableList<Node> children = inputContainer.getChildren();
		if(fieldIdx > 0) {
			fieldIdx--;
			InputField prev = (InputField) children.get(fieldIdx);
			prev.textField.requestFocus();
		}
	}
	
	private void navigateDown() throws Exception {
		VBox inputContainer = (VBox) getScene().lookup("#inputContainer");
		ObservableList<Node> children = inputContainer.getChildren();
		fieldIdx++;
		
		if(children.size() <= fieldIdx)
			children.add(new InputField());
		
		InputField next = (InputField) inputContainer.getChildren().get(fieldIdx);
		next.textField.requestFocus();
	}
	
	private void maybeDelete() throws Exception {
		if( textField.getLength() == 0) {
			VBox inputContainer = (VBox) getScene().lookup("#inputContainer");
			ObservableList<Node> children = inputContainer.getChildren();
			
			int prevIdx = children.indexOf(this)-1;
			
			// requestFocus first to avoid an exception
			((InputField)children.get(prevIdx)).textField.requestFocus();
			children.remove(this);
		}
	}
	
	// Event handlers referenced in FXML must be public!!!!!!!!!!!!!!!!!!!!!!!
	public void removeField(ActionEvent event) {
		VBox inputContainer = (VBox) getScene().lookup("#inputContainer");
		ObservableList<Node> children = inputContainer.getChildren();
		if(children.size() > 1) {
			children.remove(this);
			fieldIdx = Math.min(children.size()-1, fieldIdx);
			((InputField)children.get(fieldIdx)).textField.requestFocus();
		}
	}
	
	public void initRender(ActionEvent event) {
		// This event will keep firing if a key is held down. How can the
		// event handler be removed and reattached in onRelease?
		if(released) {
			released = false;
			// expression.update(textField.getText());
			update();
			definitions.put(expression.name, this);
			Viewport vp = (Viewport) getScene().lookup("#graphViewport");
			vp.render();
		}
	}
	
	public void onRelease(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) {
			released = true;
		}
	}
	
	public void update() {
		String inName, inParams, inExpr, rawExpr = textField.getText();
		Matcher m = exprPat.matcher(rawExpr);
		
		if(m.matches()) {
			inName   = m.group("v");
			inParams = m.group("p");
			inExpr   = m.group("e");
		} else if(rawExpr.length() == 0) {
			inName = inParams = inExpr = "";
		} else {
			// Oh no... :'(
			// (trigger the error dialog and then refocus the TextField)
			// ErrorDialog.show("Invalid expression, please specify an independent variable");
			return;
		}
		
		setExpressionName(inName);
		
		// Some of this should go inside InputExpression, but how much?
		
		// If there are no parameters, use an empty array.
		expression.parameters = new TreeSet<String>(Arrays.asList(
			inParams == null || inParams.length() == 0
			? new String[]{}
			: inParams.split(",")
		));
		
		expression.updateDependencies(inExpr);
		
		Boolean usesAxis =
			expression.dependencies.contains("x") ||
			expression.dependencies.contains("y");
		
		expression.isRenderable =
			expression.name != null && expression.name.length() > 0 && usesAxis;
		
		if(expression.isRenderable) {
			String[] varNames = expression.dependencies.toArray(new String[]{});
			expression.grid.setFunction(expression.name, inExpr, varNames);
		}
	}
	
	public void setExpressionName(String newName) {
		// Add to exposed variables list if new name is neither null nor empty
		if(newName != null) {
			if(newName.length() > 0) {
				// System.out.println("Adding " + newName);
				expression.name = newName;
				definitions.put(newName, this);
			}
		}
		
		// Remove from exposed variables list if new name is null
		else {
			// System.out.println("Removing " + expression.name);
			definitions.remove(expression.name);
			expression.name = newName;
		}
	}
}
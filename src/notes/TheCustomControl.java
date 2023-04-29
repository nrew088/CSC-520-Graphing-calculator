package notes; // <- package declaration needs to be the first non-empty line!

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent; // Optional, see buttonAction.

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class TheCustomControl

/*\
 *  Extending from Pane or one of its subclasses is typically the easiest way
 *  to implement a custom control in FXML. It's possible to extend from any
 *  subclass of Region, but this requires extra work - for example to extend
 *  from Control entails:
 *  
 *  - Exposing a public getChildren() method so the FXMLLoader can load them
 *  - Optionally a @DefaultProperty("children") annotation to remove an extra
 *    layer of indentation in the FXML file
 *  - Creating and setting a default skin
 *  
 *  ...Which basically just reinvents Pane's wheel. Supposedly the control and
 *  skin approach makes it easier to dynamically change the appearance of
 *  TheCustomControl, but if you use FXCSS and extend from Pane you can still
 *  achieve this by simply adding or removing CSS class names from the
 *  ObservableList<String> returned by TheCustomControl.getStyleClass().
 *  Either way, whichever class you choose to extend from, don't forget to
 *  import it!
\*/
extends HBox // Direct subclass of Pane, adds some automatic alignment

// Optional but useful, see initialize() below
implements Initializable
{
	@FXML public Button theButton;       // Optional if you don't reference
	@FXML public TextField theTextField; // these subcontrols in later code
	
	public TheCustomControl() throws Exception
	{
		// Superclass constructor
		super();
		
		
		/*\
		 *  Note that there are two options for getResource()'s argument:
		 *  
		 *  - A file path relative to /src (if the first character is '/')
		 *  - A package-relative file path (otherwise)
		 *  
		 *  So in this example, the loader starts in the /src folder and
		 *  navigates to /xml, where it expects to find TheCustomControl.fxml.
		 *  If instead we specify ../xml/TheCustomControl.fxml, it starts in
		 *  /src/notes, goes up to /src, then down to /src/xml where it
		 *  expects to find TheCustomControl.fxml. As far as I can tell, all
		 *  resources must be in a subfolder of /src - trying to navigate
		 *  through /src's parent folder seems to be impossible.
		\*/
		FXMLLoader loader = new FXMLLoader
			(getClass().getResource("/xml/TheCustomControl.fxml"));
		
		
		/*\
		 *  setRoot() goes hand in hand with fx:root in TheCustomControl.fxml.
		 *  Since the "type" attribute of the fx:root element is
		 *  javafx.scene.layout.HBox, TheCustomControl must extend HBox.
		 *  
		 *  setController() allows its argument to access members of
		 *  subcontrols defined as children of fx:root, in this case theButton
		 *  and theTextField.
		\*/
		loader.setRoot(this);
		loader.setController(this);
		
		// load() can throw an exception. This constructor just passes it on.
		loader.load();
	}
	
	
	
	/*\
	 *  Required with "implements Initializable". This is called somewhere
	 *  inside of FXMLLoader.load(), and nodes defined in
	 *  TheCustomControl.fxml are guaranteed to be non-null in here. But watch
	 *  out - the method that calls load() (the constructor in this example)
	 *  may still see these variables as null even after the line where load()
	 *  is called! I have no idea why.
	\*/
	public void initialize(URL u, ResourceBundle rb) {
		theButton.setText("Don't click");
		HBox.setHgrow(theButton, Priority.NEVER);
		HBox.setHgrow(theTextField, Priority.ALWAYS);
	}
	
	
	
	/*\
	 *  Optional
	 *  
	 *  FXML event handlers expect certain method signatures. theButton's
	 *  onAction handler expects "public void functionName(ActionEvent)",
	 *  where in TheCustomControl.fxml the onAction property of theButton is
	 *  set as "#functionName". The functionName used in this example is
	 *  buttonAction.
	 *  
	 *  Mouse and keyboard events also have more direct event handlers such as
	 *  onMousePressed and onKeyPressed. Use MouseEvent, KeyEvent, and their
	 *  respective subclasses to respond to mouse and keyboard input.
	\*/
	public void buttonAction(ActionEvent event) {
		System.out.println("Hey, quit poking me!");
	}
}
package graph3d;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class Viewport extends VBox implements Initializable {
	
	public Viewport() throws Exception {
		super();
		
		FXMLLoader loader = new FXMLLoader
			(getClass().getResource("/xml/Viewport.fxml"));
		
		loader.setController(this);
		loader.setRoot(this);
		loader.load();
	}
	
	public void initialize(URL u, ResourceBundle rb) {
		// Initialize stuff
	}
}

package Graph3D;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class LoginWindow extends Stage {
	private boolean loggedIn = false;
	private TextField usernameTextField;
    private PasswordField passwordField;

    public LoginWindow() {
        setTitle("Login/Logout Window");
          
        // Create the grid pane for the login/logout window
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setAlignment(Pos.CENTER);

        // Add the username label and text field to the grid pane
        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 0);

        usernameTextField = new TextField();
        GridPane.setConstraints(usernameTextField, 1, 0);

        // Add the password label and password field to the grid pane
        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);

        passwordField = new PasswordField();
        GridPane.setConstraints(passwordField, 1, 1);

        // Add the login button to the grid pane
        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 0, 2);

        // Set a handler for the login button
        loginButton.setOnAction(e -> {
            String username = usernameTextField.getText();
            String password = passwordField.getText();
            
            // Verify login credentials
            boolean validCredentials = verifyLogin(username, password);


            if (validCredentials) {
                // Set the logged in flag to true
                loggedIn = true;

                // Close the login window
                close();
            } 
        });
        
        // Add the UI elements to the grid pane
        grid.getChildren().addAll(usernameLabel, usernameTextField, passwordLabel, passwordField, loginButton);
        Scene scene = new Scene(grid, 400, 200);
        setScene(scene);
    }

    
    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    public void showAndWait() {
        // Show the login window and wait for it to close
        super.showAndWait();
    }
    
    private boolean verifyLogin(String username, String password) {
        // Verify the login credentials
        if (username.equals("admin") && password.equals("password")) {
            return true; // Return true if the login was successful
        } else {
            return false; // Return false if the login failed
        }
    }

}

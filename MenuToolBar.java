package Graph3D;


import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;



import Graph3D.LoginWindow;


public class MenuToolBar extends ToolBar {
	private BooleanProperty loggedIn = new SimpleBooleanProperty(false);
    private MenuItem loginMenuItem;
  private MenuItem logoutMenuItem;
    
    public MenuToolBar() {
        MenuButton menuButton = new MenuButton("Menu");
        MenuItem importMenuItem = new MenuItem("Import File");
        MenuItem saveMenuItem = new MenuItem("Save File");
        loginMenuItem = new MenuItem("Login"); 
        logoutMenuItem = new MenuItem("Logout");
        MenuItem helpMenuItem = new MenuItem("Help");

        menuButton.getItems().addAll(importMenuItem, saveMenuItem, loginMenuItem, logoutMenuItem, helpMenuItem);

        getItems().add(menuButton);
        
        // Set a handler for the login menu item
        loginMenuItem.setOnAction(e -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.showAndWait();
            loggedIn.set(loginWindow.isLoggedIn());
            updateMenuItems();
        });
        
        // Set a handler for the logout menu item
        logoutMenuItem.setOnAction(e -> {
            loggedIn.set(false);
            updateMenuItems();
        });
        
        
        // Bind the loginMenuItem text to the loggedIn property
        loginMenuItem.textProperty().bind(Bindings.when(loggedIn).then("Logout").otherwise("Login"));
        logoutMenuItem.textProperty().bind(Bindings.when(loggedIn).then("Logout").otherwise("Login"));
        
        // Initially, show only the login menu item
        updateMenuItems();
        
    }
    public BooleanProperty loggedInProperty() {
        return loggedIn;
    }
    
    private void updateMenuItems() {
        if (loggedIn.get()) {
        	loginMenuItem.setVisible(false);
            logoutMenuItem.setVisible(true);
        } else {
        	loginMenuItem.setVisible(true);
            logoutMenuItem.setVisible(false);
        }
    }
    
    public boolean isLoggedIn() {
        return loggedIn.get();
    }

}

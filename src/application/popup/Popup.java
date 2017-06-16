package application.popup;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * A base class template for different kinds of pop ups
 * 
 *
 */
public abstract class Popup extends Stage {
		
	protected Scene scene;
	protected Parent parent;
	protected Object controller;
	
}

package application.popup;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;

/**
 * A popup which prompts the user to select a yes or no button, returning a boolean value
 *
 */
public class Confirmation extends Popup {

	
	public Confirmation(String msg, String stylePath) {
		createPopup(msg, stylePath);
	}

	/**
	 * 
	 * @param stylePath
	 * the path to the stylesheet of this pop-up (optional)
	 * 
	 */
	protected void createPopup(String msg, String stylePath) {
		
		this.initModality(Modality.APPLICATION_MODAL);
		this.initOwner(this);
				
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/confirmation.fxml"));
		
			this.parent = loader.load();
			this.controller = loader.getController();
			
			this.scene = new Scene(this.parent);
						
			if(new File(stylePath).exists() && !stylePath.isEmpty()) {
				scene.getStylesheets().add(getClass().getResource(stylePath).toExternalForm());
			}
			
			this.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

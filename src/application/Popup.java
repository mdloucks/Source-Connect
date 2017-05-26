package application;

import java.io.IOException;

import controllers.PopupController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Popup {
	
	public Stage popup;
	public Scene popupScene;
	private Parent popupParent;
	private PopupController controller;
	
//	@FXML
//	private Label message;
	
	
	/**
	 * Creates a pop-up with the elements specified in the
	 * given FXML file.
	 * 
	 * @param fxmlLink
	 * path to the FXML file for this pop-up
	 * 
	 * @author Seth
	 */
	public Popup(String fxmlLink) {
		createPopup(fxmlLink, "");
		// Don't pass a style path so it will ignore the stylesheet
	}
	
	/**
	 * Creates a pop-up with the elements specified in the
	 * given FXML file.
	 * 
	 * @author Seth
	 */
	public Popup(String fxmlLink, String stylePath) {
		createPopup(fxmlLink, stylePath);
	}
	
	/**
	 * This method will create and show a pop-up window
	 * based on the FXML file and the stylesheet parameter.
	 * If there is no stylesheet for this pop-up, pass in
	 * an empty String ("") and it will ignore it.
	 * 
	 * @param fxmlFile
	 * path to the FXML file of this pop-up
	 * @param stylePath
	 * the path to the stylesheet of this pop-up (optional)
	 * 
	 * @author Seth
	 */
	private void createPopup(String fxmlFile, String stylePath) {
		
		FXMLLoader loader;
		
		popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.initOwner(new Stage());
				
		try {
			loader = new FXMLLoader(getClass().getResource(fxmlFile));
			popupParent = loader.load();
			popupScene = new Scene(popupParent);
			
			controller = loader.getController();
			
			if(FileManager.doesFileExist(stylePath)) {
				popupScene.getStylesheets().add(getClass().getResource(stylePath).toExternalForm());
			} else if(stylePath.length() > 0) {
				System.out.println("style sheet could not be loaded from: " + stylePath);
			}
			
			popup.setScene(popupScene);
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		popup.showAndWait();
		// Will halt all operation until pop-up is closed
		
	}
	
	/**
	 * Sets the message of the pop-up to the provided String.
	 * 
	 * @param newText
	 * the new message of the pop-up
	 */
	public void setText(String newText) {
		controller.getLabel_text().setText(newText);
	}
	
	/**
	 * Sets the text of the two buttons on pop-up.
	 * The buttonId is the button that will be updated.
	 * 0 is the Cancel button by default, and 1 is the
	 * Confirm button by default.
	 * 
	 * @param buttonId
	 * number of the button to update (0 = cancel, 1 = confirm)
	 * @param text
	 * new text of the button
	 */
	public void setButtonText(int buttonId, String text) {
		
		if(buttonId == 0) {
			controller.getButton_cancel().setText(text);
		} else if(buttonId == 1) {
			controller.getButton_confirm().setText(text);
		}
		
	}
	
	/**
	 * Returns the value of the user's acceptance. True, 
	 * if confirmed was clicked. Otherwise, false.
	 * 
	 * @return
	 * True if "confirm" was clicked. Otherwise, false.
	 */
	public boolean getState() {
		return controller.didConfirm;
	}
	
	/**
	 * Closes this pop-up window.
	 */
	public void closePopup() {
		popup.close();
	}
	
}

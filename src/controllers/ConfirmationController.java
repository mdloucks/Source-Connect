package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ConfirmationController implements Initializable {
	
	public boolean didConfirm;
	
	// Panes
	@FXML
	protected Pane pane_message;
	
	@FXML
	protected Pane pane_buttons;
	
	// Labels
	@FXML
	protected Label label_text;
	
	// Buttons
	@FXML
	protected Button button_cancel;
	
	@FXML
	protected Button button_confirm;
	
	
	public ConfirmationController() {
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Pop-up controller initalized");
	}
	
	/**
	 * Sets the message of the pop-up to the provided String.
	 * 
	 * @param newText
	 * the new message of the pop-up
	 */
	public void setText(String newText) {
		label_text.setText(newText);
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
			button_cancel.setText(text);
		} else if(buttonId == 1) {
			button_confirm.setText(text);
		}
		
	}
	
	public void confirm(ActionEvent event) {
		didConfirm = true;
		closePopup();
	}
	
	public void cancel(ActionEvent event) {
		didConfirm = false;
		closePopup();
	}
	
	public void closePopup() {
		Stage popup = (Stage) pane_message.getScene().getWindow();
		popup.close();
	}
	
	// Get / Set methods
	
	public void setPane_message(Pane pane_message) {
		this.pane_message = pane_message;
	}
	public Pane getPane_message() {
		return pane_message;
	}
	
	public void setPane_buttons(Pane pane_buttons) {
		this.pane_buttons = pane_buttons;
	}
	public Pane getPane_buttons() {
		return pane_buttons;
	}
	
	public void setlabel_text(Label label_text) {
		this.label_text = label_text;
	}
	public Label getLabel_text() {
		return label_text;
	}
	
	public void setButton_cancel(Button button_cancel) {
		this.button_cancel = button_cancel;
	}
	public Button getButton_cancel() {
		return button_cancel;
	}
	
	public void setButton_confirm(Button button_confirm) {
		this.button_confirm = button_confirm;
	}
	public Button getButton_confirm() {
		return button_confirm;
	}
	
}

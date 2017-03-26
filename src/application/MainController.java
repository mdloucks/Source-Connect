package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainController {

	@FXML
	private Label login_status;
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	
	/**
	 * @param int a
	 */
	public void login(ActionEvent event) {
		
		try {

		} catch (NumberFormatException e) {
			login_status.setText("UserName or password incorrect");
			login_status.setVisible(true);
			e.printStackTrace();
		}
	}
	
}

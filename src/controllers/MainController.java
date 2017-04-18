package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.FileManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {

	// References to all of our labels
	
	// Labels
	@FXML
	private Label login_status;
	
	// Text Fields
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	
	// Menus
	@FXML
	private Menu Menu_File;
	@FXML
	private Menu Menu_Edit;
	@FXML
	private Menu Menu_Help;
	@FXML
	private Menu Menu_New;
	
	// Menu Items
	@FXML
	private MenuItem MenuItem_Repository;
	@FXML
	private MenuItem MenuItem_Branch;
	@FXML
	private MenuItem MenuItem_Close;
	
	// panes
	@FXML
	private BorderPane BorderPane_Main;
	@FXML
	private SplitPane SplitPane_Repositories;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void initialize() {
		
	}
	
	/**
	 * TODO send login info to a web site
	 */
	public void login(ActionEvent event) {

		try {

		} catch (NumberFormatException e) {
			login_status.setText("UserName or password incorrect");
			login_status.setVisible(true);
			e.printStackTrace();
		}
	}
	
	public void exit(ActionEvent e) {
		Platform.exit();
		System.exit(0);
	}
	
	/** 
	 * an on click method that saves a configuration file that is loaded when you re launch the app
	 * 
	 * @param e
	 */
	public void initRepository(ActionEvent e) {
		System.out.println("Initializing Repository...");
		
		// adds a new repository label onto the vertical split pane
		//SplitPane_Repositories.getChildrenUnmodifiable().add(arg0);
		
		FileManager.initRepository();
	}
}

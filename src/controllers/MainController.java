package controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.ExceptionHandler;
import application.FileManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {

	// References to all of our labels
	
	// Labels
	@FXML
	private static Label login_status;
	
	// Text Fields
	@FXML
	private static TextField username;
	@FXML
	private static TextField password;
	
	// Menus
	@FXML
	private static Menu Menu_File;
	@FXML
	private static Menu Menu_Edit;
	@FXML
	private static Menu Menu_Help;
	@FXML
	private static Menu Menu_New;
	
	// Menu Items
	@FXML
	private static MenuItem MenuItem_Repository;
	@FXML
	private static MenuItem MenuItem_Branch;
	@FXML
	private static MenuItem MenuItem_Close;
	
	// panes
	@FXML
	private static BorderPane BorderPane_Main;
	@FXML
	private static SplitPane SplitPane_Repositories;
	@FXML
	private static AnchorPane AnchorPane_top;
	@FXML
	private static AnchorPane AnchorPane_botton;
	
	// files
	
	@FXML
	private ArrayList<Label> Label_localRepositories;
	
	@FXML ArrayList<Label> Label_remoteRepositories;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

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

		FileManager.initRepository();
	}
	
	
	public static Label getLogin_status() {
		return login_status;
	}

	public static TextField getUsername() {
		return username;
	}

	public static TextField getPassword() {
		return password;
	}

	public static Menu getMenu_File() {
		return Menu_File;
	}

	public static Menu getMenu_Edit() {
		return Menu_Edit;
	}

	public static Menu getMenu_Help() {
		return Menu_Help;
	}

	public static Menu getMenu_New() {
		return Menu_New;
	}

	public static MenuItem getMenuItem_Repository() {
		return MenuItem_Repository;
	}

	public static MenuItem getMenuItem_Branch() {
		return MenuItem_Branch;
	}

	public static MenuItem getMenuItem_Close() {
		return MenuItem_Close;
	}

	public static BorderPane getBorderPane_Main() {
		return BorderPane_Main;
	}

	public static SplitPane getSplitPane_Repositories() {
		return SplitPane_Repositories;
	}

	public static AnchorPane getAnchorPane_top() {
		return AnchorPane_top;
	}

	public static AnchorPane getAnchorPane_botton() {
		return AnchorPane_botton;
	}

	public static ArrayList<Label> getLabel_localRepositories() {
		return Label_localRepositories;
	}

	public static ArrayList<Label> getLabel_remoteRepositories() {
		return Label_remoteRepositories;
	}
}

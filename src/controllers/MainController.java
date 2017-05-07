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
	@FXML
	private AnchorPane AnchorPane_top;
	@FXML
	private static AnchorPane AnchorPane_bottom;
	
	
	// files
	
	@FXML
	private ArrayList<Label> Label_localRepositories;
	
	@FXML ArrayList<Label> Label_remoteRepositories;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("initialized! (Line 76)");
		System.out.println(AnchorPane_bottom);
		System.out.println(AnchorPane_top);
	}
	
	public void initialize() {
		
	}
	
	public static void loadRepositoryLabels() {
		
		try {
			
			// make sure all files are loaded 
			FileManager.loadConfigurations();
			
			if(FileManager.getRepositories().isEmpty()) {
				System.out.println("no local repositories found");
			}
			
			System.out.println("initializing controllers...");
			
			// loops through all of the user's repositories makes a button and adds it to stage
			for(File f : FileManager.getRepositories()) {
				
				Button b = new Button(f.getName());
				b.setPrefWidth(AnchorPane_bottom.getWidth());
								
			    b.setOnAction(new EventHandler<ActionEvent>() {
		             @Override public void handle(ActionEvent e) {
		                  System.out.println("Repository: " + f.getName().toString() + " has been selected");
		             }
		        });
				
			    AnchorPane_bottom.getChildren().add(b);
				
				System.out.println("added repository button: " + f.getAbsolutePath());
			}
			
		} catch(NullPointerException e) {
			
			ExceptionHandler.popup("could not retrieve a value from a file in the listed repositories", e, false);
			
		} catch(Exception e) {
			
		}
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
}

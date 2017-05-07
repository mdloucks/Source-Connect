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
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

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
	private Menu menu_file;
	@FXML
	private Menu menu_edit;
	@FXML
	private Menu menu_help;
	@FXML
	private Menu menu_new;
	
	// Menu Items
	@FXML
	private MenuItem menuItem_repositories;
	@FXML
	private MenuItem menuItem_branch;
	@FXML
	private MenuItem menuItem_close;
	
	// panes
	@FXML
	private BorderPane borderPane_main;
	@FXML
	private SplitPane splitPane_repositories;
	@FXML
	private AnchorPane anchorPane_top;
	@FXML
	private AnchorPane anchorPane_botton;
	
	// files
	
	private ArrayList<Label> label_localRepositories;
	
	private ArrayList<Label> label_remoteRepositories;
	
	// tool bars
	
	@FXML
	private ToolBar toolBar_main;
	
	// tool bar buttons
	
	@FXML 
	private Button button_stage;
	
	@FXML 
	private Button button_commit;
	
	@FXML 
	private Button button_fetch;
	
	@FXML 
	private Button button_pull;
	
	// vboxes
	
	@FXML
	private VBox vBox_mainFileContainer;
	
	@FXML 
	private VBox vBox_menuBox;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		loadRepositoryLabels();
	}
	
	/**
	 * creates a label on the left side of the window that shows labels for all repositories
	 * 
	 */
	private void loadRepositoryLabels() {
		
		try {

			if(FileManager.getRepositories().isEmpty()) {
				
				System.out.println("no local repositories found");
				
			} else {
				
				System.out.println("initializing buttons for user repositories...");
				
				// loops through all of the user's repositories makes a button and adds it to stage
				for(File f : FileManager.getRepositories()) {
					
					Button b = new Button(f.getName());
					b.setText(f.getName());
					b.prefWidthProperty().bind(anchorPane_botton.widthProperty());
					
				    b.setOnAction(new EventHandler<ActionEvent>() {
			             @Override public void handle(ActionEvent e) {
			                  System.out.println("Repository: " + f.getName().toString() + " has been selected");
			                  
			                  
			             }
			        });
					
					anchorPane_botton.getChildren().add(b);
					
					System.out.println("added repository button: " + f.getAbsolutePath());
				}
			}
			

			
		} catch(NullPointerException e) {
			
			ExceptionHandler.popup("could not retrieve a value from a file in the listed repositories", e, false);
			
		} catch(Exception e) {
			
		}
	}
	
	/**
	 * creates a ton of labels for all the files in a directory and puts them on the main vbox
	 * 
	 * @param directory
	 */
	private void displayFiles(File directory) {
		
		if(!directory.isDirectory())
			return;
		
		for(File f : FileManager.getFiles(directory.getAbsolutePath())) {
			
		}
		
	}

	
	/**
	 * TODO send login info to a web site
	 * 
	 * WIP
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

	public Label getLogin_status() {
		return login_status;
	}

	public void setLogin_status(Label login_status) {
		this.login_status = login_status;
	}

	public TextField getUsername() {
		return username;
	}

	public void setUsername(TextField username) {
		this.username = username;
	}

	public TextField getPassword() {
		return password;
	}

	public void setPassword(TextField password) {
		this.password = password;
	}

	public Menu getMenu_file() {
		return menu_file;
	}

	public void setMenu_file(Menu menu_file) {
		this.menu_file = menu_file;
	}

	public Menu getMenu_edit() {
		return menu_edit;
	}

	public void setMenu_edit(Menu menu_edit) {
		this.menu_edit = menu_edit;
	}

	public Menu getMenu_help() {
		return menu_help;
	}

	public void setMenu_help(Menu menu_help) {
		this.menu_help = menu_help;
	}

	public Menu getMenu_new() {
		return menu_new;
	}

	public void setMenu_new(Menu menu_new) {
		this.menu_new = menu_new;
	}

	public MenuItem getMenuItem_repositories() {
		return menuItem_repositories;
	}

	public void setMenuItem_repositories(MenuItem menuItem_repositories) {
		this.menuItem_repositories = menuItem_repositories;
	}

	public MenuItem getMenuItem_branch() {
		return menuItem_branch;
	}

	public void setMenuItem_branch(MenuItem menuItem_branch) {
		this.menuItem_branch = menuItem_branch;
	}

	public MenuItem getMenuItem_close() {
		return menuItem_close;
	}

	public void setMenuItem_close(MenuItem menuItem_close) {
		this.menuItem_close = menuItem_close;
	}

	public BorderPane getBorderPane_main() {
		return borderPane_main;
	}

	public void setBorderPane_main(BorderPane borderPane_main) {
		this.borderPane_main = borderPane_main;
	}

	public SplitPane getSplitPane_repositories() {
		return splitPane_repositories;
	}

	public void setSplitPane_repositories(SplitPane splitPane_repositories) {
		this.splitPane_repositories = splitPane_repositories;
	}

	public AnchorPane getAnchorPane_top() {
		return anchorPane_top;
	}

	public void setAnchorPane_top(AnchorPane anchorPane_top) {
		this.anchorPane_top = anchorPane_top;
	}

	public AnchorPane getAnchorPane_botton() {
		return anchorPane_botton;
	}

	public void setAnchorPane_botton(AnchorPane anchorPane_botton) {
		this.anchorPane_botton = anchorPane_botton;
	}

	public ArrayList<Label> getLabel_localRepositories() {
		return label_localRepositories;
	}

	public void setLabel_localRepositories(ArrayList<Label> label_localRepositories) {
		this.label_localRepositories = label_localRepositories;
	}

	public ArrayList<Label> getLabel_remoteRepositories() {
		return label_remoteRepositories;
	}

	public void setLabel_remoteRepositories(ArrayList<Label> label_remoteRepositories) {
		this.label_remoteRepositories = label_remoteRepositories;
	}

	public ToolBar getToolBar_main() {
		return toolBar_main;
	}

	public void setToolBar_main(ToolBar toolBar_main) {
		this.toolBar_main = toolBar_main;
	}
	
	
}

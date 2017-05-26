package controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.FileManager;
import application.Popup;
import application.Repository;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainController implements Initializable {

	public MainController() {
		System.out.println("initializing main controller...");
	}
	
	// References to all of our labels
	
	// Labels
	@FXML
	protected Label login_status;
	
	// Text Fields
	@FXML
	protected TextField username;
	@FXML
	protected TextField password;
	
	// Menus
	@FXML
	protected Menu menu_file;
	@FXML
	protected Menu menu_edit;
	@FXML
	protected Menu menu_help;
	@FXML
	protected Menu menu_new;
	
	// Menu Items
	@FXML
	protected MenuItem menuItem_repositories;
	@FXML
	protected MenuItem menuItem_branch;
	@FXML
	protected MenuItem menuItem_close;
	
	// panes
	@FXML
	protected BorderPane borderPane_main;
	
	// files
	
	protected ArrayList<Label> label_localRepositories;
	
	protected ArrayList<Label> label_remoteRepositories;
	
	// tool bars
	
	@FXML
	protected ToolBar toolBar_main;
	
	// tool bar buttons
	
	@FXML 
	protected Button button_stage;
	
	@FXML 
	protected Button button_commit;
	
	@FXML 
	protected Button button_fetch;
	
	@FXML 
	protected Button button_pull;
	
	@FXML
	protected Button button_push;
	
	// vboxes
	
	@FXML 
	protected VBox vBox_menuBox;
	
	@FXML
	protected ButtonBar buttonBar_mainBar;
		
	@FXML
	protected VBox vBox_repositories;
	
	@FXML
	protected VBox vBox_mainContainer;
	
	@FXML
	protected VBox vBox_fileContainer;
	
	// hboxes
	
	@FXML
	protected HBox hBox_fileInfo;
	
	// current selected button reference
	File selectedFile;
	
	protected ArrayList<Button> repositoryButtons;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		System.out.println("main controller has been initialized");
		new ButtonController(this);
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
	
	// Main menu bar actions
	
	// File
	public void initRepository(ActionEvent e) {
		new Repository();
	}
	
	public void initBranch(ActionEvent e) {
		// TODO: Create new branch here
	}
	
	public void exit() {
		Platform.exit();
		System.exit(0);
	}
	
	// Edit
	/**
	 * This method will delete all of the files within the
	 * selected repository, as well as the repository directory
	 * itself. The files within the directory must be deleted
	 * first because Java cannot remove directories that contain
	 * data.
	 * 
	 * @param e
	 * ActionEvent object
	 * 
	 * @see {@link File#delete()}
	 * 
	 * @author Seth
	 */
	public void deleteRepository(ActionEvent e) {
		
		Popup confirmDelete = new Popup("/resources/fxml/confirmation.fxml");
		
		if(confirmDelete.getState()) {
			
			System.out.println("deleting repository " + selectedFile.getName());
			
			FileManager.deleteDirectory(selectedFile.getPath());
			
			// TODO: Remove repository from display and config file
			//vBox_repositories.getChildren().remove(0);
		}
		
	}
	
	// Help
	public void openAbout(ActionEvent e) {
		// TODO: Open an about pop-up
	}
	
	// Repository actions
	
	public void commit(ActionEvent event) {
		System.out.println("committing repository " + selectedFile.getName());
	}
	
	public void push(ActionEvent event) {
		System.out.println("pushing repository " + selectedFile.getName());
	}
	
	public void pull(ActionEvent event) {
		System.out.println("pulling repository " + selectedFile.getName());
	}
	
	public void fetch(ActionEvent event) {
		System.out.println("fetching repository " + selectedFile.getName());
	}
	
	public void stage(ActionEvent event) {
		System.out.println("staging repository " + selectedFile.getName());
	}
	
	// Login
	
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
	
	// Other
	
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

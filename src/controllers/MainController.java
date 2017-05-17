package controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import application.ExceptionHandler;
import application.FileManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
	
	@FXML
	private Button button_push;
	
	// vboxes
	
	@FXML 
	private VBox vBox_menuBox;
	
	@FXML
	private ButtonBar buttonBar_mainBar;
		
	@FXML
	private VBox vBox_repositories;
	
	@FXML
	private VBox vBox_mainContainer;
	
	// hboxes
	
	@FXML
	private HBox hBox_fileInfo;
	
	// current selected button reference
	File selectedFile;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		loadRepositoryLabels();
		buttonBar_mainBar.setDisable(true);
		System.out.println("disabled button bar buttons");
	}
	
	/**
	 * creates a label on the left side of the window that shows labels for all repositories
	 * 
	 */
	private void loadRepositoryLabels() {
		
		try {

			ArrayList<Button> repositoryButtons = new ArrayList<Button>();
			
			if(FileManager.getRepositories().isEmpty()) {
				
				System.out.println("no local repositories found");
				
			} else {
				
				System.out.println("initializing buttons for user repositories...");
								
				
				for(File f : FileManager.getRepositories()) {
					
					Button b = new Button(f.getName());
					b.setText(f.getName());
					b.prefWidthProperty().bind(vBox_repositories.widthProperty());
					b.setWrapText(true);
					
				    b.setOnAction(new EventHandler<ActionEvent>() {
			             @Override public void handle(ActionEvent e) {
			                  selectedFile = f;
			                  
			                  buttonBar_mainBar.setDisable(false);
			                  displayFiles(f);
			                  
			             }
			        });

				    repositoryButtons.add(b);
				
				    System.out.println("added repository button: " + f.getAbsolutePath());
					
				}
				vBox_repositories.getChildren().addAll(repositoryButtons);
				System.out.println("added buttons to vertical repository box");
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
		
		if(!directory.isDirectory()) {
			System.out.println(directory.getName() + " is not a directory");
			return;
		}
		
		if(directory.list().length == 0) {
			System.out.println("there are no files to display!");
			return;
		}
		
		System.out.println("displaying files for " + directory.getName());
		vBox_mainContainer.getChildren().clear();
		
		// returns all of the files in "directory" and loops through them
		for(File f : directory.listFiles()) {

			// horizontal container for all of the file info
			HBox hbox = new HBox(0);

			// get the file extension
			String extension = "";
			
			int i = f.getAbsolutePath().lastIndexOf('.');
		
			if (i > 0) {
			    extension = f.getAbsolutePath().substring(i+1);
			} else {
				extension = "File Folder";
			}
			
			// get precise size
			long size = f.length() / 1000000000;
			String strSize = " GB";
			
			if(size < 1) {
				size = f.length() / 1000000;
				strSize = " MB";
			}
			if(size < 1) {
				size = f.length() / 1000;
				strSize = " KB";
			}
			
			Button b = new Button(f.getName() + "\t" 
					+ new SimpleDateFormat("dd/MM/yyyy H:m:s").format(
						    new Date(f.lastModified()) 
							) + "\t" 
					+ extension + "\t"
					+ Long.toString(size).concat(strSize));
			

			b.setOnMouseClicked(new EventHandler<MouseEvent>() {
				
			    @Override
			    public void handle(MouseEvent mouseEvent) {
			    	
			        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
			        	
			            if(mouseEvent.getClickCount() == 2) {
			            	
			                try {
			                	
								Desktop.getDesktop().open(f);
								System.out.println("opening file " + f.getName());
								
							} catch (IOException e) {
								
								ExceptionHandler.popup("Java could not find an appropriate default application to open this file", e, false);
								e.printStackTrace();
							}
			            }
			        }
			    }
			});
		    
			hbox.getChildren().add(b);
			vBox_mainContainer.getChildren().add(hbox);	
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
	
	public void exit() {
		Platform.exit();
		System.exit(0);
	}
	
	// Committing 
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

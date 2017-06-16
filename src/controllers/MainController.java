package controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Repository;
import application.managers.ButtonManager;
import application.managers.TreeManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainController implements Initializable {
	
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
	
	// scroll panes
	@FXML
	protected ScrollPane scrollPane_fileContainer;
	
	@FXML
	protected ScrollPane scrollPane_fileRootContainer;
	
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
	
	// file buttons
	
	@FXML 
	protected Button button_fileName;
	
	@FXML 
	protected Button button_fileModified;
	
	@FXML 
	protected Button button_fileType;
	
	@FXML
	protected Button button_fileSize;
	
	
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
	protected VBox vBox_treeViewContainer;
	
	// horizontal boxes
	
	@FXML
	protected HBox hBox_fileInfo;
	
	@FXML
	protected HBox hBox_breadcrumbs;
	
	@FXML
	protected HBox hBox_folderTools;
	
	// tree views
	
	@FXML
	protected TreeView<File> treeView_localFiles;
	
	@FXML
	protected TreeView<File> treeView_remoteFiles;
	
	@FXML
	protected TreeView<File> treeView_thisPCFiles;
	
	public TreeItem<File> localRoot;
	public TreeItem<File> remoteRoot;
	public TreeItem<File> thisPCRoot;
	
	
	// current selected button reference
	File selectedFile;
	// the directory that is currently displayed by display-files()
	File selectedDirectory;
		
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		System.out.println("main controller has been initialized");
		vBox_repositories.setPrefHeight(441);
		
		this.scrollPane_fileRootContainer.setHbarPolicy(ScrollBarPolicy.NEVER);
		this.scrollPane_fileContainer.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		TreeManager.initLabels(this);
	}

	// ------------------ event listeners -------------------------
	public void sortFilesByName(ActionEvent event) {
		System.out.println("sorting by name");
		ButtonManager.displayFiles(this, this.selectedDirectory.listFiles(), 1);
	}
	
	public void sortFilesByDate(ActionEvent event) {
		System.out.println("sorting by date");
		ButtonManager.displayFiles(this, this.selectedDirectory.listFiles(), 2);
	}
	
	public void sortFilesByType(ActionEvent event) {
		System.out.println("sorting by type");
		ButtonManager.displayFiles(this, this.selectedDirectory.listFiles(), 3);
	}
	
	public void sortFilesBySize(ActionEvent event) {
		System.out.println("sorting by size");
		ButtonManager.displayFiles(this, this.selectedDirectory.listFiles(), 4);
	}
	

	
	// ----  Main menu bar actions  ----
	
	// ------------  File  ------------
	public void initRepository(ActionEvent e) {
		// Create the repository
		Repository repository = new Repository(this);
	}
	
	public void initBranch(ActionEvent e) {
		// TODO: Create new branch here
	}
	
	public void exit() {
		Platform.exit();
		System.exit(0);
	}
	
	// ------------  Edit  ------------
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
		
//		Confirmation confirmDelete = new Confirmation("/resources/css/application.css");
//		
//		ConfigFile mainConf = new ConfigFile("sc.conf");
//		TreeItem<File> localReps = treeView_localFiles.getSelectionModel().getSelectedItem();
//		int repIndex = 0; // Index of repository in the config file
//		
//		confirmDelete.setText("Are you sure you want to delete the repository " + selectedFile.getName() + "?");
//		confirmDelete.showPopup();
//		repIndex = mainConf.getRepositoryIndex(selectedFile.getPath());
//		
//		if(confirmDelete.getState()) {
//			
//			System.out.println("deleting repository " + selectedFile.getName());
//			
//			try {
//				FileManager.deleteDirectory(selectedFile.getPath());
//			} catch(NullPointerException n) {
//				n.printStackTrace();
//				System.out.println("the directory could not be found at: " + selectedFile.getAbsolutePath());
//			}
//			
//			// Remove repository from config file and display
//			mainConf.removeRepository(selectedFile.getPath());
//			// 0 is the index of the selected item
//			localReps.getParent().getChildren().remove(repIndex);
//			
//		}
		
	}
	
	// ------------  Help  ------------
	public void openAbout(ActionEvent e) {
		// TODO: Open an about pop-up
	}
	
	// ----  Repository actions  ----
	
	public void commit(ActionEvent event) {
		System.out.println("committing repository ");
	}
	
	public void push(ActionEvent event) {
		System.out.println("pushing repository ");
	}
	
	public void pull(ActionEvent event) {
		System.out.println("pulling repository ");
	}
	
	public void fetch(ActionEvent event) {
		System.out.println("fetching repository ");
	}
	
	public void stage(ActionEvent event) {
		System.out.println("staging repository ");
	}

	public Label getLogin_status() {
		return login_status;
	}


	public TextField getUsername() {
		return username;
	}


	public TextField getPassword() {
		return password;
	}


	public Menu getMenu_file() {
		return menu_file;
	}


	public Menu getMenu_edit() {
		return menu_edit;
	}


	public Menu getMenu_help() {
		return menu_help;
	}


	public Menu getMenu_new() {
		return menu_new;
	}


	public ScrollPane getScrollPane_fileContainer() {
		return scrollPane_fileContainer;
	}


	public ScrollPane getScrollPane_fileRootContainer() {
		return scrollPane_fileRootContainer;
	}


	public MenuItem getMenuItem_repositories() {
		return menuItem_repositories;
	}


	public MenuItem getMenuItem_branch() {
		return menuItem_branch;
	}


	public MenuItem getMenuItem_close() {
		return menuItem_close;
	}


	public BorderPane getBorderPane_main() {
		return borderPane_main;
	}


	public ArrayList<Label> getLabel_localRepositories() {
		return label_localRepositories;
	}


	public ArrayList<Label> getLabel_remoteRepositories() {
		return label_remoteRepositories;
	}


	public ToolBar getToolBar_main() {
		return toolBar_main;
	}


	public Button getButton_stage() {
		return button_stage;
	}


	public Button getButton_commit() {
		return button_commit;
	}


	public Button getButton_fetch() {
		return button_fetch;
	}


	public Button getButton_pull() {
		return button_pull;
	}


	public Button getButton_push() {
		return button_push;
	}


	public Button getButton_fileName() {
		return button_fileName;
	}


	public Button getButton_fileModified() {
		return button_fileModified;
	}


	public Button getButton_fileType() {
		return button_fileType;
	}


	public Button getButton_fileSize() {
		return button_fileSize;
	}


	public VBox getvBox_menuBox() {
		return vBox_menuBox;
	}


	public ButtonBar getButtonBar_mainBar() {
		return buttonBar_mainBar;
	}


	public VBox getvBox_repositories() {
		return vBox_repositories;
	}


	public VBox getvBox_mainContainer() {
		return vBox_mainContainer;
	}


	public VBox getvBox_treeViewContainer() {
		return vBox_treeViewContainer;
	}


	public HBox gethBox_fileInfo() {
		return hBox_fileInfo;
	}


	public HBox gethBox_breadcrumbs() {
		return hBox_breadcrumbs;
	}


	public HBox gethBox_folderTools() {
		return hBox_folderTools;
	}


	public TreeView<File> getTreeView_localFiles() {
		return treeView_localFiles;
	}


	public TreeView<File> getTreeView_remoteFiles() {
		return treeView_remoteFiles;
	}


	public TreeView<File> getTreeView_thisPCFiles() {
		return treeView_thisPCFiles;
	}


	public File getSelectedFile() {
		return selectedFile;
	}

	public void setLogin_status(Label login_status) {
		this.login_status = login_status;
	}


	public void setUsername(TextField username) {
		this.username = username;
	}


	public void setPassword(TextField password) {
		this.password = password;
	}


	public void setMenu_file(Menu menu_file) {
		this.menu_file = menu_file;
	}


	public void setMenu_edit(Menu menu_edit) {
		this.menu_edit = menu_edit;
	}


	public void setMenu_help(Menu menu_help) {
		this.menu_help = menu_help;
	}


	public void setMenu_new(Menu menu_new) {
		this.menu_new = menu_new;
	}


	public void setScrollPane_fileContainer(ScrollPane scrollPane_fileContainer) {
		this.scrollPane_fileContainer = scrollPane_fileContainer;
	}


	public void setScrollPane_fileRootContainer(ScrollPane scrollPane_fileRootContainer) {
		this.scrollPane_fileRootContainer = scrollPane_fileRootContainer;
	}


	public void setMenuItem_repositories(MenuItem menuItem_repositories) {
		this.menuItem_repositories = menuItem_repositories;
	}


	public void setMenuItem_branch(MenuItem menuItem_branch) {
		this.menuItem_branch = menuItem_branch;
	}


	public void setMenuItem_close(MenuItem menuItem_close) {
		this.menuItem_close = menuItem_close;
	}


	public void setBorderPane_main(BorderPane borderPane_main) {
		this.borderPane_main = borderPane_main;
	}


	public void setLabel_localRepositories(ArrayList<Label> label_localRepositories) {
		this.label_localRepositories = label_localRepositories;
	}


	public void setLabel_remoteRepositories(ArrayList<Label> label_remoteRepositories) {
		this.label_remoteRepositories = label_remoteRepositories;
	}


	public void setToolBar_main(ToolBar toolBar_main) {
		this.toolBar_main = toolBar_main;
	}


	public void setButton_stage(Button button_stage) {
		this.button_stage = button_stage;
	}


	public void setButton_commit(Button button_commit) {
		this.button_commit = button_commit;
	}


	public void setButton_fetch(Button button_fetch) {
		this.button_fetch = button_fetch;
	}


	public void setButton_pull(Button button_pull) {
		this.button_pull = button_pull;
	}


	public void setButton_push(Button button_push) {
		this.button_push = button_push;
	}


	public void setButton_fileName(Button button_fileName) {
		this.button_fileName = button_fileName;
	}


	public void setButton_fileModified(Button button_fileModified) {
		this.button_fileModified = button_fileModified;
	}


	public void setButton_fileType(Button button_fileType) {
		this.button_fileType = button_fileType;
	}


	public void setButton_fileSize(Button button_fileSize) {
		this.button_fileSize = button_fileSize;
	}


	public void setvBox_menuBox(VBox vBox_menuBox) {
		this.vBox_menuBox = vBox_menuBox;
	}


	public void setButtonBar_mainBar(ButtonBar buttonBar_mainBar) {
		this.buttonBar_mainBar = buttonBar_mainBar;
	}


	public void setvBox_repositories(VBox vBox_repositories) {
		this.vBox_repositories = vBox_repositories;
	}


	public void setvBox_mainContainer(VBox vBox_mainContainer) {
		this.vBox_mainContainer = vBox_mainContainer;
	}


	public void setvBox_treeViewContainer(VBox vBox_treeViewContainer) {
		this.vBox_treeViewContainer = vBox_treeViewContainer;
	}


	public void sethBox_fileInfo(HBox hBox_fileInfo) {
		this.hBox_fileInfo = hBox_fileInfo;
	}


	public void sethBox_breadcrumbs(HBox hBox_breadcrumbs) {
		this.hBox_breadcrumbs = hBox_breadcrumbs;
	}


	public void sethBox_folderTools(HBox hBox_folderTools) {
		this.hBox_folderTools = hBox_folderTools;
	}


	public void setTreeView_localFiles(TreeView<File> treeView_localFiles) {
		this.treeView_localFiles = treeView_localFiles;
	}


	public void setTreeView_remoteFiles(TreeView<File> treeView_remoteFiles) {
		this.treeView_remoteFiles = treeView_remoteFiles;
	}


	public void setTreeView_thisPCFiles(TreeView<File> treeView_thisPCFiles) {
		this.treeView_thisPCFiles = treeView_thisPCFiles;
	}


	public void setSelectedFile(File selectedFile) {
		this.selectedFile = selectedFile;
	}

	public File getSelectedDirectory() {
		return selectedDirectory;
	}
	
	public void setSelectedDirectory(File directory) {
		this.selectedDirectory = directory;
	}
	
	
}

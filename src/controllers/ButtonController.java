package controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import application.ExceptionHandler;
import application.FileManager;
import application.Git;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 
 * controller class with same functionallity as the MainController
 * that manages just buttons 
 * 
 * @author louck
 *
 */
public class ButtonController extends MainController {

	MainController m;
	
	public ButtonController(MainController mc) {
		m = mc;
		loadRepositoryLabels();
		loadBreadcrumbs();
	}
	
	/**
	 * creates a label on the left side of the window that shows labels for all repositories
	 * 
	 */
	protected void loadRepositoryLabels() {
		
		try {

			Image imgFolder = new Image(getClass().getResource("/resources/img/icon_folder.png").toURI().toString(), 16, 16, true, true);
			
			repositoryButtons = new ArrayList<Button>();
			
			if(Git.getRepositories().isEmpty()) {
				
				System.out.println("no local repositories found");
				return;
				
			} else {
				
				System.out.println("initializing buttons for user repositories...");
								
				// add local repository labels
				for(File f : Git.getRepositories()) {
					
					Button b = new Button(f.getName());
					b.setText(f.getName());
					b.prefWidthProperty().bind(m.vBox_repositories.widthProperty());
					b.setGraphic(new ImageView(imgFolder));
					b.setWrapText(true); 
					
				    b.setOnAction(new EventHandler<ActionEvent>() {
			             @Override public void handle(ActionEvent e) {
			                  selectedFile = f;
			                  
			                  m.buttonBar_mainBar.setDisable(false);
			                  displayFiles(f);
			                  
			             }
			        });

				    repositoryButtons.add(b);					
				}
				m.vBox_repositories.getChildren().addAll(repositoryButtons);
				System.out.println("added buttons to vertical repository box");
			}
			
		} catch(NullPointerException e) {
			
			ExceptionHandler.popup("could not retrieve a value from a file in the listed repositories", e, false);
			
		} catch(Exception e) {
			
		}
	}
	
	protected void loadBreadcrumbs() {
		Button b = new Button("breadcrumbs 1 ->");
		Button bc = new Button("breadcrumbs 2 ->");
		Button bd = new Button("breadcrumbs 3");
		m.hBox_breadcrumbs.getChildren().addAll(b, bc, bd);
	}
	
	/**
	 * creates a ton of labels for all the files in a directory and puts them on the main vbox
	 * 
	 * @param directory
	 */
	protected void displayFiles(File directory) {
		
		if(!directory.isDirectory()) {
			
			System.out.println(directory.getName() + " is not a directory");
			return;
			
		} else if(directory.list().length == 0) {
			
			System.out.println("there are no files to display in " + directory.getName());
			return;
		}
		
		System.out.println("displaying files for " + directory.getName());
		
		// Hbox container
		VBox fileContainer = new VBox();
		m.scrollPane_fileContainer.setContent(null);
		m.scrollPane_fileContainer.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		for(File f : directory.listFiles()) {

			HBox hbox = new HBox(0);
			
			String name = f.getName().length() > 20 ? f.getName().substring(0, 20) : f.getName();
			String date =  new SimpleDateFormat("dd/MM/yyyy H:m:s").format(new Date(f.lastModified()));
			String extension = FileManager.getExtension(f);
			String size = FileManager.getSize(f);
			
			
			StringBuilder buttonText = new StringBuilder();
			
			buttonText.append(name);
			
			
			
			// name --
			while(buttonText.length() < 30) {
				buttonText.append(" ");
			}
		
			
			buttonText.append("|");
			
			buttonText.append(date);
			
			// name date --
			while(buttonText.length() < 60) {
				buttonText.append(" ");
			}
		
			
			buttonText.append(extension);
			
			// name date extension --
			while(buttonText.length() < 75) {
				buttonText.append(" ");
			}
			
			buttonText.append(size);
			
			while(buttonText.length() < 100) {
				buttonText.append(" ");
			}
						
			Button b = new Button(buttonText.toString());
			
			
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
			
			fileContainer.getChildren().add(b);	
		}
		m.scrollPane_fileContainer.setContent(fileContainer);
	}
	
}

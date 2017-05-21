package controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import application.ExceptionHandler;
import application.Git;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

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
			                  m.selectedFile = f;
			                  
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
	
	/**
	 * creates a ton of labels for all the files in a directory and puts them on the main vbox
	 * 
	 * @param directory
	 */
	protected void displayFiles(File directory) {
		
		if(!directory.isDirectory()) {
			System.out.println(directory.getName() + " is not a directory");
			return;
		}
		
		if(directory.list().length == 0) {
			System.out.println("there are no files to display!");
			return;
		}
		
		System.out.println("displaying files for " + directory.getName());
		// remove file buttons
		m.vBox_fileContainer.getChildren().clear();
		
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
			m.vBox_fileContainer.getChildren().add(hbox);	
		}
	}
	
}

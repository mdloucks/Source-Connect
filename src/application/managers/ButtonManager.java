package application.managers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import application.Git;
import controllers.MainController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * A class that modifies buttons contained in main controller
 * to save room in that class and for readability
 * 
 * @author louck
 *
 */
public class ButtonManager {

	/**
	 * creates a 
	 * 
	 * @param controller - the main instance of MainController
	 * @param files - list of files to display
	 * 
	 * 
  	 * @param sort - how the list of files should be ordered
  	 *  <ul>
     *	<li>0 - no sort </li> 
	 *  <li>1 - sort by name </li> 
	 *  <li>2 - sort by date </li> 
	 *  <li>3 - sort by extension </li> 
	 *  <li>4 - sort by size </li> 
	 * 	</ul>
	 */
	public static void displayFiles(MainController controller, File files[], int sort) {
		

		if(files.length == 0 || files == null) {
			System.out.println("the given array contains no files to display");
			controller.getScrollPane_fileContainer().setContent(null);
			return;
		}
		
		controller.setSelectedDirectory(files[0].getParentFile());
		
		
		VBox fileContainer = new VBox();
		// clear all of the old buttons out
		controller.getScrollPane_fileContainer().setContent(null);

				

		if(sort == 1) {
			
			files = FileManager.getSortedByName(files);
			
			// date
		} else if(sort == 2) {
			
			files = FileManager.getSortedByDate(files);
			
			// type
		} else if(sort == 3) {
			
			files = FileManager.getSortedByExt(files);
			
			// size
		} else if(sort == 4) {
			
			files = FileManager.getSortedBySize(files);
			
		}
		
		
		for(File f : files) {
						
			if(f.isHidden()) {
				continue;
			}
			
			String name = f.getName().length() > 20 ? f.getName().substring(0, 20) : f.getName();
			String date = new SimpleDateFormat("dd/MM/yyyy H:m:s").format(new Date(f.lastModified()));
			String extension = FileManager.getExtension(f);
			String size = FileManager.getSize(f);
			
			if(name.length() > 20) {
				name = name.substring(0, 25) + "...";
			}
			
			if(size.length() > 5) {
				size = "...";
			}
			
			StringBuilder buttonText = new StringBuilder();
			
			buttonText.append(name);
			
			// name --
			while(buttonText.length() < 30) {
				buttonText.append(" ");
			}
					
			buttonText.append(date);
			
			// name date --
			while(buttonText.length() < 57) {
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
					
					if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
						
						if(mouseEvent.getClickCount() == 2) {
							
							try {
								
								if(f.isDirectory()) {
									addBreadcrumb(controller, f);
									displayFiles(controller, f.listFiles(), 0);
								} else {
									
									if(f.canExecute() || f.canRead()) {
										Desktop.getDesktop().open(f);
										System.out.println("opening file " + f.getName());
									} else {
										System.out.println("could not open file " + f.getName());
									}

								}
								
							} catch (IOException e) {
								
								ExceptionManager
										.popup("Java could not find an appropriate default application to open this file", e, false);
								e.printStackTrace();
							}
						}
					}
				}
			});
			
			fileContainer.getChildren().add(b);
		}
		controller.getScrollPane_fileContainer().setContent(fileContainer);
	}

	
	/**
	 * creates and adds a bread crumb to the bread crumb container which will remove the previous 
	 * bread crumbs and move to the stated directory when clicked
	 * 
	 * @param f
	 * 
	 */
	public static void addBreadcrumb(MainController controller, File f) {
		
		Button b = new Button(f.getName());
		
		
		b.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent e) {
				
				for(int a = controller.gethBox_breadcrumbs().getChildren().size(); a >  0; a--) {
										
					if(controller.gethBox_breadcrumbs().getChildren().get(a-1) == b) {

						controller.gethBox_breadcrumbs().getChildren().remove(a, controller.gethBox_breadcrumbs().getChildren().size());
					}
				}
				
				displayFiles(controller, f.listFiles(), 0);
			}
			
		});
		
		controller.gethBox_breadcrumbs().getChildren().add(b);
	}
}

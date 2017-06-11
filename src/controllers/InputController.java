package controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import application.ExceptionHandler;
import application.FileManager;
import application.Git;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * 
 * controller class that manages buttons and other input related controls
 * 
 *
 */
public class InputController extends MainController {
	
	MainController controller;
	
	public InputController(MainController mc) {
		controller = mc;
		initLabels();
	}
	
	/**
	 * adds all labels loaded by other methods into the window
	 * 
	 * TODO inject repository labels into tree view
	 */
	protected void initLabels() {
		
		TreeItem<File> localRoot = new TreeItem<File>(new File("local repositories"));
		TreeItem<File> remoteRoot = new TreeItem<File>(new File("remote repositories"));
		TreeItem<File> thisPCRoot = new TreeItem<File>(new File("This PC"));
		
		addLocalRepositoryTreeItems(localRoot);
		addThisPCTreeItems(thisPCRoot);
		
		addTreeItemHandlers(localRoot, remoteRoot, thisPCRoot);
		
		controller.treeView_localFiles.setRoot(localRoot);
		controller.treeView_remoteFiles.setRoot(remoteRoot);
		controller.treeView_thisPCFiles.setRoot(thisPCRoot);
	}
	
	protected void addTreeItemHandlers(TreeItem localRoot, TreeItem remoteRoot, TreeItem thisPCRoot) {
		
		localRoot.expandedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean isExpanded) {
				
				if (isExpanded) {
					controller.treeView_localFiles.setPrefHeight((localRoot.getChildren().size() + 1) * 32);
				} else {
					controller.treeView_localFiles.setPrefHeight(50);
				}
				
			}
		});
		
		controller.treeView_localFiles.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<TreeItem<File>>() {
					
					@Override
					public void changed(ObservableValue<? extends TreeItem<File>> observable, TreeItem<File> oldValue, TreeItem<File> newValue) {
						controller.hBox_breadcrumbs.getChildren().clear();
						displayFiles(newValue.getValue());
					}
				});
		
		controller.treeView_localFiles.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<TreeItem<File>>() {
					
					// Beware! Voo-doo magic lies below!
					@Override
					public void changed(ObservableValue<? extends TreeItem<File>> observable, TreeItem<File> oldValue, TreeItem<File> newValue) {
						TreeItem<File> selectedItem = (TreeItem<File>) newValue;
						controller.selectedFile = selectedItem.getValue();
					}
				});
		
		remoteRoot.expandedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean isExpanded) {
				
				if (isExpanded) {
					controller.treeView_remoteFiles.setPrefHeight((remoteRoot.getChildren().size() + 1) * 32);
				} else {
					controller.treeView_remoteFiles.setPrefHeight(50);
				}
				
			}
		});
		
		controller.treeView_thisPCFiles.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<TreeItem<File>>() {
					
					@Override
					public void changed(ObservableValue<? extends TreeItem<File>> observable, TreeItem<File> oldValue, TreeItem<File> newValue) {
						controller.hBox_breadcrumbs.getChildren().clear();
						displayFiles(newValue.getValue());
					}
				});
		
		thisPCRoot.expandedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean isExpanded) {
				
				if (isExpanded) {
					controller.treeView_thisPCFiles.setPrefHeight((thisPCRoot.getChildren().size() + 1) * 32);
				} else {
					controller.treeView_thisPCFiles.setPrefHeight(50);
				}
				
			}
		});
	}
	
	protected void addLocalRepositoryTreeItems(TreeItem<File> root) {
		
		try {
			Image img = new Image(getClass().getResource("/resources/img/icon_folder.png").toURI()
					.toString(), 16, 16, true, true);
			
			for (File f : Git.getRepositories()) {
				
				TreeItem<File> item = new TreeItem<File>(f);
				EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						controller.selectedFile = f;
					}
				};
				
				item.setGraphic(new ImageView(img));
				item.addEventHandler(MouseEvent.ANY, handler);
				
				root.getChildren().add(item);
			}
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	
	protected void addThisPCTreeItems(TreeItem<File> root) {
		
		try {
			Image img = new Image(getClass().getResource("/resources/img/icon_folder.png").toURI()
					.toString(), 16, 16, true, true);
			
			for (File f : File.listRoots()) {
				
				TreeItem<File> item = new TreeItem<File>(f);
				item.setGraphic(new ImageView(img));
				
				root.getChildren().add(item);
			}
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected void loadBreadcrumbs() {
		Button b = new Button("breadcrumbs 1 ->");
		Button bc = new Button("breadcrumbs 2 ->");
		Button bd = new Button("breadcrumbs 3");
		controller.hBox_breadcrumbs.getChildren().addAll(b, bc, bd);
	}
	
	/**
	 * creates a ton of labels for all the files in a directory and puts them on
	 * the main vbox
	 * 
	 * @param directory
	 */
	protected void displayFiles(File directory) {
		
		if (!directory.isDirectory()) {
			
			System.out.println(directory.getName() + " is not a directory");
			return;
			
		}
		
		System.out.println("displaying files for " + directory.getName());
		
		// Hbox container
		VBox fileContainer = new VBox();
		controller.scrollPane_fileContainer.setContent(null);
		controller.scrollPane_fileContainer.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		for (File f : directory.listFiles()) {
			
			HBox hbox = new HBox(0);
			
			String name = f.getName().length() > 20 ? f.getName().substring(0, 20) : f.getName();
			String date = new SimpleDateFormat("dd/MM/yyyy H:m:s").format(new Date(f.lastModified()));
			String extension = FileManager.getExtension(f);
			String size = FileManager.getSize(f);
			
			StringBuilder buttonText = new StringBuilder();
			
			buttonText.append(name);
			
			// name --
			while (buttonText.length() < 30) {
				buttonText.append(" ");
			}
			
			buttonText.append("|");
			
			buttonText.append(date);
			
			// name date --
			while (buttonText.length() < 60) {
				buttonText.append(" ");
			}
			
			buttonText.append(extension);
			
			// name date extension --
			while (buttonText.length() < 75) {
				buttonText.append(" ");
			}
			
			buttonText.append(size);
			
			while (buttonText.length() < 100) {
				buttonText.append(" ");
			}
			
			Button b = new Button(buttonText.toString());
			
			b.setOnMouseClicked(new EventHandler<MouseEvent>() {
				
				@Override
				public void handle(MouseEvent mouseEvent) {
					
					if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
						
						if (mouseEvent.getClickCount() == 2) {
							
							try {
								
								if (f.isDirectory()) {
									addBreadcrumb(f);
									displayFiles(f);
								} else {
									Desktop.getDesktop().open(f);
									System.out.println("opening file " + f.getName());
								}
								
							} catch (IOException e) {
								
								ExceptionHandler
										.popup("Java could not find an appropriate default application to open this file", e, false);
								e.printStackTrace();
							}
						}
					}
				}
			});
			
			fileContainer.getChildren().add(b);
		}
		controller.scrollPane_fileContainer.setContent(fileContainer);
	}
	
	/**
	 * 
	 * @param f
	 */
	protected void addBreadcrumb(File f) {
		
		Button b = new Button(f.getName());
		
		b.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent e) {
				// TODO GET RID OF THIS LOOP
				for (Node n : controller.hBox_breadcrumbs.getChildren()) {
					
					n.setOnMouseClicked(new EventHandler<MouseEvent>() {
						
						@Override
						public void handle(MouseEvent event) {
							
							for (int a = controller.hBox_breadcrumbs.getChildren().size() - 1; a > 0; a--) {
								
								if (controller.hBox_breadcrumbs.getChildren().get(a) == b) {
									
									controller.hBox_breadcrumbs.getChildren()
											.remove(a + 1, controller.hBox_breadcrumbs.getChildren().size());
									
								}
							}
							
						}
					});
				}
				
				displayFiles(f);
			}
			
		});
		
		controller.hBox_breadcrumbs.getChildren().add(b);
	}
	
}

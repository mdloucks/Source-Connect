package application.managers;

import java.io.File;

import application.Git;
import controllers.MainController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


/**
 * A class that modifies tree items and views contained in main controller
 * to save room in that class and for readability
 * 
 * @author louck
 *
 */

public class TreeManager {
	
	/**
	 * creates three tree items to add to the tree view container and formats them with different methods such as...
	 * 
	 * addLocalRepositoryRoots()
	 * addThisPCTreeItems()
	 * addTreeItemHandlers()
	 * 
	 * @param controller
	 */
	public static void initLabels(MainController controller) {
		
		controller.localRoot = new TreeItem<File>(new File("local repositories"));
		controller.remoteRoot = new TreeItem<File>(new File("remote repositories"));
		controller.thisPCRoot = new TreeItem<File>(new File("This PC"));
		
		controller.getTreeView_localFiles().setPrefHeight(25);
		controller.getTreeView_remoteFiles().setPrefHeight(25);
		controller.getTreeView_thisPCFiles().setPrefHeight(25);
		
		
		// add remote tree items
		addLocalRepositoryTreeItems(controller, controller.localRoot);
		addThisPCTreeItems(controller, controller.thisPCRoot);
		
		addTreeItemHandlers(controller, controller.localRoot, controller.remoteRoot, controller.thisPCRoot);
		
		controller.getTreeView_localFiles().setRoot(controller.localRoot);
		controller.getTreeView_remoteFiles().setRoot(controller.remoteRoot);
		controller.getTreeView_thisPCFiles().setRoot(controller.thisPCRoot);
	}
	
	/**
	 * takes all of the tree roots from the main tree view and adds some functionallity to them
	 * 
	 * TODO equally space containers
	 * 
	 * @param controller
	 * @param localRoot
	 * @param remoteRoot
	 * @param thisPCRoot
	 */
	private static void addTreeItemHandlers(MainController controller, TreeItem localRoot, TreeItem remoteRoot, TreeItem thisPCRoot) {
		
		// -----------------------------------------------------------------------------------------------------------------
		// listeners for any tree item inside of the main root or the main root itself
		// -----------------------------------------------------------------------------------------------------------------
		controller.getTreeView_localFiles().getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<TreeItem<File>>() {
					
					@Override
					public void changed(ObservableValue<? extends TreeItem<File>> observable, TreeItem<File> oldValue, TreeItem<File> newValue) {
						controller.gethBox_breadcrumbs().getChildren().clear();
						
						if(newValue != controller.localRoot) {
							ButtonManager.displayFiles(controller, newValue.getValue().listFiles(), 0);
						} 
					}
				});
		
		controller.getTreeView_localFiles().getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<TreeItem<File>>() {
					
					// Beware! Voodoo magic lies below!
					@Override
					public void changed(ObservableValue<? extends TreeItem<File>> observable, TreeItem<File> oldValue, TreeItem<File> newValue) {
						TreeItem<File> selectedItem = (TreeItem<File>) newValue;
						controller.setSelectedFile(selectedItem.getValue());
					}
				});
		

		
		controller.getTreeView_thisPCFiles().getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<TreeItem<File>>() {
					
					@Override
					public void changed(ObservableValue<? extends TreeItem<File>> observable, TreeItem<File> oldValue, TreeItem<File> newValue) {
						controller.gethBox_breadcrumbs().getChildren().clear();
						
						if(newValue != controller.thisPCRoot) {
							ButtonManager.displayFiles(controller, newValue.getValue().listFiles(), 0);
						} else {
							ButtonManager.displayFiles(controller, File.listRoots(), 0);
						}
					}
				});
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		// -----------------------------------------------------------------------------------------------------------------
		// resize listener to 'correctly' adjust the sizes when the tree views expand
		// -----------------------------------------------------------------------------------------------------------------
		thisPCRoot.expandedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean isExpanded) {
				
				if (isExpanded) {
					controller.getTreeView_thisPCFiles().setPrefHeight((thisPCRoot.getChildren().size() + 1) * 25);
				} else {
					controller.getTreeView_thisPCFiles().setPrefHeight(25);
				}
				
			}
		});
		
		localRoot.expandedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean isExpanded) {
				
				if (isExpanded) {
					controller.getTreeView_localFiles().setPrefHeight((localRoot.getChildren().size() + 1) * 25);

				} else {
					controller.getTreeView_localFiles().setPrefHeight(25);
				}
				
			}
		});
		
		remoteRoot.expandedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean isExpanded) {
				
				if (isExpanded) {
					controller.getTreeView_remoteFiles().setPrefHeight((remoteRoot.getChildren().size() + 1) * 25);
				} else {
					controller.getTreeView_remoteFiles().setPrefHeight(25);
				}
				
			}
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		// -----------------------------------------------------------------------------------------------------------------
		// click and double click event handling on root item
		// -----------------------------------------------------------------------------------------------------------------
		controller.getTreeView_thisPCFiles().setOnMouseClicked(new EventHandler<MouseEvent>()
		{
		    @Override
		    public void handle(MouseEvent mouseEvent)
		    {            
		    	
	            TreeItem<File> item = controller.getTreeView_thisPCFiles().getSelectionModel().getSelectedItem();
		    	

	            if(item == controller.thisPCRoot) {
	            	ButtonManager.displayFiles(controller, File.listRoots(), 0);
	            }

		        
		        if(mouseEvent.getClickCount() == 2) {
		        	
		        	if(item == controller.thisPCRoot) {
		        		// this makes ABSOLUTELY NO SENSE, but it works for some reason
			        	if(!controller.thisPCRoot.isExpanded()) {
			        		
			        		controller.thisPCRoot.setExpanded(false);
			        	} else {
			        		
			        		controller.thisPCRoot.setExpanded(true);
			        	}
		        	}
		        }
		    }
		});
		
		controller.getTreeView_localFiles().setOnMouseClicked(new EventHandler<MouseEvent>()
		{
		    @Override
		    public void handle(MouseEvent mouseEvent)
		    {            
		    	
	            TreeItem<File> item = controller.getTreeView_localFiles().getSelectionModel().getSelectedItem();

	            if(item == controller.localRoot) {
	            	ButtonManager.displayFiles(controller, Git.getRepositories().toArray(new File[Git.getRepositories().size()]), 0);
	            }

		        
		        if(mouseEvent.getClickCount() == 2) {
		        	
		        	if(item == controller.localRoot) {
		        		// this makes ABSOLUTELY NO SENSE, but it works for some reason
			        	if(!controller.localRoot.isExpanded()) {
			        		
			        		controller.localRoot.setExpanded(false);
			        	} else {
			        		
			        		controller.localRoot.setExpanded(true);
			        	}
		        	}
		        }
		    }
		});
		
		controller.getTreeView_remoteFiles().setOnMouseClicked(new EventHandler<MouseEvent>()
		{
		    @Override
		    public void handle(MouseEvent mouseEvent)
		    {            
		    	
	            TreeItem<File> item = controller.getTreeView_remoteFiles().getSelectionModel().getSelectedItem();
		    	

	            if(item == controller.remoteRoot) {
	            	
	            }

		        
		        if(mouseEvent.getClickCount() == 2) {
		        	
		        	if(item == controller.remoteRoot) {
		        		// this makes ABSOLUTELY NO SENSE, but it works for some reason
			        	if(!controller.remoteRoot.isExpanded()) {
			        		
			        		controller.remoteRoot.setExpanded(false);
			        	} else {
			        		
			        		controller.remoteRoot.setExpanded(true);
			        	}
		        	}
		        }
		    }
		});
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	}
	
	/**
	 * gets all of the repositories present in Git, and creates a tree item for each then adds it to the 
	 * repository items root
	 * 
	 * @param controller
	 * @param root
	 */
	private static void addLocalRepositoryTreeItems(MainController controller, TreeItem<File> root) {
		
		
		for (File f : Git.getRepositories()) {
			
			addLocalRepositoryTreeItem(controller, root, f);
		}


	}
	
//	/**
//	 * Adds a repository label to the specified group
//	 * on the display (local, remote, PC).
//	 * 
//	 * @param pathToRep
//	 * path to the repository that will be added
//	 * @param groupName
//	 * "local" - Local Repository<br>
//	 * "remote" - Remote Repository<br>
//	 * "pc" - This PC Repository
//	 * 
//	 * TODO MATT NEEDS TO DESTROY THIS ABOMINATION
//	 */
//	public static void addRepositoryItem(String pathToRep, String groupName) {
//		
//		TreeItem<File> group = null;
//		TreeItem<File> repItem = null;
//		Image img = null;
//		File repFile = new File(pathToRep);
//		Git.getRepositories().add(repFile);
//		
//		// Load the group
//		if(groupName.equalsIgnoreCase("local")) {
//			group = treeView_localFiles.getRoot();
//		} else if(groupName.equalsIgnoreCase("remote")) {
//			group = treeView_remoteFiles.getRoot();
//		} else if(groupName.equalsIgnoreCase("pc")) {
//			group = treeView_thisPCFiles.getRoot();
//		}
//		
//		// Load item image
//		try {
//			img = new Image(getClass().getResource("/resources/img/icon_folder.png").toURI().toString(), 16, 16, true, true);
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
//		
//		// Create the item and the handler
//		repItem = new TreeItem<File>(repFile);
//		
//		EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				selectedFile = repFile;
//			}
//		};
//		repItem.setGraphic(new ImageView(img));
//		repItem.addEventHandler(MouseEvent.ANY, handler);
//		
//		group.getChildren().add(repItem);
//	}
	
	/**
	 * create a single tree item and add it to the given root on local-repository-tree
	 * 
	 * @param controller
	 * @param root
	 * @param item
	 */
	public static void addLocalRepositoryTreeItem(MainController controller, TreeItem<File> root, File f) {
		
		Image img = new Image("\\resources\\img\\icon_folder.png", 16, 16, true, true);
		
		TreeItem<File> item = new TreeItem<File>(f);
				
		EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.setSelectedFile(item.getValue());
			}
		};
			
		item.setGraphic(new ImageView(img));
		item.addEventHandler(MouseEvent.ANY, handler);

		root.getChildren().add(item);
	}
	
	/**
	 * generate a group of file tree items that represent the system file roots
	 * and then are added to the given tree item
	 * 
	 * @param root
	 */
	public static void addThisPCTreeItems(MainController controller, TreeItem<File> root) {
				
		for (File f : File.listRoots()) {
			
			addThisPCTreeItem(controller, root, f);
		}
	}
	
	/**
	 * load a single tree item into the this-pc tree view
	 * 
	 * @param root
	 */
	public static void addThisPCTreeItem(MainController controller, TreeItem<File> root, File f) {
		
		Image img = new Image("resources/img/icon_folder.png", 16, 16, true, true);
		
			TreeItem<File> item = new TreeItem<File>(f);
			item.setGraphic(new ImageView(img));
			
			root.getChildren().add(item);
	}


	

}


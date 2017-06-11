package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.stage.DirectoryChooser;

public class Repository {

	private String path = "";

	
	/**
	 * open a file explorer and have the user choose a repository location
	 * this creates a sc.conf file in this application that points to that file path
	 * and it also creates another sc.conf file in their selected directory
	 */
	public Repository() {
		
		System.out.println("initializing repository...");
				
		// choose a folder
		DirectoryChooser dc = new DirectoryChooser();
		dc.setTitle("new repository");
		
		ConfigFile mainConfig = new ConfigFile("sc.conf");
		File repository = dc.showDialog(null);
		path = repository.getAbsolutePath();
		
		if(repository != null && repository.isDirectory()) {
			
			// store the new file path in sc.conf
			
			System.out.println("logging new repository in main sc.conf");
			
			// Add new information					
			mainConfig.addRepository(path);
			
								
			// Repository config file
			ConfigFile repConfig = new ConfigFile(path + "/sc-local.conf", ConfigFile.Type.REP);
			repConfig.indexFiles();
			
		} else {
			System.err.println("Directory " + path + " is invalid.");
		}	
	}
	
	private ArrayList<File> files;
	
	public ArrayList<File> getFiles() {
		return files;
	}
	
	// Get / Set methods
	
	public String getPath() {
		return path;
	}
	
}

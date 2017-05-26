package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javafx.stage.DirectoryChooser;

public class Repository {

	
	
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
		
		File repository = dc.showDialog(null);
		
		if(repository != null && repository.isDirectory()) {
			
			try {
				
				PrintWriter pw;
				
				File mainConf = new File("sc.conf");
				
				// validate mainConf
				FileManager.createConfiguration(mainConf);
				
				// store the new file path in sc.conf
				if(mainConf.exists()) {
					
					pw = new PrintWriter(mainConf);
					
					System.out.println("logging new repository in main sc.conf");
					
					pw.println("REPOSITORY " + repository.getAbsoluteFile());
					
					pw.close();
					
				} else {
					System.out.println("could not find the main configuration file while initializing a new repository");
				}
				
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				
			}
			
		} else {
			System.err.println("Directory " + repository.getAbsolutePath() + " is invalid.");
		}	
	}
	
	private ArrayList<File> files;
	
	public ArrayList<File> getFiles() {
		return files;
	}
	
}

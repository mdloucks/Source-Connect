package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import application.managers.FileManager;

public class RepositoryConfiguration extends ConfigurationFile {
	
	public RepositoryConfiguration(String path) {
		super(path);
	}

	/**
	 * Adds all of the files within the same
	 * directory as the configuration file
	 * into the file itself.
	 * 
	 * @author Seth
	 */
	public void indexFiles() {
		
		ArrayList<File> files = FileManager.getFiles(new File(path).getParentFile());
		
		// File list
		for(int f = 0; f < files.size(); f++) {
			
			// Make sure the file isn't this config file
			if(!files.get(f).getName().equalsIgnoreCase("sc-local.conf")) {
				write("FILE " + files.get(f).getName());
			}
		}
		
		// TODO: Add history / commit recording here
		
	}

	@Override
	protected void validate(String path) {
		
		File file = new File(this.path);
		
		if(!file.exists()) {
			
			try {
				file.createNewFile();
				
				write("# This is a local configuration file for Source Connect repository control");
				write("");
				write("# Keywords");
				write("");
				write("FILE - a file currently being tracked in this repository");
				write("IGNORE - the listed file extension or file will not be included in your next commit or push");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}

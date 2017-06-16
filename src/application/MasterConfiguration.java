package application;

import java.io.File;
import java.io.IOException;

public class MasterConfiguration extends ConfigurationFile {

	
	public MasterConfiguration(String path) {
		super(path);
	}

	
	public void addRepository(String repo) {
		write("REPOSITORY " + repo);
	}
	
	/**
	 * Deletes the repository from the configuration file.
	 * 
	 * @param repPath
	 * path of the repository to remove
	 * 
	 * @author Seth
	 */
	public void removeRepository(String repPath) {
		
		String content = getContents();
		
		content = content.replaceFirst("REPOSITORY " + repPath + "\n", "");
		write(content);
	}


	@Override
	protected void validate(String path) {

		File file = new File(this.path);
		
		if(!file.exists()) {
			
			try {
				file.createNewFile();
				
				write("# This is the main configuration file for Source Connect repository control");
				write("");
				write("# Keywords");
				write("");
				write("# REPOSITORY - a file location pointer to a local repository on this machine");
				write("# IGNORE - a file extension that will be ignored while commiting in any repository. ex. *.txt");
				write("");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Returns the index of the first occurrence of
	 * a repository that has the same path as the
	 * path given. If none is found, it will return
	 * -1.
	 * 
	 * @param path
	 * path of the target repository
	 * @return
	 * Index of the repository. -1 if it doesn't exist.
	 */
	public int getRepositoryIndex(String path) {
		
		String[] repositories = readKeyValue("REPOSITORY");
		
		for(int r = 0; r < repositories.length; r++) {
			if(repositories[r].equalsIgnoreCase(path)) {
				return r;
			}
		}
		
		return -1;
	}
}

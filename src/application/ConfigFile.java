package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ConfigFile {
	
	// MASTER is the main configuration file used
	// to store repository locations
	// REP is the configuration for each
	// repository, located within the repository
	// itself
	public enum Type {MASTER, REP};
	
	private String path = "";
	private Type type = Type.MASTER; // Default value to prevent null
	
	private final String infoTextMaster = 
			"# This is the main configuration file for " +
			"Source Connect repository control\n" +
			"# Do not change any values presented in this file, " +
			"unless you are sure of your intentions.\n\n" +
			"# Keywords\n" +
			"# REPOSITORY - a file location pointer to a local " +
			"repository on this machine\n" +
			"# IGNORE - a file extension that will be ignored " +
			"while commiting in any repository. ex. *.txt\n\n";
	
	private final String infoTextRep = 
			"# This is the local configuration file that " +
			"is used to keep track of all of the files within " +
			"this repository. In addition, it will attempt to " +
			"keep a record of the changes on a file.\n" +
			"# Do not change any values presented in this file, " +
			"unless you are sure of your intentions.\n\n" +
			"# Keywords\n" +
			"# FILE - a file that is a part of this repository\n" +
			"# HISTORY - the revision history of a given file\n" +
			"# IGNORE - will ignore the filename that follows\n\n";
	
	/**
	 * Creates a new configuration file at the
	 * specified location. If one already exists,
	 * then it will not attempt to create a new
	 * one. This class is made to be a uniform, standard
	 * way to write to configuration files.
	 * 
	 * @param path
	 * location of this configuration file INCLUDING the name
	 * @param configType
	 * config type:<br>
	 * MASTER - main config<br>
	 * REP - local config for repository
	 * 
	 * @author Seth
	 */
	public ConfigFile(String path, Type configType) {
		
		this.path = path;
		this.type = configType;
		
		initFile(path);
	}
	
	/**
	 * Creates a new configuration file at the
	 * specified location. If one already exists,
	 * then it will not attempt to create a new
	 * one. This class is made to be a uniform, standard
	 * way to write to configuration files. Default
	 * type is MASTER.
	 * 
	 * @param path
	 * location of this configuration file INCLUDING the name
	 * 
	 * @author Seth
	 */
	public ConfigFile(String path) {
		
		this.path = path;
		
		initFile(path);
	}
	
	/**
	 * Will attempt to create a file at
	 * the given location if it doesn't
	 * already exist.
	 * 
	 * @param path
	 * location of the new file
	 * 
	 * @author Seth
	 */
	private void initFile(String path) {
		
		File config = new File(path);
		if(!config.exists()) { // If it doesn't exist:
			
			try {
				config.createNewFile();
				
				// Add "flavor" text
				if(type.equals(Type.MASTER)) {
					writeToConfig(infoTextMaster);
				} else if(type.equals(Type.REP)) {
					writeToConfig(infoTextRep);
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("could not create config file at: " + path);
			}
		} // End of if statement
		
	}
	
	// ------- MASTER methods -------
	
	/**
	 * Adds a repository to the configuration file.
	 * 
	 * @param repPath
	 * path of the new repository
	 * 
	 * @author Seth
	 */
	public void addRepository(String repPath) {
		addToConfig("REPOSITORY " + repPath + "\n");
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
		
		String content = readConfig();
		
		content = content.replaceFirst("REPOSITORY " + repPath + "\n", "");
		writeToConfig(content);
	}
	
	// ------- REP methods -------
	
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
				addToConfig("FILE " + files.get(f).getName() + "\n");
			}
		}
		
		// TODO: Add history / commit recording here
		
	}
	
	
	// ------- Miscellaneous -------
	
	/**
	 * Reads in the content of this config file.
	 * 
	 * @return
	 * A string representing the text in this file.
	 * 
	 * @author Seth
	 */
	private String readConfig() {
		return FileManager.readFileContent(path);
	}
	
	/**
	 * Appends the new string to the end of config
	 * file by reading the current text and then
	 * writing the old content with the new content
	 * tacked on at the end.
	 * 
	 * @param newContent
	 * the new text to add to the file
	 * 
	 * @author Seth
	 */
	private void addToConfig(String newContent) {
		writeToConfig(readConfig() + newContent);
	}
	
	/**
	 * Writes the the given String to this
	 * configuration file. It will delete
	 * any other text within the file, so
	 * it may be wise to prepend {@code content}
	 * with the output of {@link #readConfig()}.
	 * 
	 * @param content
	 * the content of the config file
	 * 
	 * @author Seth
	 */
	private void writeToConfig(String content) {
		
		File config = new File(path);
		
		try {
			PrintWriter writer = new PrintWriter(config);
			writer.print(content);
			writer.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Config was not found at: " + path);
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
	
	/**
	 * This method will read the configuration file
	 * in search of all of the keywords and their
	 * values. Note: For a key that points to a file,
	 * this method will ONLY return an array Strings,
	 * not File objects. This method will automatically
	 * insert a space after the given key.
	 * 
	 * @param key
	 * the key to search for (i.e. REPOSITORY)
	 * @return
	 * An array of values for the given key.
	 */
	public String[] readKeyValue(String key) {
		
		String fileContent = removeComments(readConfig());
		String[] repList = new String[countOccurances(fileContent, key)];
		int lastIndex = 0;
				
		for(int r = 0; r < repList.length; r++) {
			repList[r] = getKeyValue(fileContent, key + " ", lastIndex);
			
			// Move to the next repository
			lastIndex = fileContent.indexOf(key + " ", lastIndex) + (key + " ").length();
		}
		
		return repList;
	}
	
	/**
	 * Gets the value that is adjacent to the key.\n
	 * Ex: {@code REPOSITORY /Users/Desktop/example.txt}\n
	 * This method will return the String
	 * "/Users/Desktop/example.txt" if given the key
	 * {@code REPOSITORY}. To ignore part of the file,
	 * pass a non-zero number into the {@code ignoreIndex}
	 * parameter.
	 * 
	 * @param source
	 * the source string, the hay-stack
	 * @param key
	 * the key of the desired value
	 * @param ignoreIndex
	 * the index to start the search at
	 * @return
	 * The value of the key.
	 */
	private String getKeyValue(String source, String key, int ignoreIndex) {
		
		int beginIndex, endIndex;
		String value = "";
		
		source = removeComments(source);
		
		beginIndex = source.indexOf(key, ignoreIndex) + key.length();
		endIndex = source.indexOf("\n", beginIndex);
		value = source.substring(beginIndex, endIndex);
		
		return value;
	}
	
	private String removeComments(String raw) {
		
		final String commentStart = "#";
		String cleaned = raw;
		int startIndex = 0;
		int endIndex = 0;
		
		for(int c = 0; c < countOccurances(raw, commentStart); c++) {
			startIndex = cleaned.indexOf(commentStart);
			endIndex = cleaned.indexOf("\n", startIndex);
			
			// Remove the comment
			cleaned = cleaned.substring(0, startIndex) + cleaned.substring(endIndex);
		}
		
		return cleaned;
	}
	
	/**
	 * Counts the number of occurrences of the given
	 * substring (needle) within the source (hay-stack).
	 * 
	 * @param haystack
	 * source to look in
	 * @param needle
	 * substring to count
	 * @return
	 * Number of occurances of the needle in the hay-stack.
	 * 
	 * @author Seth
	 */
	private int countOccurances(String haystack, String needle) {
		
		int count = 0;
		int lastIndex = 0;
		
		while(lastIndex != -1) {
			
			lastIndex = haystack.indexOf(needle, lastIndex);
			
			if(lastIndex != -1) {
				count ++;
				lastIndex = lastIndex + needle.length();
			}
		}
		return count;
	}
	
	// Get / Set methods
	
	public void setPath(String path) {
		this.path = path;
	}
	public String getPath() {
		return path;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	public Type getType() {
		return type;
	}
	
}

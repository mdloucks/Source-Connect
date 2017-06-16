package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import application.managers.FileManager;

public abstract class ConfigurationFile {

	protected String path = "";
	
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
	public ConfigurationFile(String path) {
		
		this.path = path;
		
		validate(path);
	}
	
	
	/**
	 * Writes the the given String to this
	 * configuration file. It will delete
	 * any other text within the file, so
	 * it may be wise to prepend {@code content}
	 * with the output of {@link #getContents()}.
	 * 
	 * @param content
	 * the content of the config file
	 */
	protected void write(String content) {
				
		BufferedWriter bw = null;
		
		try {
			bw = new BufferedWriter(new FileWriter(this.path, true));
			bw.newLine();
			bw.write(content);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("config could not be found at " + this.path);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
	protected abstract void validate(String path);
	
	private String removeComments(String raw) {
		
		final String commentStart = "#";
		String cleaned = raw;
		int startIndex = 0;
		int endIndex = 0;
															// lol
		for(int c = 0; c < countOccurances(raw, commentStart); c++) {
			startIndex = cleaned.indexOf(commentStart);
			endIndex = cleaned.indexOf("\n", startIndex);
			
			// Remove the comment
			cleaned = cleaned.substring(0, startIndex) + cleaned.substring(endIndex);
		}
		
		return cleaned;
	}
		
	/**
	 * Reads in the content of this config file.
	 * 
	 * @return
	 * A string representing the text in this file.
	 * 
	 * @author Seth
	 */
	protected String getContents() {
		return FileManager.getFileContent(path);
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
		
		String fileContent = removeComments(getContents());
		String[] repList = new String[countOccurances(fileContent, key)];
		int lastIndex = 0;
				
		for(int r = 0; r < repList.length; r++) {
			repList[r] = getKeyValue(key + " ", lastIndex);
			
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
	private String getKeyValue(String key, int ignoreIndex) {
		
		int beginIndex, endIndex;
		String value = "";
		
		String source = removeComments(getContents());
		
		beginIndex = source.indexOf(key, ignoreIndex) + key.length();
		endIndex = source.indexOf("\n", beginIndex);
		value = source.substring(beginIndex, endIndex);
		
		return value;
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
}

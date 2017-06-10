package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * 
 * A general file handler that manages files stored on this computer
 * not meant to hold any important code, just a lot of general purpose methods
 * 
 * @author louck
 *
 */
public class FileManager {
	
	public FileManager() {
		
	}



	
	/**
	 * loads any relevant data from the main configuration folder into memory
	 * 
	 */
	public static void loadConfigurations(ArrayList<File> repositories, ArrayList<String> ignores) {
		
		System.out.println("checking configurations...");
		
		String[] fileArray;
		File currentFile;
		ConfigFile config = new ConfigFile("sc.conf");
		
		// Files
		fileArray = config.readKeyValue("REPOSITORY");
		
		for(int f = 0; f < fileArray.length; f++) {
			currentFile = new File(fileArray[f]);
			repositories.add(currentFile);
		}
		
		// Ignores
		fileArray = config.readKeyValue("IGNORE");
		for(int i = 0; i < fileArray.length; i++) {
			ignores.add(fileArray[i]);
		}
		
		System.out.println("repositories: " + repositories);
		System.out.println("ignores: " + ignores);
		
	}

	/**
	 * recursively add all files and sub-directories in a given folder to an array-list and return it
	 * 
	 * @param the repository of which the files you would like
	 * @return all of the files in a given directory
	 */
	public static ArrayList<File> getFiles(File directory) {

	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    ArrayList<File> files = new ArrayList<File>();
	    
	    for (File file : fList) {
	    	
	        if (file.isFile()) {
	        	
	            files.add(file);
	        } else if (file.isDirectory()) {
	        	
	            files.addAll(getFiles(directory));
	        }
	    }
	    return files;
	}
	
	/**
	 * searches for a given file in this project
	 * 
	 * @param file
	 * @return
	 */
	public static File getFile(File file) {
		
		System.out.println("searching for " + file.getName() + "...");
		
		ArrayList<File> files = getFiles(new File(System.getProperty("user.dir")));

		// loop through files returned by getFiles()
		for(File f : files) {
				
			try {
				if(f.getCanonicalPath().equals(file.getCanonicalPath())) {
					System.out.println(f.getAbsolutePath());
					return f;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("could not find " + file.getName());
		return null;
	}
	
	/**
	 * returns the file extension from the given file
	 * 
	 * @param f
	 * @return
	 */
	public static String getExtension(File f) {
				
		int i = f.getAbsolutePath().lastIndexOf('.');
		return i > 0 ? f.getAbsolutePath().substring(i+1) : "File Folder";
	}
	
	/**
	 * get a string of the size of a given file in either GB MB or KB
	 * 
	 * @return
	 */
	public static String getSize(File f) {
		
		// get size in GB
		long size = f.length() / 1000000000;
		String strSize = " GB";
		
		if(size < 1) {
			size = f.length() / 1000;
			strSize = " MB";
		}
		if(size < 1) {
			size = f.length() / 10;
			strSize = " KB";
		}
		
		return Long.toString(size).concat(strSize);
	}
	
	/**
	 * This method will delete all of the files within the
	 * specified directory. The files within the directory must
	 * be deleted first because Java cannot remove directories
	 * that contain data.
	 * 
	 * @param path
	 * path to the directory to delete
	 * 
	 * @see {@link File#delete()}
	 * 
	 * @author Seth
	 */
	public static void deleteDirectory(String path) {
		
		File targetFile = new File(path);
		
		String[] files = targetFile.list();
		File nestedFile;
		for(String s : files) {
			nestedFile = new File(targetFile.getPath(), s);
			nestedFile.delete();
		}
		targetFile.delete();
		
	}
	
	/**
	 * goes to a given directory and makes a sc.conf file
	 * 
	 * @param path of creation
	 */
	public static void createConfiguration(File conf) {
		
		try {
	
			if(conf.exists()) {
				return;
				
			} else {
				
				PrintWriter pw;
				
				System.out.println("creating main configuration file...");
				System.out.println("sc.conf will be created at " + conf.getAbsolutePath());
				
				pw = new PrintWriter(conf);
				
				pw.println("# This is the main configuration file for Source Connect repository control");
				pw.println("# Do not change any values presented in this file, unless you are sure of your intentions.");
				pw.println();
				pw.println("# Keywords");
				pw.println();
				pw.println("# REPOSITORY - a file location pointer to a local repository on this machine");
				pw.println("# IGNORE - a file extension that will be ignored while commiting in any repository. ex. *.txt");
				pw.println();
				
				pw.close();
			}
			
			System.out.println("a new main configuration file has been created");
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			
		}
	}
	
	/**
	 * Tests to see if a file at the given path exists.
	 * 
	 * @param path
	 * path to the file in question
	 * @return
	 * True, if the file exists. Otherwise, false.
	 * 
	 * @author Seth
	 */
	public static boolean doesFileExist(String path) {
		File pathFile = new File(path);
		return pathFile.exists();
	}
	
	/**
	 * Reads the text from the given file path.
	 * 
	 * @param path
	 * path to the text file
	 * @return
	 * String containing the information in the given file.
	 * 
	 * @author Seth
	 */
	public static String readFileContent(String path) {
		
		String content = "";
		
		try {
			for(String line : Files.readAllLines(Paths.get(path))) {
				content = content + line + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("file couldn't be found at: " + path);
		}
		
		return content;
	}
	
}

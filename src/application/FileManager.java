package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javafx.stage.DirectoryChooser;

/**
 * TODO add a "dump" file that stores an error log
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

	// initialized repositories
	private static ArrayList<File> repositories = new ArrayList<File>();
	
	private static ArrayList<String> ignores = new ArrayList<String>();
	
	/**
	 * open a file explorer and have the user choose a repository location
	 * this creates a sc.conf file in this application that points to that file path
	 * and it also creates another sc.conf file in their selected directory
	 */
	public static void initRepository() {
		
		System.out.println("initializing repository...");
		
		// choose a folder
		DirectoryChooser dc = new DirectoryChooser();
		dc.setTitle("new repository");
		
		File repository = dc.showDialog(null);
		
		if(repository != null && repository.isDirectory()) {
			
			// create a configuration file at the given directory
			try {
				
				PrintWriter pw;
				
				// configuration file in another folder
				File remoteConf = new File(repository.getPath() + "/sc.conf");

				// local configuration file
				File mainConf = new File("sc.conf");
				
				// store the new file path in sc.conf
				if(mainConf.exists()) {
					
					pw = new PrintWriter(mainConf);
					
					System.out.println("logging new repository in main sc.conf");
					
					pw.println("REPOSITORY" + repository.getAbsoluteFile());
					
					pw.close();
					
				} else {

					createConfig(mainConf);
				}

				// initialize remoteConf
				pw = new PrintWriter(remoteConf);
				pw.println("# This is the main configuration file for your Source Connect repository");
				pw.println("# Do not change any values presented in this file, unless you are sure of your intentions.");
				// set hidden
				Process p = Runtime.getRuntime().exec("attrib +h " + remoteConf.getPath() + "/sc.conf");
			    p.waitFor();
			    System.out.println(remoteConf.getPath() + "/sc.conf");
			    
				
				pw.close();
				
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				
			} catch(IOException e2) {
				e2.printStackTrace();
				
			} catch (InterruptedException e3) {
				e3.printStackTrace();
			}
			
		} else {
			System.err.println("Directory " + repository.getAbsolutePath() + " is invalid.");
		}	
	}
	
	/**
	 * loads any relevant data from the conf folder into memory
	 * 
	 * 
	 */
	public static void loadConfigurations() {
		
		try {
			
			System.out.println("checking repositories...");
			
			File mainConf = new File("D:\\Programs\\eclipse\\Source Connect\\src\\application\\conf\\sc.conf");
			
			if(!mainConf.exists()) {
				createConfig(mainConf);
			}
			
			Scanner s = new Scanner(mainConf);
			
			String line;
			// read each line of the file
			while(s.hasNextLine() && (line = s.nextLine()) != null) {

				line.trim();
				
				// skip commented lines
				if(line.startsWith("#") || line.startsWith("//")) {
					continue;
				}
				
				if(line.startsWith("REPOSITORY")) {
					
					File repo = new File(line.substring(11));
					repositories.add(repo);
					
					System.out.println("repository " + repo.getAbsolutePath() + " has been loaded into memory");
					
				} else if(line.startsWith("IGNORE")) {

					ignores.add(line.substring(7));
					
					System.out.println("ignore " + line.substring(7) + " has been loaded into memory");
				}
			}
			
			s.close();
			
			System.out.println("repositories: " + repositories);
			System.out.println("ignores: " + ignores);
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			
			ExceptionHandler.popup("sc.conf has been removed or missplaced from your package, this file is required to run.", e, true);
			
		} catch(NoSuchElementException e1) {
			
			ExceptionHandler.popup("sc.conf is empty!", e1, true);
		}

	}
	
	/**
	 * recursively add all files and sub-directories in a given folder to an array-list and return it
	 * 
	 * @param the repository of which the files you would like
	 * @return all of the files in a given directory
	 */
	public static ArrayList<File> getFiles(String directoryName) {
		
	    File directory = new File(directoryName);

	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    ArrayList<File> files = new ArrayList<File>();
	    
	    for (File file : fList) {
	    	
	        if (file.isFile()) {
	        	
	            files.add(file);
	        } else if (file.isDirectory()) {
	        	
	            files.addAll(getFiles(file.getAbsolutePath()));
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
		
		ArrayList<File> files = getFiles(System.getProperty("user.dir"));

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

	public static ArrayList<File> getRepositories() {
		return repositories;
	}
	
	/**
	 * TODO decide if we should hide the conf file?
	 * WIP
	 * @param f
	 */
	private void hideFile(File f) {
		
		System.out.println("detecting operating system...");
		
		String os = System.getProperty("os.name");
		System.out.println("OS: " + os);
		os.toLowerCase();

		
		
		try {

			
			
			if(os.contains("windows")) {
				
				System.out.println("your machine is running " + os);
				
				Path path = FileSystems.getDefault().getPath(f.getAbsolutePath());
				Files.setAttribute(path, "dos:hidden", true);
				
			} else if(os.contains("mac")) {
				
				System.out.println("your machine is running " + os);
				
			} else if(os.contains("linux") || os.contains("unix")) {
				
				System.out.println("your machine is running " + os);
				
			} else {
				System.out.println("your operating system - " + os + " - has no clear way of instantiating hidden files in java");
			}
			
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	/**
	 * goes to a given directory and makes a sc.conf file
	 * 
	 * @param path of creation
	 */
	private static void createConfig(File conf) {
		
		try {

			PrintWriter pw;
			
			if(conf.exists()) {
				return;
				
			} else {
				
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

			pw.close();
			
			System.out.println("a new configuration file has been created");
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			
		}
	}
}

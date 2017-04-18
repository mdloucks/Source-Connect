package application;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * TODO add a "dump" file that stores an error log
 * 
 * 
 * @author louck
 *
 */
public class FileManager {

	// initialized repositories
	private static ArrayList<File> repositories = new ArrayList<File>();
	// staged files
	private static ArrayList<File> stage = new ArrayList<File>();
	
	public static void initRepository() {
		
		// choose a folder
		FileChooser fs = new FileChooser();
		fs.getExtensionFilters().addAll(new ExtensionFilter("Folder", "*.FOLDER"));
		
		File repository = fs.showOpenDialog(null);

		if(repository != null && repository.isDirectory()) {
			
			// log the file into conf.txt as a repository
			try {
				DataOutputStream dos =  new DataOutputStream(new FileOutputStream("conf.txt"));
				dos.writeChars(repository.getAbsolutePath());
				
				dos.close();
				
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else {
			System.err.println("Directory " + repository.getAbsolutePath() + " is not valid.");
		}	
	}
	
	/**
	 * @param the repository of which the files you would like
	 * @return an array of file paths
	 */
	public static File[] getFiles(File repo) {
		
		File[] repository_files = repo.listFiles();

	    for (int i = 0; i < repository_files.length; i++) {
	    	
	    	if (repository_files[i].isFile()) {
	    		System.out.println("retrieving file: " + repository_files[i].getName());
	    	} else if (repository_files[i].isDirectory()) {
	    		System.out.println("retrieving directory: " + repository_files[i].getName());
	    	}
	    }
	    
	    return repository_files;
	}
	
	/**
	 * Takes an array of files to "stage" which prepares them to be committed
	 * basically sorts through the selected files with the git ignore 
	 * and packages them into a final state
	 * 
	 * @param File array
	 */
	public static void stage(File files[], Commit commit) {
		
		// checks for a git ignore
		File gitignore = new File(".gitignore");
	
		if(gitignore.exists() && gitignore.isHidden()) {
			
			try {
				
				// read the contents
				BufferedReader reader = new BufferedReader(
						new FileReader(gitignore.getAbsolutePath()));
				
				ArrayList<String> ignores = new ArrayList<String>();
				// loop through each thing we are going to ignore
				
				String ignore;
				while((ignore = reader.readLine()) != null) {
					
					// if the line isn't a comment
					if(!ignore.contains("#")) {
						ignores.add(ignore);
					}
				}
				
				int count = 0;
				
				for(File file : files) {
					/* checks file path and name
					 *  example: there is a repository in Desktop
					 *  (if) "guy.txt" == "guy.txt"
					 *  (if) file C:/Desktop/chips/guy.txt (contains) /chips/ (or) /chips/guy.txt  
					*/ 
					if(!file.getName().contains(ignores.get(count)) 
					|| file.getAbsolutePath().toString().contains(ignores.get(count))) {
						// add the file to the stage
						stage.add(file);
						
					} else {
						continue;
						// skip that file
					}
					
					count++;
					
					reader.close();
				}
				
			} catch(IOException e) {
				e.printStackTrace();
				System.err.println("there was an error while accessing the git ignore file");
			} catch(Exception e1) {
				e1.printStackTrace();
				System.err.println("there was an error that is unassociated with the git ignore file");
			}

			
		} else {
			System.out.println("could not find gitignore");
			System.out.println("continuing to stage...");
		}
		
		for(File file : files) {
			// add that file to the stage
			stage.add(file);
		}
	
	
	}
	
	public void sendFile(String file, Socket s) throws IOException {
		
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		FileInputStream fis = new FileInputStream(file);
		
		File f = new File(file);
		// sends name of file
		dos.writeUTF(file);
		// sends size of file
		dos.writeInt((int) f.length());
		
		// fills up the buffer on each write, then sends that buffer to client
		byte[] buffer = new byte[8192];
		while (fis.read(buffer) > 0) {
			dos.write(buffer);
		}
		
		fis.close();
		dos.close();	
	}
	
	public void saveFile(Socket clientSock) throws IOException {
		
		DataInputStream dis = new DataInputStream(clientSock.getInputStream());
		FileOutputStream fos = new FileOutputStream(dis.readUTF());
		
		byte[] buffer = new byte[8192];
		
		// receives size of file
		int filesize = dis.readInt();
		int read = 0;
		int totalRead = 0;
		int remaining = filesize;
		while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
			totalRead += read;
			remaining -= read;
			System.out.println("read " + totalRead + " bytes.");
			fos.write(buffer, 0, read);
		}
		
		fos.close();
		dis.close();
	}
	
	private static class Commit {
		
		private ArrayList<File> elements = new ArrayList<File>();
		private String msg;
		
		@SuppressWarnings("unused")
		public ArrayList<File> getElements() {
			return elements;
		}
		@SuppressWarnings("unused")
		public void setElements(ArrayList<File> elements) {
			this.elements = elements;
		}
		@SuppressWarnings("unused")
		public String getMsg() {
			return msg;
		}
		@SuppressWarnings("unused")
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
}

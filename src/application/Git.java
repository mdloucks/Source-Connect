package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import application.App.OperatingSystem;

/**
 * the actual end class which takes the data from this machine and
 * transfers/sorts it with git and github
 * 
 * 
 * @author louck
 *
 */
public class Git {

	private static ArrayList<File> repositories = new ArrayList<File>();

	private static ArrayList<String> ignores = new ArrayList<String>();

	/**
	 * executes the given command in the users command shell
	 * 
	 * @param command
	 */
	public void execute(String command) {

		System.out.println("executing command \"" + command + "\"");

		switch (App.OS) {

		case Windows:

			try {
				// /C Carries out the command specified by string and then
				// terminates
				ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
				builder.redirectErrorStream(true);
				Process p = builder.start();
				BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while (true) {
					line = r.readLine();
					if (line == null) {
						break;
					}
					System.out.println(line);
				}

			} catch (IOException e) {

			}

			break;

		case Other:
			System.out.println("cannot execute the given command because this machine's operating system is unspecified");
			break;

		default:
			try {
				Runtime.getRuntime().exec(command);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;
		}

	}
	
	/**
	 * 
	 */
	public static void removeRepository() {
		
		
		
	}

	public static ArrayList<File> getRepositories() {
		return repositories;
	}

	public static ArrayList<String> getIgnores() {
		return ignores;
	}

}

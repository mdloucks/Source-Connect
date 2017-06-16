package application;
	
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;

import application.managers.ConnectionManager;
import application.managers.ExceptionManager;
import application.managers.FileManager;
import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class App extends Application implements Runnable {

	private static ConnectionManager cm;
	private static ServerSocket ss;
	public static OperatingSystem OS;
	public static int port = 9342;
	private MainController mainController;
	
	@Override
	public void start(Stage window) {
		
		try {
			
			System.out.println("detecting os...");
			detectOS();
			
			Git g = new Git();
			g.execute("ls");
						
			if(OS.equals(OperatingSystem.Windows)) {
				System.out.println("checking for updates...");
				ConnectionManager.checkUpdates();
			} else {
				System.out.println("not checking for updates due to os");
			}
			
			FileManager.loadConfigurations(Git.getRepositories(), Git.getIgnores());
			
			System.out.println("loading scenes...");
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/main.fxml"));
			
			Parent parent_main = loader.load();
			
			mainController = loader.getController();
			
			Scene scene_main = new Scene(parent_main);
			
			System.out.println("loading css content...");
			scene_main.getStylesheets().add(getClass().getResource("/resources/css/application.css").toExternalForm());
			
			System.out.println("loading images...");
			Image window_icon = new Image("/resources/img/icon.png");

			System.out.println("setting window properties...");
			window.setTitle("Source Connect");
			window.getIcons().add(window_icon);
			window.setResizable(false);
			
			
			System.out.println("starting ServerSocket thread...");
			ss = new ServerSocket();
			Thread t1 = new Thread(new App());
			t1.start();
			
			
			window.setScene(scene_main);
			window.show();
			
		} catch(FileNotFoundException e1) {
			
			e1.printStackTrace();
			ExceptionManager.popup("could not find required resource files", e1, true);
			
		} catch(javafx.fxml.LoadException e2) {	
			
			e2.printStackTrace();
			ExceptionManager.popup("could not find required resource files", e2, true);
		
		} catch(NullPointerException e3) {
			
			e3.printStackTrace();
			ExceptionManager.popup("could not find required resource files", e3, true);
			
		} catch(Exception e4) {
			
			e4.printStackTrace();
			ExceptionManager.popup("could not find required resource files", e4, true);
			
		}
		
	}

	public static void main(String[] args) {
		System.out.println("launching...");
		launch(args);
	}

	/**
	 * creates a new server socket, then waits for a 'client' to connect indefinitely
	 */
	public void run() {
		
		while (true) {
			
			try {
				ss = new ServerSocket(port);
				Socket client = ss.accept();
				cm.saveFile(client);
				ss.close();
				System.out.println("successfully closed connection with " + client.getInetAddress().toString());
				
			} catch (BindException e) {
				ExceptionManager.popup("java could not sucessfully bind to port ", e, false);;
				stop();
			} catch(IOException e) {
				ExceptionManager.popup("there was an isssue establishing a IO stream with your machine", e, false);;
				stop();
			}
		}
	}
	
	public enum OperatingSystem {
	    Windows, MacOS, Linux, Other
	};
	
	@Override
	public void stop(){
	    ExceptionManager.exit();
	}
	
	/**
	 * finds the current os the user is running and sets the main.OS to that type
	 */
	private void detectOS() {
		
		String os = System.getProperty("os.name").toLowerCase();
				
		if(os.contains("windows")) {
			App.OS = OperatingSystem.Windows;
			System.out.println("your machine is running " + os);
		} else if(os.contains("nux")) {
			App.OS = OperatingSystem.Linux;
			System.out.println("your machine is running " + os);
		} else if(os.contains("mac")) {
			App.OS = OperatingSystem.MacOS;
			System.out.println("your machine is running " + os);
		} else {
			App.OS = OperatingSystem.Other;
			System.out.println("java could not properly determine your OS");
		}
	}
}

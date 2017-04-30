package application;
	
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application implements Runnable {

	private static ConnectionManager cm;
	private static ServerSocket ss;
	
	@Override
	public void start(Stage window) {
		
		try {
			
			System.out.println("checking for updates...");
			ConnectionManager.checkUpdates();
			
			System.out.println("loading scenes...");
			// loading anything from FXMLLoader calls the initialize method in MainController
			Parent parent_main = FXMLLoader.load(getClass().getResource("/resources/fxml/main.fxml"));
			Parent parent_login = FXMLLoader.load(getClass().getResource("/resources/fxml/login.fxml"));
			
			Scene scene_main = new Scene(parent_main);
			Scene scene_login = new Scene(parent_login);
			
			System.out.println("loading css content...");
			scene_main.getStylesheets().add(getClass().getResource("/resources/css/application.css").toExternalForm());
			
			System.out.println("loading images...");
			Image window_icon = new Image("/resources/img/icon.png");

			System.out.println("setting window properties...");
			window.setTitle("Source Connect");
			window.getIcons().add(window_icon);
			
			
			System.out.println("starting ServerSocket thread...");
			ss = new ServerSocket();
			Thread t1 = new Thread(new Main());
			t1.start();
			
			MainController.loadRepositoryLabels();
			
			window.setScene(scene_main);
			window.show();
			
		} catch(FileNotFoundException e1) {
			
			e1.printStackTrace();
			ExceptionHandler.popup("could not find required resource files", e1, true);
			
		} catch(javafx.fxml.LoadException e2) {	
			
			e2.printStackTrace();
			ExceptionHandler.popup("could not find required resource files", e2, true);
		
		} catch(NullPointerException e3) {
			
			e3.printStackTrace();
			ExceptionHandler.popup("could not find required resource files", e3, true);
			
		} catch(Exception e4) {
			
			e4.printStackTrace();
			ExceptionHandler.popup("could not find required resource files", e4, true);
			
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
				ss = new ServerSocket(9435);
				Socket client = ss.accept();
				cm.saveFile(client);
				ss.close();
				System.out.println("successfully closed connection with " + client.getInetAddress().toString());
				
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}
	
	@Override
	public void stop(){
	    ExceptionHandler.exit();
	}
}

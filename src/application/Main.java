package application;
	
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application implements Runnable {

	private static FileManager fm;
	private static ServerSocket ss;
	
	
	@Override
	public void start(Stage window) {
		
		try {
			System.out.println("loading scenes...");
			
			Parent parent_main = FXMLLoader.load(getClass().getResource("/resources/main.fxml"));
			Parent parent_login = FXMLLoader.load(getClass().getResource("/resources/login.fxml"));
			
			fm = new FileManager();
			ss = new ServerSocket();
	
			System.out.println("starting ServerSocket thread...");
			Thread t1 = new Thread(new Main());
			t1.start();
			
			//Socket s = new Socket("97.84.170.69", 5555);
			//fm.sendFile("D:/Pictures/yolo.jpg", s);
			

			Scene scene_main = new Scene(parent_main);
			Scene scene_login = new Scene(parent_login);
			
			
			System.out.println("loading css content...");
			scene_main.getStylesheets().add(getClass().getResource("/resources/application.css").toExternalForm());
			
			
			window.setScene(scene_main);
			window.show();
			
		} catch(FileNotFoundException e1) {
			
			ExceptionHandler eh = new ExceptionHandler(e1);
			eh.popup("could not find required resource files", window);
			
		} catch(javafx.fxml.LoadException e2) {	
			
			ExceptionHandler eh = new ExceptionHandler(e2);
			eh.popup("could not find required resource files", window);

		
		} catch(NullPointerException e3) {
			
			ExceptionHandler eh = new ExceptionHandler(e3);
			eh.popup("could not find required resource files", window);
			
			
		} catch(Exception e4) {
			
			ExceptionHandler eh = new ExceptionHandler(e4);
			eh.popup("could not find required resource files", window);
			
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
				Socket clientSock = ss.accept();
				fm.saveFile(clientSock);
				ss.close();
				
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}
}

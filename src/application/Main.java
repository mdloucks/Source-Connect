package application;
	
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

			Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
			
			fm = new FileManager();
			ss = new ServerSocket();
			
			// clients network info
			//fm.FileClient(host, port, file);
			
			Thread t1 = new Thread(new Main());
			t1.start();
			
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(scene);
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * checks for new incoming files
	 */
	public void run() {
		while (true) {
			try {
				ss = new ServerSocket(5555);
				Socket clientSock = ss.accept();
				fm.saveFile(clientSock);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

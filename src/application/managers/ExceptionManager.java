package application.managers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * gives the end client easier ways to diagnose problems with their application
 * 
 * error handling stages are generated programmatically, because the user is unable to do a certain action </br>
 * and we may be unable to load the fxml files for the stage reliably. </br>
 * 
 * 
 * 
 * TODO near deployment, overhaul this class and add some better exception handling
 * 
 * @author loucks
 *
 */
public class ExceptionManager {
	
	/**
	 * TODO make a error dump file
	 * @param e
	 */
	public ExceptionManager() {
	}
	
	/**
	 * TODO save resources before close </br>
	 *
	 * <b> creates a quick pop up window that displays a message  </b> </br> </br>
	 * 
	 * fatal exceptions are those that severely limit the program and the user's ability to function
	 * Set it as true to have the program exit on button click
	 * 
	 * @param String msg
	 * @param Exception e
	 * @param boolean fatal
	 */
	public static void popup(String msg, Exception exception, boolean fatal) {
		
		if(exception != null) {
			exception.printStackTrace();
		}
		
		final Stage stage = new Stage();
		stage.setTitle("An error has occured");
		stage.centerOnScreen();
		stage.setAlwaysOnTop(true);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(new Stage());

        Button button = new Button();
        button.setText("Exit");
        
        Label label = new Label();
        label.setText(msg);
        label.setWrapText(true);
        
        VBox container = new VBox(12);
        
        container.setAlignment(Pos.CENTER);
        
        container.getChildren().addAll(label,button);
        
        label.setAlignment(Pos.CENTER);  
        
        Scene dialogScene = new Scene(container, 300, 200);
        stage.setScene(dialogScene);
        
        if(fatal) {
        	
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    exit();
                }
            });
            
    		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
  		      public void handle(WindowEvent we) {
  		          exit();
  		      }
  		  	}); 
        	
        } else {
        	// non fatal
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    stage.close();
                }
            });
            
    		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
  		      public void handle(WindowEvent we) {
  		          stage.close();
  		      }
  		  	}); 
        	
        }
        
        stage.show();
	}
	
	/**
	 * close platform and exit application, leaving given message
	 */
	public static void exit() {
		System.out.println("stage is closing...");
		Platform.exit();
		System.out.println("program terminated");
		System.exit(0);
	}

}

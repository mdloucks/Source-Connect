package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * gives the end client ways to diagnose problems with their application
 * 
 * 
 * @author loucks
 *
 */
public class ExceptionHandler {
	
	/**
	 * TODO make a error dump file
	 * @param e
	 */
	public ExceptionHandler(Exception e) {
		e.printStackTrace();
	}
	
	/**
	 * TODO save resources before close
	 * 
	 * creates a quick pop up window that displays a message
	 * 
	 * this is generated programmatically, because the user is unable to load any files
	 * because of some weird file path issues, so it's unlikely we will be able to load error fxml files
	 * 
	 * 
	 * @param String msg
	 */
	public void popup(String msg, Stage own) {
		
		final Stage stage = new Stage();
		stage.setTitle("An error has occured");
		stage.centerOnScreen();
		stage.setAlwaysOnTop(true);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(own);
		
        VBox dialogVbox = new VBox(20);
        
        Button button = new Button();
        button.setText("Exit");
        
        Label label = new Label();
        label.setText(msg);
        
        dialogVbox.getChildren().addAll(label,button);
        
        dialogVbox.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        stage.setScene(dialogScene);
        
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.exit(-1);
            }
        });
        
        stage.show();
	}

}

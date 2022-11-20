import java.io.File;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class Main extends Application {
	String path = "/test.mp3";
	Media m = new Media(Paths.get("test.mp3").toUri().toString());
	MediaPlayer spotify = new MediaPlayer(m);
	@Override
	public void start(Stage primaryStage) {
		try {
			Button btn = new Button();
	        btn.setText("Play");
	        btn.setOnAction(new EventHandler<ActionEvent>() {
	 
	            @Override
	            public void handle(ActionEvent event) {
	            	System.out.println("button pressed");

	            	
	            	//String bip = "Ghost - Cirice.mp3";
	            	String getText = btn.getText();
	            	if(getText.equalsIgnoreCase("play")) {
	            		spotify.play();
	            		btn.setText("Pause");
	            	}else {
	            		spotify.pause();
	            		btn.setText("Play");
	            	}
	            }
	        });
	        
	        StackPane root = new StackPane();
	        root.getChildren().add(btn);

	        Scene scene = new Scene(root, 300, 250);

	        primaryStage.setTitle("Play");
	        primaryStage.setScene(scene);
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

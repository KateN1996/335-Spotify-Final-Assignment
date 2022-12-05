
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.imageio.ImageIO;
//is this working?
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
public class MusicPlayer extends Application {
	public static BrazilBeatsUI gui;
	@Override
	public void start(Stage primaryStage) {
		try {
			//Playlist defaultLib = defaultLibrary();
			gui = new BrazilBeatsUI();
	    	gui.initializeComponents();
					//(defaultLib.getSong("It's Called Freefall").getPlayer(), defaultLib);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private Playlist defaultLibrary() {
		Playlist defaultLib = new Playlist("Default Library", "");
		defaultLib.addSong(new Song("Cirice", "Ghost", "Ghost - Cirice.mp3", "blank.png"));
		defaultLib.addSong(new Song("It's Called Freefall", "Rainbow Kitten Surprise", "Rainbow Kitten Surprise - It's Called Freefall.mp3", "blank.png"));
		defaultLib.addSong(new Song("Jolene", "The White Stripes", "The White Stripes - Jolene.mp3", "blank.png"));
		return defaultLib;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

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
public class temp extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Playlist defaultLib = defaultLibrary();
			
			BrazilBeats ui = new BrazilBeats(defaultLib.getSong("It's Called Freefall").getPlayer(), defaultLib);
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

class BrazilBeats {
	private JFrame frame;
	private JPanel panel;

	private JLabel albumImagePreview;
	private JLabel songTitlePreview;
	private JLabel albumTitlePreview;
	private JLabel artistTitlePreview;
	
	private JLabel beatsDanceView;

	private Color appColor = new Color(50, 55, 50);			// Light Brazil Green
	private Color borderColor = new Color(30, 33, 30);		// Dark Brazil Green
	private Color barColor = new Color(40, 44, 40);			// Medium Brazil Green
	private Color accentColor = new Color(30, 80, 30);		// Saturated Brazil Green
	private Color detailColor = new Color(230, 220, 100);	// Light Brazil Yellow

	private Font mainFont = new Font("Arial Bold", Font.PLAIN, 16);
	private Font captionFont = new Font("Arial Bold", Font.PLAIN, 12);
	private Font headerFont = new Font("Arial Bold", Font.BOLD, 28);
	private Dimension defaultRes = new Dimension(1400, 800);
	
	static final int IMG_RES_MAX = 256;
	
	static final int SPACINGX = 5;
	static final int SPACINGY = 10;
	static final Insets INSET_GAP = new Insets(SPACINGX, SPACINGY, SPACINGX, SPACINGY);
	
	private MediaPlayer spotify;
	private Playlist defLib;
	
	BrazilBeats(MediaPlayer spotify, Playlist defLib) {
		this.spotify = spotify;
		this.defLib = defLib;
		// Create Frame
		frame = new JFrame("Brazil Beats Premium");

		// Create main panel
		panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(defaultRes);
		panel.setBackground(appColor);
		panel.setFocusable(true);

		// Library view
		Container libraryView = createLibraryViewContainer();
		// Playback Bar
		Container playbackBar = createPlaybackOptionsContainer();

		// Parent containers to panel
		GridBagConstraints gbc = new GridBagConstraints();

		JPanel navPanel = new JPanel(new GridBagLayout());
		navPanel.setBackground(borderColor);
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.SOUTHWEST;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(navPanel, gbc);

		// Current Song Preview and info
		Container songPreview = createSongPreviewContainer();
		gbc.insets = INSET_GAP;
		gbc.fill = GridBagConstraints.NONE;
		navPanel.add(songPreview, gbc);

		// add the mouseListener so we can add the pane switching
		songPreview.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("SWITCH PANES");
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
		});

		// Navigation Menu
		Container navigationMenu = createNavigationContainer();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;

		navPanel.add(navigationMenu, gbc);

		JPanel beatsPanel = new JPanel(new GridBagLayout());
		beatsPanel.setBackground(borderColor);
		gbc.insets = new Insets(0,0,0,0);
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.SOUTHEAST;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 2;
		gbc.gridy = 1;
		panel.add(beatsPanel, gbc);
		
		// BRAZIL BEATS DANCING CHARACTER CORNER!!!!! LETS GOOOOOOOO!!!!!
		Container brazilBeatsView = createBrazilBeatsContainer();
		gbc.insets = INSET_GAP;
		gbc.anchor = GridBagConstraints.NORTHEAST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = IMG_RES_MAX + (INSET_GAP.left * 2);
		beatsPanel.add(brazilBeatsView, gbc);
		
		// gbc.anchor = GridBagConstraints.NORTH;
		gbc.gridx = 1;
		gbc.gridy = 0;
		// panel.add(libraryView, gbc);

		JPanel playbackPanel = new JPanel();
		playbackPanel.setBackground(barColor);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		playbackPanel.add(playbackBar, gbc);

		gbc.anchor = GridBagConstraints.SOUTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(playbackPanel, gbc);


		// Set and open frame
		frame.setSize(defaultRes);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack(); // Packs all components to fit on screen
		frame.setLocationRelativeTo(null); // Centers frame on screen
		frame.setVisible(true); // Opens frame
		
	}
	

	/**
	 * Creates the graphics container with navigation buttons and playlist tab.
	 * 
	 * @return
	 */
	private Container createNavigationContainer() {
		// Colored panel for whole section

		Container navContainer = new Container();
		GridBagLayout colLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();

		navContainer.setLayout(colLayout);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = INSET_GAP;
		// Create components
		JButton libraryButton = new JButton("Your Library");
		libraryButton.setFont(mainFont);
		libraryButton.setForeground(detailColor);
		libraryButton.setBackground(accentColor);
		gbc.gridx = 0;
		gbc.gridy = 0;
		navContainer.add(libraryButton, gbc);

		JButton searchButton = new JButton("Search");
		searchButton.setFont(mainFont);
		searchButton.setForeground(detailColor);
		searchButton.setBackground(accentColor);
		gbc.gridy = 1;
		navContainer.add(searchButton, gbc);

		JLabel playlistsLabel = new JLabel("Playlists");
		playlistsLabel.setFont(headerFont);
		playlistsLabel.setForeground(detailColor);
		gbc.gridy = 2;
		navContainer.add(playlistsLabel, gbc);

		// TODO: Change this to actual playlist object array
		String[] examplePlaylists = new String[] { "Drake playlist", "Logic isn't that bad", "6ix9ine hidden gems",
				"YEAT's greatest hits", "Wolfgang Amadeus Mozart", "Dababy sleep playlist 2" };
		JList<String> playlistsList = new JList<String>(examplePlaylists); // Store an arrayList of Playlist Objects
																			// here
		playlistsList.setBackground(borderColor);
		playlistsList.setForeground(detailColor);
		playlistsList.setFont(captionFont);
		JScrollPane playlistScroll = new JScrollPane();
		playlistScroll.setViewportView(playlistsList);
		playlistsList.setLayoutOrientation(JList.VERTICAL);
		// JList<Playlist> playlistsList = new JList<Playlist>();
		gbc.gridy = 3;
		navContainer.add(playlistsList, gbc);
		playlistsList.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getButton() == 1) {
					spotify.pause();
					spotify = defLib.getSong("Cirice").getPlayer();
					//update(spotify, defLib);
					System.out.println(" can i switch songs without breaking");
					
				}
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		return navContainer;
	}

	/**
	 * Creates the container with all components used for displaying Current song
	 * info and album art. Will later have a method used for updating graphics to
	 * always match current song playing.
	 * 
	 * @return
	 */
	private Container createSongPreviewContainer() {
		Container songContainer = new Container();
		GridBagLayout colLayout = new GridBagLayout();

		songContainer.setLayout(colLayout);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = INSET_GAP;
		gbc.anchor = GridBagConstraints.WEST;
		// Create components
		// TESTING ALBUM COVER -- HARDCODED ----
		albumImagePreview = new JLabel("");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		songContainer.add(albumImagePreview, gbc);

		songTitlePreview = new JLabel("");
		songTitlePreview.setFont(mainFont);
		songTitlePreview.setForeground(detailColor);
		gbc.gridy = 2;
		songContainer.add(songTitlePreview, gbc);

		albumTitlePreview = new JLabel("");
		albumTitlePreview.setFont(captionFont);
		albumTitlePreview.setForeground(detailColor);
		gbc.gridy = 3;
		songContainer.add(albumTitlePreview, gbc);

		artistTitlePreview = new JLabel("");
		artistTitlePreview.setFont(captionFont);
		artistTitlePreview.setForeground(detailColor);
		gbc.gridy = 4;
		songContainer.add(artistTitlePreview, gbc);

		updateSongPreview();

		return songContainer;
	}

	/**
	 * Creates UI container with the current library view. It should change for if
	 * you are looking at artists, playlists, songs, or albums.
	 * 
	 * @return libContainer
	 */
	private Container createLibraryViewContainer() {
		Container libContainer = new Container();
		BoxLayout colLayout = new BoxLayout(libContainer, BoxLayout.Y_AXIS);
		libContainer.setLayout(colLayout);

		return libContainer;
	}

	/**
	 * Creates the playback options menu on the bottom of the screen with the
	 * playback timeBar and all playback buttons. Each button will later be wired to
	 * methods which complete their respective functions, and the bar will later
	 * update to match the song's timing.
	 * 
	 * @return playbackContainer
	 */
	private Container createPlaybackOptionsContainer() {
		Container playbackContainer = new Container();
		GridBagLayout rowLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		playbackContainer.setLayout(rowLayout);

		gbc.insets = INSET_GAP;
		gbc.anchor = GridBagConstraints.SOUTH;
		
		Double curTime = spotify.getCurrentTime().toSeconds();
		//need to bind timeStamp to current time of song
		JLabel timeStampCur = new JLabel("0:00");
		
		timeStampCur.setFont(captionFont);
		timeStampCur.setForeground(detailColor);
		gbc.gridx = 0;
		gbc.gridy = 0;
		playbackContainer.add(timeStampCur, gbc);

		JProgressBar playbackBar = new JProgressBar(SwingConstants.HORIZONTAL);
		
		playbackBar.setPreferredSize(new Dimension(400, 12));
		playbackBar.setMaximum(172);
		
		// Max set to max length of song
		//playbackBar.setValue(48);
		playbackBar.setBackground(borderColor);
		playbackBar.setForeground(detailColor);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 0;
		playbackContainer.add(playbackBar, gbc);
		gbc.fill = GridBagConstraints.NONE;
		

		JLabel timeStampEnd = new JLabel("0:00");
		timeStampEnd.setFont(captionFont);
		timeStampEnd.setForeground(detailColor);
		
		spotify.setAudioSpectrumListener(new AudioSpectrumListener() {
			@Override
			public void spectrumDataUpdate(double arg0, double arg1, float[] arg2, float[] arg3) {
				Duration current = spotify.getCurrentTime();
				Duration total = spotify.getCycleDuration();
				double amount = current.toSeconds() /total.toSeconds();
				int curTime = (int)(amount * 172);
				int curMin = (int)current.toMinutes();
				int curSec = (int)(current.toSeconds() - curMin *60);
				timeStampCur.setText(String.format("%d:%02d", curMin, curSec));
				Duration end = spotify.getTotalDuration();
				
				int endMin = (int)end.toMinutes();
				int endSec = (int)(end.toSeconds() - endMin *60);
				String endString = String.format("%d:%02d", endMin, endSec);
				timeStampEnd.setText(endString);
				playbackBar.setValue(curTime);
				playbackBar.setVisible(true);
			}
			
		});
		
		playbackBar.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				double percent = (double)e.getX()/400;
				Duration total = spotify.getCycleDuration();
				Duration current = spotify.getCurrentTime();
				double amount = current.toSeconds() /total.toSeconds();
				spotify.seek(spotify.getTotalDuration().multiply(percent));
				
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		playbackContainer.add(timeStampEnd, gbc);

		Container buttonContainer = new Container();
		Dimension buttonSpacing = new Dimension(10, 0);
		buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
		
		JButton shuffleButton = new JButton("$");
		shuffleButton.setBackground(accentColor);
		shuffleButton.setForeground(detailColor);
		shuffleButton.setFont(mainFont);
		buttonContainer.add(shuffleButton);
		buttonContainer.add(Box.createRigidArea(buttonSpacing));

		JButton restartButton = new JButton("|<");
		restartButton.addMouseListener(new MouseListener() {
			//skip to next song
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			//skip a few seconds until released
			@Override
			public void mousePressed(MouseEvent e) {
				spotify.seek(spotify.getCurrentTime().subtract(Duration.seconds(5)));
			}
			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
		});
		restartButton.setBackground(accentColor);
		restartButton.setForeground(detailColor);
		restartButton.setFont(mainFont);
		buttonContainer.add(restartButton);
		buttonContainer.add(Box.createRigidArea(buttonSpacing));

		JButton playPauseButton = new JButton("|>");
		spotify.setOnEndOfMedia(() -> {
		      spotify = defLib.getSong("Cirice").getPlayer();
		      System.out.println("resetting to new song");
		 });
		playPauseButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(playPauseButton.getLabel().equals("|>")) {
					playPauseButton.setText("||");
					spotify.play();
				}else {
					playPauseButton.setText("|>");
					spotify.pause();
				}
				
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
		});

		playPauseButton.setBackground(accentColor);
		playPauseButton.setForeground(detailColor);
		playPauseButton.setFont(mainFont);
		buttonContainer.add(playPauseButton);
		buttonContainer.add(Box.createRigidArea(buttonSpacing));

		JButton skipButton = new JButton(">|");
		skipButton.addMouseListener(new MouseListener() {
			//skip to next song
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			//skip a few seconds until released
			@Override
			public void mousePressed(MouseEvent e) {
				spotify.seek(Duration.seconds(5).add(spotify.getCurrentTime()));
			}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
		});
		skipButton.setBackground(accentColor);
		skipButton.setForeground(detailColor);
		skipButton.setFont(mainFont);
		buttonContainer.add(skipButton);
		buttonContainer.add(Box.createRigidArea(buttonSpacing));

		JButton saveButton = new JButton(":)");
		saveButton.setBackground(accentColor);
		saveButton.setForeground(detailColor);
		saveButton.setFont(mainFont);
		buttonContainer.add(saveButton);

		gbc.gridx = 1;
		gbc.gridy = 1;
		playbackContainer.add(buttonContainer, gbc);

		return playbackContainer;
	}
	

	/**
	 * Creates and returns the UI container for the Dancing Brazil Beats Visualizer
	 * Section. The dancing character will be updated in realtime along with the
	 * music from an outside music player class.
	 * 
	 * @return
	 */
	private Container createBrazilBeatsContainer() {
		Container brazilContainer = new Container();
		GridBagLayout colLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		brazilContainer.setLayout(colLayout);
		gbc.insets = INSET_GAP;
		
		JLabel brazilBeatsLabel = new JLabel("Beats Visualizer");
		brazilBeatsLabel.setFont(headerFont);
		brazilBeatsLabel.setForeground(detailColor);
		gbc.gridy = 0;
		brazilContainer.add(brazilBeatsLabel, gbc);
		
		beatsDanceView = new JLabel("");
		gbc.gridy = 1;
		updateBeatsView();
		brazilContainer.add(beatsDanceView, gbc);
		
		return brazilContainer;
	}

	/**
	 * Updates the song preview view to match current song's Album art, title, album
	 * name, and artist name
	 */
	private void updateSongPreview() {
		File albumImageFile = new File("Mmfood.jpg");
		Image albumImage;
		try {
			albumImage = ImageIO.read(albumImageFile);
			albumImage = albumImage.getScaledInstance(IMG_RES_MAX, IMG_RES_MAX, Image.SCALE_DEFAULT);
			ImageIcon albumIcon = new ImageIcon(albumImage);

			albumImagePreview.setIcon(albumIcon);
			songTitlePreview.setText("Rapp Snitch Knishes"); // Match song name
			albumTitlePreview.setText("MM...FOOD"); // Match album name
			artistTitlePreview.setText("MF DOOM"); // Match artist name

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void updateBeatsView() {
		File danceImageFile = new File("brazilDance.jpg");
		Image danceImage;
		try {
			danceImage = ImageIO.read(danceImageFile);
			danceImage = danceImage.getScaledInstance(IMG_RES_MAX, IMG_RES_MAX, Image.SCALE_DEFAULT);
			ImageIcon albumIcon = new ImageIcon(danceImage);

			beatsDanceView.setIcon(albumIcon);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


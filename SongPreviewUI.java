import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class SongPreviewUI extends Container{
	private BrazilBeatsUI gui;
	/**
	 * 
	 */
	private SongPlayer songPlayer;
	
	private static final long serialVersionUID = 1L;
	private JLabel albumImagePreview;
	private JLabel songTitlePreview;
	private JLabel albumTitlePreview;
	private JLabel artistTitlePreview;
	/**
	 * Creates the container with all components used for displaying Current song
	 * info and album art. Will later have a method used for updating graphics to
	 * always match current song playing.
	 * 
	 * @return
	 */
	SongPreviewUI() {
		gui = Main.gui;
		songPlayer = Main.songPlayer;
		
		GridBagLayout colLayout = new GridBagLayout();

		this.setLayout(colLayout);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = BrazilBeatsUI.INSET_GAP;
		gbc.anchor = GridBagConstraints.WEST;
		// Create components
		// TESTING ALBUM COVER -- HARDCODED ----
		albumImagePreview = new JLabel("");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		this.add(albumImagePreview, gbc);

		songTitlePreview = new JLabel("");
		songTitlePreview.setFont(BrazilBeatsUI.mainFont);
		songTitlePreview.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridy = 2;
		this.add(songTitlePreview, gbc);

		albumTitlePreview = new JLabel("");
		albumTitlePreview.setFont(BrazilBeatsUI.captionFont);
		albumTitlePreview.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridy = 3;
		this.add(albumTitlePreview, gbc);

		artistTitlePreview = new JLabel("");
		artistTitlePreview.setFont(BrazilBeatsUI.captionFont);
		artistTitlePreview.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridy = 4;
		this.add(artistTitlePreview, gbc);
		// add the mouseListener so we can add the pane switching
		this.addMouseListener(new MouseListener() { // Mouse listener to switch to Playlist view when pressed
			@Override
			public void mouseClicked(MouseEvent e) {
				gui.switchMainViewPane("Song View");
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

		updateSongPreview();

	}
	
	/**
	 * Updates the song preview view to match current song's Album art, title, album
	 * name, and artist name
	 */
	public void updateSongPreview() {
		Song currentSong = songPlayer.getCurrentSong();
		
		
		File albumImageFile = new File(currentSong.coverPath);
		
		
		Image albumImage;
		try {
			albumImage = ImageIO.read(albumImageFile);
			albumImage = albumImage.getScaledInstance(BrazilBeatsUI.IMG_RES_MAX, BrazilBeatsUI.IMG_RES_MAX, Image.SCALE_DEFAULT);
			ImageIcon albumIcon = new ImageIcon(albumImage);

			albumImagePreview.setIcon(albumIcon);
			songTitlePreview.setText(currentSong.title); // Match song name
			albumTitlePreview.setText(currentSong.album); // Match album name
			artistTitlePreview.setText(currentSong.artist); // Match artist name

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

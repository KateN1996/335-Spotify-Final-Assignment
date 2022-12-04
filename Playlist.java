import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
/**
 * @author Kate Nixon
 * 
 * Playlist.java
 * 
 * This is a class representing a Playlist of Songs. In this case, the playlist
 * is meant to work together with 4 other classes to emulate spotify;
 * these 4 other classes are as follows: Library, Song, User, and UserCollection
 * 
 * The Playlist class has two constructors. One constructor just takes in a String
 * as the name of the playlist while the other constructor takes in a name and
 * a list of Song objects as the contents.
 * 
 * The methods of Playlist are as follows:
 * 		-getName() - returns name
 * 		-addSong() - adds Song to the playlist
 * 		-play() - plays the playlist
 * 		-removeSong() - removes song from the playlist
 *
 */
public class Playlist {
	private String name;
	private List <Song> contents = new ArrayList<Song>();
	private ImageIcon image;
	private Song curSong;
	
	/**
	 * Constructs a new Playlist object with just the name of the playlist
	 * @param name
	 */
	public Playlist(String name, String image) {
		this.name = name;
		if(image.equals("")) {
			image = "blank.png";
		}
		this.image = new ImageIcon(image);
		curSong = null;
	}
	/**
	 * Constructs a new Playlist object with both a string representing
	 * the name of the playlist along with a list of Song objects that 
	 * are the content of the playlist
	 * @param name
	 * @param contents
	 */
	public Playlist(String name, List<Song>contents) {
		this.name = name;
		this.contents = contents;
	}
	
	public ImageIcon getImage() {
		return image;
	}
	
	public void setImage(String image) {
		if(image.equals("")) {
			image = "blank.png";
		}
		this.image = new ImageIcon(image);
	}
	/**
	 * Getter that returns the name of the play list
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Adds the Song object to the playlist
	 * @param song
	 */
	public void addSong(Song song) {
		contents.add(song);
	}
	/**
	 * Plays the song. This method relies on the Song
	 * object's play method (which in turn relies on the Song
	 * object's toString() method to describe the song)
	 */
	public void play() {
		for(int i = 0; i < contents.size(); i++) {
			contents.get(i).play();
		}
	}
	/**
	 * Removes the Song from the playlist if it exists in
	 * the playlist
	 * @param song
	 */
	public void removeSong(Song song) {
		contents.remove(song);
	}
	
	public Song getSong(String name) {
		for(Song s : contents) {
			if(s.getTitle().equalsIgnoreCase(name)) {
				return s;
			}
		}
		return null;
	}
	
	public void setCurSong(String name) {
		curSong = this.getSong(name);
	}
	
	public Song getCurSong() {
		return curSong;
	}
}

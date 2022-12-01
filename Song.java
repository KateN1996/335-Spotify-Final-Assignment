/**
 * @author Kate Nixon
 
 * 
 * Song.java
 * 
 * This is a class representing a song. In this case, the song is meant to 
 * work together with 4 other classes to emulate spotify; these 4 other classes
 * are as follows: Library, Playlist, User, and UserCollection
 * 
 * The song takes two Strings in the constructor: title and artist.
 * Song also stored how many times it as been played as an int
 * 
 * The methods of Song are as follows:
 * getTitle() - returns the title
 * getArtist() - returns the artist
 * getTimesPlayed() - returns how many times the song has been played
 * play() - prints the description of the song then increments times played
 * toString() - creates a String representation of the song
 * 			  - the format is: title + " by " + artist + ", " + timesPlayed + " play(s)"
 *
 */
public class Song {
	private String title;
	private String artist;
	private int timesPlayed;
	/**
	 * Constructs a new instance of the Song class
	 * with the specified song title and artist
	 * @param title
	 * @param artist
	 */
	public Song(String title, String artist) {
		this.title = title;
		this.artist = artist;
		timesPlayed = 0;
	}
	/**
	 * Returns the title of the song, which is a String
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * returns the art of the song, which is  a string
	 * @return artist
	 */
	public String getArtist() {
		return artist;
	}
	/**
	 * returns the number (int) of times the song has been played
	 * @return timesPlayed
	 */
	public int getTimesPlayed() {
		return timesPlayed;
	}
	/**
	 * play song somehow
	 */
	public void play() {
	}
	
	/**
	 * returns a String representation of the song, with title, artists, and
	 * number of times played
	 */
	@Override
	public String toString() {
		return title + " by " + artist + ", " + timesPlayed + " play(s)";

	}

}

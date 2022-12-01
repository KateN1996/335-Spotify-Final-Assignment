import java.util.ArrayList;
import java.util.List;
/**
 * @author Kate Nixon
 * 
 * User.java
 * 
 * This is a class representing a User.
 * 
 * The User takes two Strings in the constructor: name and password.
 * 
 * The methods of User are as follows:
 * 			-getName() - returns name
 * 			-attemptLogin() - returns Boolean of if the attempted password
 * 			 matches the User's password
 * 			-addPlaylist() - adds a playlist to all of the users playlists if
 * 							 it doesn't already exist within the playlists
 * 			-getPlaylists() - returns list of all playlists
 * 			-selectPlaylist() - plays selected playlist
 * 			-toString() - returns string representing user with the format of
 *						  "username, # playlist(s)"
 *
 */
public class User {
	private String name;
	private String password;
	private List<Playlist> playlists = new ArrayList<Playlist>();
	/**
	 * Constructs a new user with two strings, one for name and one for password
	 * @param name
	 * @param password
	 */
	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}
	/**
	 * Gets and returns the name of the user
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * returns true if attempted password is equal to the user's password
	 * @param password
	 * @return boolean value
	 */
	public boolean attemptLogin(String password) {
		return this.password.equals(password);
	}
	/**
	 * Adds a Playlist object to the list of user's Playlists
	 * Only adds the playlist if the playlist doesn't already exist
	 * within the collection of playlists
	 * @param newPlaylist
	 */
	public void addPlaylist(Playlist newPlaylist) {
		if(!playlists.contains(newPlaylist)) {
			playlists.add(newPlaylist);
		}
	}
	/**
	 * returns the list of all Playlists
	 * @return playlist
	 */
	public List<Playlist> getPlaylists(){
		return playlists;
	}
	
	public List<String> getPlaylistNames(){
		List<String> names = new ArrayList<>();
		for(Playlist p : playlists) {
			names.add(p.getName());
		}
		return names;
	}
	/**
	 * Plays the selected playlist if it exists within the list of playlists
	 * @param name
	 */
	public void selectPlaylist(String name) {
		for(int i = 0; i < playlists.size();i++) {
			//if playlist exists within collection of playlists
			if(playlists.get(i).getName().equals(name)) {
				playlists.get(i).play();
			}
		}
	}
	/**
	 * Returns a string representation of the user.
	 * example if the user was "Kate" and she had 20 playlists
	 * it would return "Kate, 20 playlist(s)"
	 * @return String
	 */
	public String toString() {
		String len = Integer.toString(playlists.size());
		return name +  ", " + len + " playlist(s)";
	}
}

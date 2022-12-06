import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;



/*
 * 
 */
public class Playlist {

	//
	public final String title;
	private String playlistPath;
	private ArrayList<Song> songs;
	
	
	
	/*
	 * 
	 * NOTE - this requires SearchInterface to be initialized
	 */
	public Playlist(String playlistTitle) {
		title = playlistTitle;
		playlistPath = "./playlists/" + playlistTitle + ".txt";
		songs = new ArrayList<Song>();
		
		File file = new File(playlistPath);
		
		//System.out.println(file.getAbsolutePath());
		
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String songTitle = scanner.nextLine();
				Song song = SearchInterface.getSong(songTitle);
				if (song != null) {
					songs.add(song);
				}
			}
			scanner.close();
		}
		catch (FileNotFoundException e) {
			// missing file
			e.printStackTrace();
		}
		
		updateFile();
	}
	
	
	
	/*
	 * 
	 */
	public ArrayList<Song> getSongs(){
		return songs;
	}
	
	
	
	/*
	 * 
	 */
	public void addSong(Song song) {
		if (!songs.contains(song)) {
			songs.add(song);
			updateFile();
		}
	}
	
	
	
	/*
	 * 
	 */
	public void removeSong(Song song) {
		if (songs.contains(song)) {
			songs.remove(song);
			updateFile();
		}
	}
	
	
	
	/*
	 * 
	 */
	private void updateFile() {
		try {
            FileWriter writerObj = new FileWriter(playlistPath, false);
            for (Song song : songs) {
            	writerObj.write(song.title + '\n');
            }
            writerObj.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	
	
	/*
	 * 
	 */
	public void deletePlaylist() {
		File file = new File(playlistPath);
		if (file.exists()) {
			file.delete();
		}
	}
	
	
	
	/*
	 * 
	 */
	public Integer getSize() {
		return this.songs.size();
	}
	
	
	
	/*
	 * 
	 */
	public Song getSongAtIndex(Integer index) {
		return this.songs.get(index);
	}
	
	
	
	/*
	 * 
	 */
	public Integer getIndexOfSong(Song song) {
		Integer index = 0;
		for (Song plSong : songs) {
			if (plSong == song) {
				return index;
			}
			index += 1;
		}
		return null;
	}
	
	
	
	
	
	public ArrayList<Song> getSongsSortedByTitle(){
		ArrayList<Song> sortedSongs = new ArrayList<Song>(this.getSongs());		
		Collections.sort(sortedSongs, (o1, o2) -> (o1.getTitle().compareTo(o2.getTitle())));
		return sortedSongs;
	}
	
	public ArrayList<Song> getSongsSortedByAlbum(){
		ArrayList<Song> sortedSongs = new ArrayList<Song>(this.getSongs());		
		Collections.sort(sortedSongs, (o1, o2) -> (o1.getAlbum().compareTo(o2.getAlbum())));
		return sortedSongs;
	}

	public ArrayList<Song> getSongsSortedByArtist(){
		ArrayList<Song> sortedSongs = new ArrayList<Song>(this.getSongs());		
		Collections.sort(sortedSongs, (o1, o2) -> (o1.getArtist().compareTo(o2.getArtist())));
		return sortedSongs;
	}
	
}



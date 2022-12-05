import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;



/*
 *  organizes all data and provides a single search interface for the user/UI
 */
public class SearchInterface {

	// all available songs on the current system
	private static ArrayList<Song> songs;
	
	
	
	/*
	 * initialization is required to use static methods
	 */
	public SearchInterface() {
		
		// initializing list of available songs
		songs = new ArrayList<Song>();
		
		// getting list of song files
		String wavPath = "/wavs";
		File wavFolder = new File(wavPath);
		File[] wavFiles = wavFolder.listFiles();
		
		// populating available song list
		for (File file : wavFiles){
			String wavFileName = file.getName().replace(".wav", "");
			songs.add(new Song(wavFileName));
		}
		
	}
	
	
	
	/*
	 * returns an array of all existing songs
	 */
	public static ArrayList<Song> getAllSongs(){
		return songs;
	}
	
	
	
	/*
	 * returns the song with the given title.
	 * returns null if no song with the given title exists
	 */
	public static Song getSong(String songTitle) {
		for (Song song : songs) {
			if (song.title.equals(songTitle)) {
				return song;
			}
		}
		System.out.println("NO SONG FOUND: " + songTitle);
		return null;
	}
	
	
	
	/*
	 * returns a list of songs with similar attributes
	 * to searchtext and sorts by match percentage
	 */
	public static ArrayList<Song> search(String searchText){
				
		// filtering songs by match percentage and similarity floor value
		Double simFloor = 0.1;
		HashMap<Song, Double> songMatchMap = new HashMap<Song, Double>();
		for (Song song : songs) {
			Double titleMP = SongTitleComparator.computeSimilarityPercentage(song.title, searchText);
			Double albumMP = SongTitleComparator.computeSimilarityPercentage(song.artist, searchText);
			Double artistMP = SongTitleComparator.computeSimilarityPercentage(song.album, searchText);
			Double matchPer = Math.max(Math.max(titleMP,albumMP),artistMP);
			if (matchPer >= simFloor) {				
				songMatchMap.put(song, matchPer);
			}
		}
		
		// sorting by match percentage
		ArrayList<Song> results = new ArrayList<Song>();
		while (songMatchMap.size() > 0) {
			Double curMatchPer = -1.0;
			Song curMatchSong = null;
			for (Song song : songMatchMap.keySet()) {
				if (songMatchMap.get(song) > curMatchPer) {
					curMatchPer = songMatchMap.get(song);
					curMatchSong = song;
				}			
			}
			results.add(curMatchSong);
			songMatchMap.remove(curMatchSong);
		}
		
		// returning sorted song array
		return results;
		
	}
	
	
	
}

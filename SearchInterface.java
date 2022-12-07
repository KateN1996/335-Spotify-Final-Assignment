import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;



/**
 *  organizes all data and provides a single search interface for the user/UI
 *  
 *  @author Ryan Pecha
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
		String wavPath = "./wavs";
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
	private static ArrayList<Song> searchByAttributes(String searchText, boolean titleSearch, boolean artistSearch, boolean albumSearch){
		// filtering songs by match percentage and similarity floor value
		//Double simFloor = 0.1;
		HashMap<Song, Double> songMatchMap = new HashMap<Song, Double>();
		for (Song song : songs) {
			ArrayList<Double> matchPers = new ArrayList<Double>();
			if (titleSearch) {
				matchPers.add(StringComparator.computeSimilarityPercentage(song.title, searchText));
			}
			if (artistSearch) {
				matchPers.add(StringComparator.computeSimilarityPercentage(song.artist, searchText));
			}
			if (albumSearch) {
				matchPers.add(StringComparator.computeSimilarityPercentage(song.album, searchText));
			}
			Double matchPer = Collections.max(matchPers);
			songMatchMap.put(song, matchPer);
			/*
			if (matchPer >= simFloor) {				
				songMatchMap.put(song, matchPer);
			}
			*/
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
		
		// updating search results
		Main.playlistManager.getSearchResults().setSongs(results);
		
		// returning sorted song array
		return results;
	}
	
	
	
	/*
	 * returns a list of songs with similar attributes
	 * to searchtext and sorts by match percentage
	 */
	public static ArrayList<Song> searchByAll(String searchText){	
		return searchByAttributes(searchText, true, true, true);
	}
	
	
	
	/*
	 * returns a list of songs from all available songs,
	 * matched and sorted by song title
	 */
	public static ArrayList<Song> searchByTitle(String searchText){
		return searchByAttributes(searchText, true, false, false);
	}

	
	
	/*
	 * returns a list of songs from all available songs,
	 * matched and sorted by song artist
	 */
	public static ArrayList<Song> searchByArtist(String searchText){
		return searchByAttributes(searchText, false, true, false);
	}

	
	
	/*
	 * returns a list of songs from all available songs,
	 * matched and sorted by song album
	 */
	public static ArrayList<Song> searchByAlbum(String searchText){
		return searchByAttributes(searchText, false, false, true);
	}
	
	
	
}

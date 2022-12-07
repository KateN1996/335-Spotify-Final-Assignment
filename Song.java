import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;



/**
 * Class used to store a single song,
 * its attributes, and its data paths
 * 
 * @author Ryan Pecha
 */
public class Song {
	
	// song info
	public final String title;
	public final String artist;
	public final String album;
	
	// data paths
	public final String audioPath;
	public final String coverPath;
	public final String metaPath;
	
	/*
	 *  constructor
	 */
	public Song(String title) {
		
		// title matches given title and should be predefined in file system via wav files
		this.title = title;
		
		// calculating data paths
		this.audioPath = "./wavs/" + title + ".wav";
		this.metaPath = "./metaData/" + title + ".txt";
		
		// reading metaData from metaData.txt file
		HashMap<String,String> metaData = new HashMap<String,String>();
		try {
			File file = new File(this.metaPath);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String[] splitLine = scanner.nextLine().split(":");
				String[] content = new String[splitLine.length - 1];
				System.arraycopy(splitLine, 1, content, 0, content.length);
				String joinedContent = String.join(":", content);
				metaData.put(splitLine[0], joinedContent);
			}
			scanner.close();
		}
		catch (FileNotFoundException e) {
			// missing file
			e.printStackTrace();
		}
		
		// getting specific attributes
		this.artist = metaData.get("artist");
		this.album = metaData.get("album");
		
		// getting cover path via album name
		this.coverPath = "./covers/" + this.album + ".jpg";

	}
	
	/*
	 * returns the title of this song.
	 * used for sorting
	 */
	public String getTitle() {
		return this.title;
	}
	
	/*
	 * returns the artist of this song.
	 * used for sorting
	 */
	public String getArtist() {
		return this.artist;
	}
	
	/*
	 * returns the album of this song.
	 * used for sorting
	 */
	public String getAlbum() {
		return this.album;
	}
	
}



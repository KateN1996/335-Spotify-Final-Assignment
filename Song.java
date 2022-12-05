import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;



/*
 * 
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
		
		this.title = title;
		this.audioPath = "./wavs/" + title + ".wav";
		this.coverPath = "./covers/" + title + ".jpg";
		this.metaPath = "./metaData/" + title + ".txt";
		
		// reading metaData
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
		
		// setting artist from metaData
		if (metaData.containsKey("artist")) {
			this.artist = metaData.get("artist");
		}
		else {
			this.artist = "UNKOWN";
		}
		
		// setting album from metaData
		if (metaData.containsKey("album")) {
			this.album = metaData.get("album");
		}
		else {
			this.album = "UNKOWN";
		}

	}
	
}



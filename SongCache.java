import java.util.HashMap;

// TODO add local file downloading

public class SongCache {
	
	private HashMap<String, Song> songMap;
	
	
	
	public SongCache() {
		songMap = new HashMap<String, Song>();
	}
	
	
	
	public void insertSong(String songTitle, Song song) {
		songMap.put(songTitle, song);
	}
	
	
	
	public Song getSong(String songTitle){
		if (containsSong(songTitle)) {
			return songMap.get(songTitle);
		}
		return null;
	}
	
	
	
	public boolean containsSong(String songTitle) {
		return songMap.containsKey(songTitle);
	}
	
	
	
	public boolean containsSong(Song song) {
		return songMap.containsValue(song);
	}
	
	
	
	public Integer getCacheSize() {
		return songMap.size();
	}
}




import java.util.ArrayList;

public abstract class SourceInterface {
	
	private SongCache songCache;
	
	
	
	public SourceInterface() {
		this.songCache = new SongCache();
	}
	
	
	
	public abstract ArrayList<SearchResult> search(String searchText);
	
	public abstract Song buildSong(String songTitle);
	
	
	
	public Song getSong(String songTitle) {
		if (songCache.containsSong(songTitle)) {
			return songCache.getSong(songTitle);
		}
		for (SearchResult searchResult : search(songTitle)) {
			if (searchResult.getSongTitle().equals(songTitle)) {
				Song song = buildSong(songTitle);
				songCache.insertSong(songTitle, song);
				return song;
			}
		}
		return null;
	}
	
}



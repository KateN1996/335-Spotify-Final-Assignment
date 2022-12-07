
/**
 * SearchResult Class extending the Playlist class to
 * allow for a persistent, automanaged, and hidden playlist
 * behind the search bar. This is insantiated once as a special
 * class by PlaylistManager and is autoupdated by SearchInterface.
 * 
 * @author Ryan Pecha
 */
public class SearchResults extends Playlist{
	
	/*
	 * Creating a new playlist with hardcoded SearchResults title 
	 */
	public SearchResults() {
		super("searchResults");
	}
		
	/*
	 * Cannot delete the SearchResults playlist
	 */
	@Override 
	public void deletePlaylist() {
		return;
	}
	
}

import java.util.ArrayList;

// organizes all data sources and provides a single source search interface
public class SearchInterface {

	private ArrayList<SourceInterface> sources;
	
	
	
	public SearchInterface() {
		sources = new ArrayList<SourceInterface>();		
		sources.add(new SourceInterface_Local());
		sources.add(new SourceInterface_SoundCloud());
		sources.add(new SourceInterface_Spotify());
		sources.add(new SourceInterface_YouTube());
	}
	
	
	
	public ArrayList<SearchResult> search(String searchText){
		ArrayList<SearchResult> searchResults = new ArrayList<SearchResult>();
		for (SourceInterface source : sources) {
			ArrayList<SearchResult> results = source.search(searchText);
			if (results != null) {				
				searchResults.addAll(results);
			}
		}
		return searchResults;
	}
	
	
	
	public Song getSong(String songTitle, SourceInterface source) {
		return source.getSong(songTitle);
	}
	
	public Song getSong(SearchResult searchResult) {
		return searchResult.getSource().getSong(searchResult.getSongTitle());
	}
}




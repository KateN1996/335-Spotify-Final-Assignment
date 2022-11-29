
public class SearchResult {
	
	private String songTitle;
	private SourceInterface source;
	private String sourcePath;
	
	public SearchResult( String songTitle, SourceInterface source, String sourcePath) {
		this.songTitle = songTitle;
		this.source = source;
		this.sourcePath = sourcePath;
	}
	
	public String getSongTitle() {
		return this.songTitle;
	}
	
	public SourceInterface getSource() {
		return this.source;
	}
	
	public String sourcePath() {
		return this.sourcePath;
	}
}

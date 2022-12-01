import java.util.ArrayList;

public class testAPI {

	public static void main(String[] args) {
		
		SearchInterface searchInterface = new SearchInterface();

		ArrayList<SearchResult> results = searchInterface.search("MF DOOM");
		for (SearchResult result : results) {
			System.out.println(result.getSongTitle());
		}
		
	}

}

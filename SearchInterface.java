import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

// organizes all data sources and provides a single source search interface
public class SearchInterface {

	private HashMap<String,String> songNameToPathMap;
	
	public SearchInterface() {
		songNameToPathMap = new HashMap<String,String>();
		
		File f = new File("C:\\example");
		File[] matchingFiles = f.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.startsWith("temp") && name.endsWith("txt");
		    }
		});
		
		for () {
			
		}
	}
	
	
	
	public ArrayList<Song> search(String searchText){
		
	}

}

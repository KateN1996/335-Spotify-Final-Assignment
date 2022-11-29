import java.util.ArrayList;

import de.voidplus.soundcloud.*;

public class SourceInterface_SoundCloud extends SourceInterface {

	public SoundCloud soundCloud;
	
	// https://github.com/nok/soundcloud-java-library#api
	// https://docs.google.com/forms/d/e/1FAIpQLSfNxc82RJuzC0DnISat7n4H-G7IsPQIdaMpe202iiHZEoso9w/closedform
	public SourceInterface_SoundCloud() {
		/*
		soundCloud = new SoundCloud(
		    "appClientId",
		    "appClientSecret"
		);
		*/
	}
	
	@Override
	public ArrayList<SearchResult> search(String searchText) {
		/*
		ArrayList<Track> result = soundCloud.findTrack("Chromatics");
		if(result!=null){
		    System.out.println("Tracks: "+result.size());
		    for(Track track:result){
		        System.out.println(track);
		    }
		}
		
		*/
		return null;
	}

	@Override
	public Song buildSong(String songTitle) {
		// TODO Auto-generated method stub
		return null;
	}

}

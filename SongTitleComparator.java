


/*
 * abstract class used to compare song titles
 */
public abstract class SongTitleComparator {

	
	
	/*
	 * compares 2 song title strings based similarity of characters.
	 * returns a double in the range 0-1 inclusive which correlates
	 * to the match percentage
	 */
	public static Double computeSimilarityPercentage(String title1, String title2) {
	    
		// case insensitive
		title1 = title1.toLowerCase();
	    title2 = title2.toLowerCase();
	    
	    // tracking distance cost
	    int[] costs = new int[title2.length() + 1];

	    // iterating over characters of s1
	    for (int i = 0; i <= title1.length(); i++) {
	    	// tracking previous value
	    	int previousValue = i;
	        
	    	// iterating over values of s2
	        for (int j = 0; j <= title2.length(); j++) {
	            
	        	// initializing costs with distance from start index
	        	if (i == 0) {
	                costs[j] = j;
	            } 
	            
	        	// past starting index
	        	else if (j > 0){
	        		// curCost = prevCost
                    int newValue = costs[j - 1];
                    // checking if characters are equal
                    if (title1.charAt(i - 1) != title2.charAt(j - 1)) {
                    	// (min of prevIndex,curIndex,curCost) + 1
                        newValue = Math.min(Math.min(newValue, previousValue), costs[j]) + 1;
                    }
                    // updating previous and cur value
                    costs[j - 1] = previousValue;
                    previousValue = newValue;
	            }
	        	
	        }
	        
	        // past starting index
	        if (i > 0) {
	        	// last index of cost is prevVal
	            costs[title2.length()] = previousValue;
	        }
	    }	 
	    
	    // grabbing final cost
	    Integer editDistance = costs[title2.length()];
	    
	    // verifying that s1 > s2
	    if (title1.length() < title2.length()) {
	        String temp = title1;
	        title1 = title2;
	        title2 = temp;
	    }
	    
	    // size of larger string
	    Integer s1Len = title1.length();
	    
	    // both strings are empty, fully matching
	    if (s1Len == 0) {
	    	return 1.0;
	    }

	    // else return match percentage. Range 0-1 inclusive
	    return (s1Len - editDistance) / (double) s1Len;
	}

	
	
	
	/*
	public static void printDistance(String s1, String s2) {
	    System.out.println(s1 + " & " + s2 + " | SIMILARITY PERCENTAGE : " + computeSimilarityPercentage(s1,s2));
	}



	public static void main(String[] args) {
		
	    printDistance("", "");
	    printDistance("1234567890", "1");
	    printDistance("1234567890", "12");
	    printDistance("1234567890", "123");
	    printDistance("1234567890", "1234");
	    printDistance("1234567890", "12345");
	    printDistance("1234567890", "123456");
	    printDistance("1234567890", "1234567");
	    printDistance("1234567890", "12345678");
	    printDistance("1234567890", "123456789");
	    printDistance("1234567890", "1234567890");
	    printDistance("1234567890", "1234567980");
	
	    printDistance("47/2010", "472010");
	    printDistance("47/2010", "472011");
	
	    printDistance("47/2010", "AB.CDEF");
	    printDistance("47/2010", "4B.CDEFG");
	    printDistance("47/2010", "AB.CDEFG");
	
	    printDistance("The quick fox jumped", "The fox jumped");
	    printDistance("The quick fox jumped", "The fox");
	    printDistance("The quick fox jumped",
	            "The quick fox jumped off the balcany");
	    printDistance("kitten", "sitting");
	    printDistance("rosettacode", "raisethysword");
	    printDistance(new StringBuilder("rosettacode").reverse().toString(),
	            new StringBuilder("raisethysword").reverse().toString());
	    for (int i = 1; i < args.length; i += 2) {
	        printDistance(args[i - 1], args[i]);
	    }
	    
	 }
	*/


}
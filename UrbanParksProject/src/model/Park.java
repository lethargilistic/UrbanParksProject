package model;

/**
 * This class is an instance of a park.
 * @author Arsh
 * @version May 1
 *
 */
public class Park {
	
	/**
	 * This holds the name of the park.
	 */
	private String myName;

	/**
	 * This holds the value of the location (the city) of the park.
	 */
    private String myLocation;

    /**
     * This holds the value of the zip code that the park is located in.
     */
    private int myZipCode;
    
    /**
     * This is a constructor.
     * @param theName is the name of the park
     * @param theLoc is the city of the park
     * @param theZip is the zip code of the location
     */
    public Park(String theName, String theLoc, int theZip) {
    	myName = theName;
    	myLocation = theLoc;
    	myZipCode = theZip;
    }

	public String getName() {
		return myName;
	}
    
	@Override
	public String toString()
	{
		return myName;
	}
}

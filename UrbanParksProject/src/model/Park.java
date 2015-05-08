package model;

/**
 * This class is an instance of a park.
 * @author Arsh
 * @version May 1
 *
 */
public class Park { // Because the myLocation and myZipCode fields are never used,
					// this brings up the question of whether we even need these fields.
					// Therefore, if we're only going to identify a Park by it's name as
					// a string, then it raises the question: do we even need a Park class?!?
	
	/**
	 * This holds the name of the park.
	 */
	private String myName;

	/**
	 * This holds the value of the location (the city) of the park.
	 */
    private String myLocation; // warning - not used!

    /**
     * This holds the value of the zip code that the park is located in.
     */
    private int myZipCode; // warning - not used!
    
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

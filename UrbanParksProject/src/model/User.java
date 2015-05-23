package model;

/**
 * This is a general class for all Users of our Urban Parks program.
 * 
 * It applies to Administrators, ParkManagers, and Volunteers.
 * 
 * @author Reid Thompson - initial implementation
 * @version 5.16.2015
 */
public abstract class User {
	private String myFirstName;
	private String myLastName;
	private String myEmail;
	private String myUserType;
	
	public User(String theFirstName, String theLastName, String theEmail) {
		myFirstName = theFirstName;
		myLastName = theLastName;
		myEmail = theEmail;
	}
	
	public String getFirstName() {
		return myFirstName;
	}
	
	public String getLastName() {
		return myLastName;
	}
	
	public String getEmail() {
		return myEmail;
	}
	
	protected void setUserType(String theUserType) {
		myUserType = theUserType;
	}
	
	public String getUserType() {
		return myUserType;
	}
	
	// Reid: first and foremost, do we even need setters? All fields should be initially set in the constructor,
	// Reid: and none of the user stories specify that we have to allow users to change their data in the system.
	// Reid: b/c of this fact, I'm opting NOT to give access to a "setUserType()" method b/c that data shouldn't change.
	// Reid: if we keep these setters, maybe we could return a boolean indicating status of set operation.
	// Reid: ie. if the caller passes in an empty string/null, etc. etc.
	
	public void setFirstName(String theFirstName) {
		myFirstName = theFirstName;
	}
	
	public void setLastName(String theLastName) {
		myLastName = theLastName;
	}
	
	public void setEmail(String theEmail) {
		myEmail = theEmail;
	}
	
	public boolean isVolunteer() {
		return myUserType.equals("Volunteer");
	}
	
	public boolean isParkManager() {
		return myUserType.equals("ParkManager");
	}
	
	public boolean isAdministrator() {
		return myUserType.equals("Administrator");
	}
}

package model;

import java.util.ArrayList;
import java.util.List;

/**
 * User List contains a list of all users, including Volunteers, Park Managers, and Administrators. <br>
 * @author Taylor Gorman
 * @version 8 May 2015
 *
 */
public class UserList {

	//User Lists
	private List<Volunteer> myVolunteerList;
	private List<ParkManager> myParkManagerList;
	private List<Administrator> myAdministratorList;
	
		
	public UserList() {
		myVolunteerList = new ArrayList<Volunteer>();
		myParkManagerList = new ArrayList<ParkManager>();
		myAdministratorList = new ArrayList<Administrator>();
	}
	
	
	/*
	 * List Getters.
	 * Returns copies of lists so that they cannot be changed without passing them to a setter.	
	 */
	
	/**
	 * Return a copy of the Volunteer List.
	 */
	public List<Volunteer> getVolunteerCopyList() {
		return new ArrayList<Volunteer>(myVolunteerList);
	}
	
	/**
	 * Return a copy of the ParkManager List.
	 */
	public List<ParkManager> getParkManagerCopyList() {
		return new ArrayList<ParkManager>(myParkManagerList);
	}
	
	/**
	 * Return a copy of the Administrator List.
	 */
	public List<Administrator> getAdministratorCopyList() {
		return new ArrayList<Administrator>(myAdministratorList);
	}
		
	
	
	/*
	 * List Setters
     * Because we only give out copies of lists, any modified version must be passed through here for changes
	 * to be made permanent. Thus, Schedule can still check for any business rule violations.
	 */	
	
	/**
	 * Update the Volunteer List with a new version.
	 */
	public void setVolunteerList(List<Volunteer> theVolunteerList) {
		this.myVolunteerList = theVolunteerList;
	}
	
	/**
	 * Update the ParkManager List with a new version.
	 */
	public void setParkManagerList(List<ParkManager> theParkManagerList) {
		this.myParkManagerList = theParkManagerList;
	}
	
	/**
	 * Update the Administrator List with a new version.
	 */
	public void setAdministratorList(List<Administrator> theAdministratorList) {
		this.myAdministratorList = theAdministratorList;
	}

}

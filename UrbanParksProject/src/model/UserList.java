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
	
	/** 
	 * This method returns an actual reference to myVolunteerList.
	 */
	public List<Volunteer> getVolunteerList() {
		return myVolunteerList;
	}
	
	/**
	 * NOTE: we didnt have this on our pseudocode but I thought we should add it so that
	 * we can keep the volunteer list private.
	 * 
	 * This method returns a copy of myVolunteerList.
	 * 
	 * @return a list.
	 */
	public List<Volunteer> getCopyVList() {
		return new ArrayList<Volunteer>(myVolunteerList);
	}
}

package model;

import java.util.ArrayList;
import java.util.List;

public class UserList {
	/**
	 * This is a list of volunteers who have created accounts with the program.
	 */
	private List<Volunteer> myVolunteerList;
	
	
	
	
	public UserList() {
		myVolunteerList = new ArrayList<Volunteer>();
	}
	
	/**
	 * NOTE: we didnt have this on our pseudocode but I thought we should add it so that
	 * we can keep the volunteer list private.
	 * 
	 * This method returns an actual reference to myVolunteerList.
	 * @return a list.
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

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The class for the Administrator user for the system.
 * 
 * Administrators can search for Volunteers by last name.
 * 
 * @author Reid Thompson
 * @author Taylor Gorman
 * @version 8 May 2015
 */
public class Administrator extends User {

	private DataPollster myPollster = DataPollster.getInstance();

	/**
	 * Constructs an Administrator object.
	 * 
	 * @param theFirstName
	 *            The first name of the Administrator.
	 * @param theLastName
	 *            The last name of the Administrator.
	 * @param theEmail
	 *            The email of the Administrator.
	 */
	public Administrator(String theFirstName, String theLastName,
							String theEmail) {
		super(theFirstName, theLastName, theEmail);
	}

	/**
	 * Returns a List of all Volunteers matching the given last name.
	 * 
	 * @param theLastName
	 *            is the last name by which to search for Volunteer matches.
	 * @return a List of Volunteers with the same last name as theLastName.
	 */
	public List<User> getMatchingVolunteers(String theLastName) {
		List<User> matchingVols = new ArrayList<>();

		List<User> allVols = myPollster.getVolunteerListCopy();

		for (int i = 0; i < allVols.size(); i++) {
			final User currVol = allVols.get(i);
			if (currVol.getLastName().equals(theLastName)) {
				matchingVols.add(currVol);
			}
		}

		return matchingVols;
	}

	public List<User> getAllVolunteersByLNFN() {
		List<User> allVols = myPollster.getVolunteerListCopy();

		Collections.sort(allVols, new Comparator<User>() {

			// to sort Volunteers by last name ascending and then by first name
			// ascending
			@Override
			public int compare(User theVol1, User theVol2) {
				int result = theVol1.getLastName().compareTo(
						theVol2.getLastName());
				if (result == 0) {
					result = theVol1.getFirstName().compareTo(
							theVol2.getFirstName());
				}
				return result;
			}

		});
		return allVols;
	}
}

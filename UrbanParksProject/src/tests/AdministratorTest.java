package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import model.Administrator;
import model.DataPollster;
import model.User;
import model.UserList;
import model.Volunteer;

import org.junit.Before;
import org.junit.Test;

public class AdministratorTest {

	Administrator myAdmin;
	UserList myUserList;
	
	@Before
	public void setUp() throws Exception {
		myAdmin = new Administrator("Tom", "Petty", "americangirl@heartbreakers.net");
		
		myUserList = new UserList();
		myUserList.addNewVolunteer(new Volunteer("thenews@ifthisis.it", "Huey", "Lewis"));
		myUserList.addNewVolunteer(new Volunteer("awesome@goodwill.org", "Ryan", "Lewis"));
		myUserList.addNewVolunteer(new Volunteer("cocoon@arrows.co.uk", "Polly", "Scattergood"));
		myUserList.addNewVolunteer(new Volunteer("takealook@me.now", "Phil", "Collins"));
		myUserList.addNewVolunteer(new Volunteer("pedestrian@best.net", "Courtney", "Barnett"));
		DataPollster.getInstance().setUserList(myUserList);
	}

	@Test
	public void testGetMatchingVolunteersForSingleResult() {
		List<User> u = new ArrayList<>();
		u.add(myUserList.getVolunteerListCopy().get(2));
		
		assertEquals(u, myAdmin.getMatchingVolunteers("Scattergood"));
		
		u = new ArrayList<>();
		u.add(myUserList.getVolunteerListCopy().get(4));
		
		assertEquals(u, myAdmin.getMatchingVolunteers("Barnett"));
	}
	
	@Test
	public void testGetMatchingVolunteersForMultipleResults() {
		List<User> u = new ArrayList<>();
		u.add(myUserList.getVolunteerListCopy().get(0));
		u.add(myUserList.getVolunteerListCopy().get(1));
		
		assertEquals(u, myAdmin.getMatchingVolunteers("Lewis"));
	}
	
	@Test
	public void testGetAllVolunteersByLNFN() {
		List<User> alphabetizedUsers = new ArrayList<>();
		alphabetizedUsers.add(myUserList.getVolunteerListCopy().get(4));
		alphabetizedUsers.add(myUserList.getVolunteerListCopy().get(3));
		alphabetizedUsers.add(myUserList.getVolunteerListCopy().get(0));
		alphabetizedUsers.add(myUserList.getVolunteerListCopy().get(1));
		alphabetizedUsers.add(myUserList.getVolunteerListCopy().get(2));
		
		assertEquals(alphabetizedUsers, myAdmin.getAllVolunteersByLNFN());
	}

}

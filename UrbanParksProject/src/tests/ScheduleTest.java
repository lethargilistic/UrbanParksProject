package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import model.Job;
import model.JobList;
import model.ParkManager;
import model.Schedule;
import model.UserList;
import model.Volunteer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A test class for Schedule in the model package.
 * 
 * @author Reid Thompson
 * @date 5.3.2015
 */
public class ScheduleTest {

    /**
     * Schedule object for testing.
     */
    private Schedule mySchedule;

    /**
     * Job object for testing.
     */
    private Job myJob;

    private String myVolEmail;

    /**
     * Volunteer object for testing.
     */
    private Volunteer myVolunteer;

    private ParkManager myParkManager;

    /**
     * Instantiates necessary data for testing methods.
     */
    @Before
    public void setUp() {
        mySchedule = Schedule.getInstance();
        JobList myJobList = new JobList();
        UserList myUserList = new UserList();
        mySchedule.setJobList(myJobList);
        mySchedule.setUserList(myUserList);
        
        List<String> pList = new ArrayList<>();
        pList.add("Foo Park");
        myParkManager = new ParkManager("tjsg1992@gmail.com", "Taylor", "Gorman", pList);
        myJob = new Job(55, "Foo Park", 10, 10, 10, "06172015", "06172015",
                        "tjsg1992@gmail.com", new ArrayList<ArrayList<String>>());

        myVolunteer = new Volunteer("generic@gmail.com", "Bob", "Smith");
    }

    /**
     * Testing if a valid job can be received.
     */
    @Test
    public void testForValidReceiveJob() {
        mySchedule.receiveJob(myJob);
        assertFalse("No job was added.", mySchedule.getJobList().isEmpty());
        assertEquals("The incorrect job was added.", "[Foo Park]", 
                    mySchedule.getJobList().toString());
    }

    /**
     * Testing with a Job having invalid dates.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testForInvalidDatesReceiveJob() {
        Job badDates = new Job(0, "Foo Park", 10, 10, 10, "06172015", "06152015",
                                "tjsg1992@gmail.com", new ArrayList<ArrayList<String>>());
        mySchedule.receiveJob(badDates);
    }

    /**
     * Testing with a Job that doesn't have an empty Volunteer list.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testForEmptyVolListReceiveJob() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add(myVolEmail);
        temp.add("Light");
        ArrayList<ArrayList<String>> temp2 = new ArrayList<>();
        temp2.add(temp);

        myJob.getVolunteerList().add(temp);
        mySchedule.receiveJob(myJob);
    }

    /**
     * Testing with a Job with all zeroes for light, medium, and heavy
     * work grade amounts.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testForNoWorkersReceiveJob() {
        Job noSpace = new Job(55, "Foo Park", 0, 0, 0, "06172015", "06172015",
                                "tjsg1992@gmail.com", new ArrayList<ArrayList<String>>());
        mySchedule.receiveJob(noSpace);
    }

    /**
     * Testing with a Job with a null Park.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testForNullParkReceiveJob() {
        Job nullPark = new Job(55, "Foo Park", 2, 2, 2, "06172015", "06172015",
                                "tjsg1992@gmail.com", null);
        mySchedule.receiveJob(nullPark);
    }

    /**
     * Testing with a Job with a null ParkManager.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testForNullParkManagerReceiveJob() {
        Job nullMngr = new Job(55, "Foo Park", 2, 2, 2, "06172015", "06172015", null,
                                new ArrayList<ArrayList<String>>());
        mySchedule.receiveJob(nullMngr);
    }

    /**
     * Testing with a negative job id number.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testForAddNegJobIDVolunteerToJob() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("moverby@gmail.com");
        temp.add("Light");
        mySchedule.addVolunteerToJob(temp, -10);
    }

    /**
     * Testing with an invalid work grade.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testForInvalidWorkGradeAddVolunteerToJob() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("moverby@gmail.com");
        temp.add("Quasi-Light");
        mySchedule.addVolunteerToJob(temp, 10);
    }
}

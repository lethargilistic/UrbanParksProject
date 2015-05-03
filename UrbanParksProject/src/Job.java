package arsh_360;

import java.util.Date;
import java.util.List;

/**
 * This class is used to create an instance of a Job.
 * @author Arsh
 * @version May 1
 */
public class Job {
	
	
	/**
	 * This value is the identification number of a job.
	 */
	public int jobID;

	/**
	 * This is the list which holds the volunteers that have signed up to participate in this job.
	 */
    public List<Volunteer> myVolunteerList;

    /**
     * NOTE: I don't know what this does but it was in the pseudo-code so I kept it here.
     */
    public boolean myIsRequest;

    
    
    /**
     * 							NOTE: Shouldn't we make these dates PRIVATE?
     * 									I looked at the DataPollster class and these dates are
     * 									being used directly in the code.
     * 									Maybe we should create getter methods instead?
     * 										Note: I created the getter methods in case anyone needs them.
     * 
     * This is the start date of the job.
     */
    public Date myStartDate;

    /**
     * This is the ending date of the job.
     */
    public Date myEndDate;

    /**
     * This value holds the maximum number of people who can sign up for the light portion
     * of this job.
     */
    private int myLightMax;

    /**
     * This value holds the current number of people who have signed for the light portion
     * of this job.
     */
    private int myLightCurrent;

    /**
     * This value holds the maximum number of people who can sign up for the medium portion
     * of this job.
     */
    private int myMediumMax;

    /**
     * This value holds the current number of people who have signed for the medium portion
     * of this job.
     */
    private int myMediumCurrent;

    /**
     * This value holds the maximum number of people who can sign up for the heavy portion
     * of this job.
     */
    private int myHeavyMax;

    /**
     * This value holds the current number of people who have signed for the heavy portion
     * of this job.
     */
    private int myHeavyCurrent;

    /**
     * This value holds the park that the job will be located at.
     */
    public Park myPark;

    /**
     * This value holds the manager who created the job.
     */
    public ParkManager myManager;


    /**
     * This is a constructor.
     * @param thePark is the park 
     * @param theLightSlots is the max number of light positions
     * @param theMediumSlots is the max number of medium positions
     * @param theHeavySlots is the max number of heavy positions
     * @param theStartDate is the starting date of the job
     * @param theEndDate is the ending date of the job.
     */
    public Job(Park thePark, int theLightSlots, int theMediumSlots, int theHeavySlots, Date theStartDate, Date theEndDate) {
    	myPark = thePark;
    	myLightMax = theLightSlots;
    	myMediumMax = theMediumSlots;
    	myHeavyMax = theHeavySlots;
    	myStartDate = theStartDate;
    	myEndDate = theEndDate;
    }
    
    /**
     * This is used to return the starting date of the job.
     * @return myStartDate
     */
    public Date getStartDate() {
    	return myStartDate;
    }
    
    /**
     * This is used to return the ending date of the job.
     * @return myEndDate
     */
    public Date getEndDate() {
    	return myEndDate;
    }
    
    /**
     * This method is called when someone has signed up for a light portion of this job.
     * 
     * @throws IllegalStateException if called after max has been reached.
     */
    public void incrementLight() {
    	 if (myLightCurrent < myLightMax) {
    		 myLightCurrent ++;
    	 } else {
    		 throw new IllegalStateException("Max has already been reached. Cannot increment.");
    	 }
    }

    /**
     * This method is called when someone has signed up for a medium portion of this job.
     * 
     * @throws IllegalStateException if called after max has been reached.
     */
    public void incrementMedium() {
    	if (myMediumCurrent < myMediumMax) {
    		myMediumCurrent ++;
    	} else {
    		throw new IllegalStateException("Max has already been reached. Cannot increment.");
    	}
    }

    /**
     * This method is called when someone has signed up for a heavy portion of this job.
     * 
     * @throws IllegalStateException if called after max has been reached.
     */
    public void incrementHeavy() {
    	if (myHeavyCurrent < myHeavyMax) {
    		myHeavyCurrent ++;
    	} else {
    		throw new IllegalStateException("Max has already been reached. Cannot increment.");
    	}
    }


}
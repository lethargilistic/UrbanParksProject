package model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Volunteer {
	
	private String myFirstName;
	private String myLastName;
	private String myEmail;
	
	private VolunteerUI myUI;
	private DataPollster myPoll;
	private Schedule mySched;
	
	
	public Volunteer(String theEmail) {
		this.myEmail = theEmail;
	}
	
	public Volunteer(String theEmail, String firstName, String lastName)
	{
		this.myEmail = theEmail;
		myFirstName = firstName;
		myLastName = lastName;
	}
	
	//TODO remove this constructor, implement getters and setters for First and Last name instead.
	public Volunteer(String firstName, String lastName, DataPollster thePoll, Schedule theSched) {
		myFirstName = firstName;
		myLastName = lastName;
		
		myPoll = thePoll;
		mySched = theSched;
		myUI = new VolunteerUI();
	}
	
	public Volunteer(Schedule theSchedule, DataPollster thePollster, String theEmail) {
		this.mySched = theSchedule;
		this.myPoll = thePollster;
		this.myEmail = theEmail;
		myUI = new VolunteerUI();
	}
	
	public String getFirstName() {
		return this.myFirstName;
	}
	
	public String getLastName() {
		return this.myLastName;
	}
	
	
	public String getEmail() {
		return myEmail;
	}

	public void setEmail(String theEmail) {
		myEmail = theEmail;
	}

	public void setFirstName(String theFirstName) {
		myFirstName = theFirstName;
	}

	public void setLastName(String theLastName) {
		myLastName = theLastName;
	}
	
	// is this a necessary method? when are we comparing vols for equality?
	//==============stuff Arsh added======================

	//TODO: Should be removed in favor of having the commands be processed in the UI.
	public void initialize() {
		commandLoop();
	}

	//TODO: Should be removed in favor of having the commands be processed in the UI.
	/**
	 * The main loop for Park Manager.<br>
	 * Lists possible commands, prompts the user for one, and then acts on it.<br>
	 * If the user chooses to quit, or inputs an invalid command, then the loop terminates.
	 */
	public void commandLoop() {
		myUI.listCommands();
		String command = myUI.getCommand();

		if(parseCommand(command)) {
			commandLoop();
		}
	}

	//TODO: Should be removed in favor of having the commands be processed in the UI.
	/**
	 * Parse a command, and call other methods to execute the command.
	 */
	public boolean parseCommand(String command) {
		command = command.toLowerCase(); //lower case to avoid ambiguity

		boolean status = true;
		
		switch(command) { 
		case "view jobs":
		case "view job":
		case "view":	
		case "v":
		case "1":
			viewAvailableJobs(); 
			break;

		case "sign up":
		case "sign":
		case "s":
		case "2":
			signUp();
			break;

		case "view my jobs":
		case "view my job":
		case "j":
		case "m":
		case "3":
			viewMyJobs();
			break;

		case "exit":
		case "close":
		case "quit":
		case "4":
			status = false; //return false if user wants to exit.
			break;
		default: 
			System.out.println("You did not enter a valid number.");
			break; //return true and prompt the user again.
		}
		
		return status;
	}



	/**
	 * This method gives a list of all the jobs to the UI for this class.
	 */
	public void viewAvailableJobs() {
		myUI.displayJobs(myPoll.getAllJobs());
	}


	/**
	 * The volunteer can sign up for jobs from here.
	 */
	private void signUp() {
		int jobID = myUI.getJobID();

		if(!checkPark(jobID)) { //enter this if park ID is NOT valid.
			myUI.showJobIDError();	//the code for checkPark() is not yet written, we have to write it.
			return;
		}

		int level = myUI.getDifficultyLevel();

		try {	//attempt to add this volunteer
			mySched.addVolunteerToJob(this.myEmail, jobID, level);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		//if the volunteer is added successfully then addVolunteerToJob() will print out a success message.
	}


	private void viewMyJobs() {
		List<Job> aList = mySched.getJobList().getCopyList(); //get the list of jobs so we can traverse it.

		//go through each job in the list and see if the volunteer has signed up for that job.
		for (Job j: aList) {
			if (j.myVolunteerList.contains(this)) {
				printJobInfo(j);
			}
		}
	}	
	//TODO: Why aren't we just using a Job toString here?
		private void printJobInfo(Job job) {
			String startDate = calendarToString(job.getStartDate());
			String endDate = calendarToString(job.getEndDate());
			
			String jobString = "\n";
			jobString += "Job ID: " + job.getJobID();
			jobString += "\n    " + job.getPark();
			
			jobString += "\n    Begins: " + startDate;
			jobString += " , Ends: " + endDate;
			
			jobString += "\n    Light Slots: " + job.getLightCurrent() + "/" + job.getLightMax();
			jobString += "\n    Medium Slots: " + job.getMediumCurrent() + "/" + job.getMediumMax();
			jobString += "\n    Heavy Slots: " + job.getHeavyCurrent() + "/" + job.getHeavyMax() + "\n";
			
			System.out.println(jobString);
		}
			
		/**
		 * Convert a GregorianCalendar object to string of format mmddyyyy
		 */
		private String calendarToString(GregorianCalendar theCalendar) {
			String returnString = theCalendar.get(Calendar.MONTH) + "/" +
					theCalendar.get(Calendar.DAY_OF_MONTH) + "/" +
					theCalendar.get(Calendar.YEAR);
			return returnString;
		}

		@Override
		public boolean equals(Object theO)
		{
			if (!(theO instanceof Volunteer))
				return false;
		
			Volunteer theOther = (Volunteer) theO;
			
			return this.myEmail.equals(theOther.myEmail);
					
		}
		
		@Override
		public String toString()
		{
			return myFirstName + " " + myLastName;
		}
		
		/**
		 * Check to make sure that the Job ID is valid.
		 * @return True if valid, false if not.
		 */
		private boolean checkPark(int theJobID) {
			boolean status = false;
			List<Job> aList = mySched.getJobList().getCopyList();
			for (Job j: aList) {
				if (j.getJobID() == theJobID) {
					status = true;
				}
			}

			return status; //if true was never return within the for-each loop then return false
		}
}

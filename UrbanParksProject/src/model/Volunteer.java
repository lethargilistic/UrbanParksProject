package model;

public class Volunteer {
	
	
	private String myFirstName;
	private String myLastName;
	
	private VolunteerUI myUI;
	private DataPollster myPoll;
	private Schedule mySched;
	
	
	
	public Volunteer(String firstName, String lastName)
	{
		myFirstName = firstName;
		myLastName = lastName;
		
	}
	
	public Volunteer(String firstName, String lastName, DataPollster thePoll, Schedule theSched) {
		myFirstName = firstName;
		myLastName = lastName;
		
		myPoll = thePoll;
		mySched = theSched;
		myUI = new VolunteerUI();
	}
	
	public String getFirstName() {
		return this.myFirstName;
	}
	
	public String getLastName() {
		return this.myLastName;
	}
	
	@Override
	public boolean equals(Object theO)
	{
		if (!(theO instanceof Volunteer))
			return false;
		
		Volunteer theOther = (Volunteer) theO;
		
		return this.myFirstName.equals(theOther.myFirstName)
			   && this.myLastName.equals(theOther.myLastName);
				
	}
	
	@Override
	public String toString()
	{
		return myFirstName + " " + myLastName;
	}
	
	
	
	//==============stuff I added======================
	
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
			System.out.println("Choose an option");
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
			
			switch(command) { 
				case "view jobs":
				case "view job":
				case "view":	
				case "v":
				case "1":
					viewAvailableJobs(); 
					return true;
				
				case "sign up":
				case "sign":
				case "s":
				case "2":
					signUp();
					return true;
					
				case "view my jobs":
				case "view my job":
				case "j":
				case "m":
				case "3":
					viewMyJobs();
					return true;
				
				case "exit":
				case "close":
				case "quit":
				case "4":
				default: 
					return false;
			}
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
				mySched.addVolunteerToJob(this, jobID, level);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return;
			}
			//if the volunteer is added successfully then addVolunteerToJob() will print out a success message.
		}


		private void viewMyJobs() {
			// TODO Auto-generated method stub

		}


		/**
		 * Check to make sure that the Job ID is valid.
		 * @return True if valid, false if not.
		 */
		private boolean checkPark(int theJobID) {
			//TODO
			return true; //Unsure of how to implement this at the moment. Will it be done through DataPollster?	
		}





}

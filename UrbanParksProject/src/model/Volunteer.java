package model;

public class Volunteer {
	public String myFirstName;
	public String myLastName;
	
	public Volunteer(String firstName, String lastName)
	{
		myFirstName = firstName;
		myLastName = lastName;
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
}

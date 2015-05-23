/**
 * 
 */
package model.businessRules;

import model.JobList;

/**
 * A job may not be added if the total number of pending jobs is currently 30
 */
public class BusinessRule1 extends BusinessRule {

	/**
	 * @param theTestedObject a JobList object.
	 * @see model.businessRules.BusinessRule#test()
	 */
	@Override
	public boolean test(Object theTestedObject) {
		if (theTestedObject instanceof JobList)
		{
			JobList jobs = (JobList) theTestedObject;
			return jobs.getNumberOfJobs() >= 30;
		}
		else
			throw new IllegalArgumentException("Did not pass a JobList.");
	}

}

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
	 * @param theTestedObjects a JobList object.
	 * {@inheritDoc}
	 */
	@Override
	public boolean test(Object... theTestedObjects) {
		if (!(theTestedObjects[0] instanceof JobList))
			throw new IllegalArgumentException("Did not pass a JobList.");

		JobList theJobList = (JobList) theTestedObjects[0];
		return theJobList.getNumberOfJobs() <= 30;
	}

}

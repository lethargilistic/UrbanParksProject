/**
 * 
 */
package model.businessRules;

import java.lang.reflect.MalformedParametersException;
import java.util.Calendar;
import java.util.List;

import model.Job;
import model.JobList;

/**
 * A job may not be added if the total number of pending jobs during that week (3 days on either side
 * of the job days) is currently 5. In other words, during any consecutive 7 day period there can be no
 * more than 5 jobs.
 */
public class BusinessRule2 extends BusinessRule {

	/**
	 * @param theTestedObjects a Job object.
	 * {@inheritDoc}
	 */
	@Override
	public boolean test(Object... theTestedObjects)
	{
		if (theTestedObjects.length > 2)
			throw new MalformedParametersException("More than 2 arguments.");
		
		if (!(theTestedObjects[0] instanceof Job))
			throw new IllegalArgumentException("First arg was not Job.");
		
		if (!(theTestedObjects[1] instanceof JobList))
			throw new IllegalArgumentException("First arg was not JobList.");
		
		Job theJob = (Job) theTestedObjects[0];
		JobList theJobList = (JobList) theTestedObjects[1];
		
		//count increases for every other job that is within 3 days before start and 3 days after end.
		int count = 0;
		
		//3 days before start and 3 days after end
		Calendar aStart = (Calendar) theJob.getStartDate().clone();
		
		aStart.add(Calendar.DAY_OF_MONTH, -3);

		Calendar aEnd = (Calendar) theJob.getEndDate().clone();
		aEnd.add(Calendar.DAY_OF_MONTH, 3);
		
		List<Job> aList = theJobList.getCopyList();
		for (Job j: aList) {
			
			long difference =  j.getEndDate().getTimeInMillis() - aStart.getTimeInMillis();
			difference /= (3600*24*1000); //change value to days instead of milliseconds
			long difference2 =  aEnd.getTimeInMillis() - j.getStartDate().getTimeInMillis();
			difference2 /= (3600*24*1000); //change value to days instead of milliseconds
			
			//if ending date of j is within 3 days before theJob, increment count
			//if starting date of j is within 3 days after theJob, increment count
			if ((difference <= 3 && difference >= 0)|| (difference2 <= 3 && difference2 >= 0)) {
				count++;
			}
			//System.out.println("Count is " + count);
			if (count >= 5) {
				return false;
			}
		}
		return true;
	}

}

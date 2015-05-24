package model.businessRules;

import java.lang.reflect.MalformedParametersException;
import java.util.GregorianCalendar;

import model.Job;

/**
 * A job may not be scheduled that lasts more than two days.
 */
public class BusinessRule4 extends BusinessRule {
	
	public static final int MAX_DURATION = 2;
	
	/**
	 * @param theTestedObjects a Job object
	 * {@inheritDoc}
	 */
	public boolean test(Object... theTestedObjects)
	{
		if (theTestedObjects.length > 1)
			throw new MalformedParametersException("More than 1 argument.");

		if (!(theTestedObjects[0] instanceof Job))
			throw new IllegalArgumentException("First arg was not Job.");
		
		Job theJob = (Job) theTestedObjects[0];
		
		GregorianCalendar tooLong = (GregorianCalendar)  theJob.getStartDate().clone();
		tooLong.add(GregorianCalendar.DATE, MAX_DURATION);
		
		return theJob.getEndDate().before(tooLong);
	}
}

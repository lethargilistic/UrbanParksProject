package model.businessRules;

import java.lang.reflect.MalformedParametersException;
import java.util.GregorianCalendar;

import model.Job;

/**
 * A job may not be scheduled that lasts more than two days.
 */
public class BusinessRule4 extends BusinessRule {
	
	/**
	 * {@inheritDoc}
	 */
	public boolean test(Object... theTestedObjects)
	{
		if (theTestedObjects.length > 2)
			throw new MalformedParametersException("More than 1 argument.");
		
		if (!(theTestedObjects[0] instanceof Job))
			throw new IllegalArgumentException("First arg was not Job.");
		
		Job theJob = (Job) theTestedObjects[0];
		
		GregorianCalendar nextDay = (GregorianCalendar)  theJob.getStartDate().clone();
		nextDay.add(GregorianCalendar.DATE, 1);
		
		return theJob.getEndDate().after(nextDay);
	}
}

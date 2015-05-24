/**
 * 
 */
package model.businessRules;

import static org.junit.Assert.*;

import java.lang.reflect.MalformedParametersException;
import java.util.GregorianCalendar;

import model.Job;
import model.JobList;

import org.junit.Before;
import org.junit.Test;

public class BusinessRule4Test {

	BusinessRule4 myRule;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		myRule = new BusinessRule4();		
	}

	@Test (expected = MalformedParametersException.class)
	public void testTestForTooManyArguments() {
		myRule.test(new Job(0, "Foo Park", 4, 4, 4, "09012015", "09012015", "moverby@vivaldi.com", null),
					new GregorianCalendar());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testTestForImproperArgument() {
		myRule.test(new GregorianCalendar());
	}

	/**
	 * Test method for {@link model.businessRules.BusinessRule4#test(java.lang.Object[])}.
	 */
	@Test
	public void testTestForSingleDayJob() {
		Job myJob = new Job(0, "Foo Park", 4, 4, 4, "09012015", "09012015", "moverby@vivaldi.com", null);
		assertTrue(myRule.test(myJob));
	}

	/**
	 * Test method for {@link model.businessRules.BusinessRule4#test(java.lang.Object[])}.
	 */
	@Test
	public void testTestForDuoDayJob() {
		int day = BusinessRule4.MAX_DURATION;
		String dayStr = day < 10 ? "0" + day : "" + day;
		
		Job myJob = new Job(0, "Foo Park", 4, 4, 4, "09012015", "09" + dayStr + "2015", "moverby@vivaldi.com", null);
		assertTrue(myRule.test(myJob));
	}
	
	/**
	 * Test method for {@link model.businessRules.BusinessRule4#test(java.lang.Object[])}.
	 */
	@Test
	public void testTestForTooLongJob() {
		int day = BusinessRule4.MAX_DURATION+1;
		String dayStr = day < 10 ? "0" + day : "" + day;
		
		Job myJob = new Job(0, "Foo Park", 4, 4, 4, "09012015", "09" + dayStr + "2015", "moverby@vivaldi.com", null);
		assertFalse(myRule.test(myJob));
	}	
}

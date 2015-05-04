/**
 * 
 */
package model.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import junit.framework.Assert;
import model.DataPollster;
import model.Job;
import model.JobList;
import model.Park;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Mike
 *
 */
public class DataPollsterTest {

	private JobList jl;
	private Park p;
	private DataPollster dp;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		jl = new JobList();
		p = new Park("Pioneer Park", "Tacoma", 98444);
		dp = new DataPollster(jl);
	}

	/**
	 * Test method for {@link model.DataPollster#getPendingJobs(model.Volunteer)}.
	 */
	@Test
	public void testGetPendingJobs() {
		List<Job> jobs = jl.getJobList();
		assertEquals(new ArrayList<Job>(), dp.getPendingJobs());
		
		jobs.add(new Job(p, 5, 5, 5, 
				new GregorianCalendar(2015, 6, 15), 
				new GregorianCalendar(2015, 6, 15)));
	}

	/**
	 * Test method for {@link model.DataPollster#getVolunteerJobs(model.Volunteer)}.
	 */
	@Test
	public void testGetVolunteerJobs() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.DataPollster#getManagerJobs(model.ParkManager)}.
	 */
	@Test
	public void testGetManagerJobs() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.DataPollster#getVolunteerList(int)}.
	 */
	@Test
	public void testGetVolunteerList() {
		fail("Not yet implemented");
	}

}

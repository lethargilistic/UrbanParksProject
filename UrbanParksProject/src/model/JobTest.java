package model;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JobTest {
	
	private Job myJob;
	private Park myPark;

	@Before
	public void setUp() throws Exception {
		GregorianCalendar startDate = new GregorianCalendar(6, 11, 1992);
		GregorianCalendar endDate = new GregorianCalendar(7, 11, 1992);
		myJob = new Job(myPark, 2, 3, 4, startDate, endDate);
	}

	@Test
	public void getterTest() {
		assertEquals(2, myJob.getLightMax());
		assertEquals(3, myJob.getMediumMax());
		assertEquals(4, myJob.getHeavyMax());
		assertEquals(0, myJob.getLightCurrent());
		assertEquals(0, myJob.getMediumCurrent());
		assertEquals(0, myJob.getHeavyCurrent());
	}
	
	@Test
	public void incrementTest() {
		myJob.incrementLight();
		
		myJob.incrementMedium();
		myJob.incrementMedium();
		myJob.incrementMedium();
		myJob.incrementMedium();

		assertEquals(0, myJob.getHeavyCurrent());
		assertEquals(4, myJob.getHeavyMax());
		
		assertEquals(1, myJob.getLightCurrent());
		assertEquals(2, myJob.getLightMax());
		
		assertEquals(3, myJob.getMediumCurrent());
		assertEquals(3, myJob.getMediumMax());
	}
	
	@Test
	public void roomTest() {
		assertTrue(myJob.hasLightRoom());
		assertTrue(myJob.hasMediumRoom());
		assertTrue(myJob.hasHeavyRoom());
		
		myJob.incrementLight();
		myJob.incrementLight();
		
		myJob.incrementMedium();
		myJob.incrementMedium();
		myJob.incrementMedium();
		myJob.incrementMedium();
		
		myJob.incrementHeavy();
		myJob.incrementHeavy();
		
		assertFalse(myJob.hasLightRoom());
		assertFalse(myJob.hasMediumRoom());
		assertTrue(myJob.hasHeavyRoom());
		assertTrue(myJob.hasRoom());
		
		myJob.incrementHeavy();
		myJob.incrementHeavy();
		
		assertFalse(myJob.hasHeavyRoom());
		assertFalse(myJob.hasRoom());
	}

}

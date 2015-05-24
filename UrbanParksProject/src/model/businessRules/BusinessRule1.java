package model.businessRules;

import java.lang.reflect.MalformedParametersException;

import model.JobList;

/**
 * A job may not be added if the total number of pending jobs is currently 30.
 */
public class BusinessRule1 extends BusinessRule {

    /**
     * The maximum number of pending jobs.
     */
    public static final int MAX_JOBS = 30;

    /**
     * @param theTestedObjects
     *            a JobList object. {@inheritDoc}
     */
    @Override
    public boolean test(Object... theTestedObjects) {
        if (theTestedObjects.length > 1)
            throw new MalformedParametersException("More than 1 argument.");
        if (!(theTestedObjects[0] instanceof JobList))
            throw new IllegalArgumentException("Did not pass a JobList.");

        JobList theJobList = (JobList) theTestedObjects[0];
        return theJobList.getNumberOfJobs() <= MAX_JOBS;
    }
}

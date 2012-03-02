package ua.ivanchenko.eman.model;

import java.math.*;
import java.util.*;

import ua.ivanchenko.eman.exceptions.*;
public interface IDataAccessor {
	/**
	 * method add department into data source.
	 * @param dept adder department
	 * @throws DataAccessException if got data source error.
	 */
	public void addDept(IDept dept) throws DataAccessException;
/**
	 * method add job into data source.
	 * @param job adder job
	 * @throws DataAccessException if got data source error.
	 */
	public void addJob(IJob job) throws DataAccessException;
    /**
	 * method add office into data source.
	 * @param off adder office
	 * @throws DataAccessException if got data source error.
	 */
	public void addOffice(IOffice off) throws DataAccessException;
     /**
	 * method add worker into data source.
	 * @param worker adder worker
	 * @throws DataAccessException if got data source error.
	 */
	public void addWorker(IWorker worker) throws DataAccessException;
	public Collection<IWorker> getTopManagers(String sort) throws DataAccessException;
	/**
	* return collection contain all departments
	 * @throws DataAccessException 
	*/
	public Collection<IDept> getAllDepts(String sort) throws DataAccessException;
	/**
	* return collection contain all jobs
	 * @throws DataAccessException 
	*/
	public Collection<IJob> getAllJobs(String sort) throws DataAccessException;
	/**
	* return collection contain all offices
	 * @throws DataAccessException 
	*/
	public Collection<IOffice> getAllOffices(String sort) throws DataAccessException;
	/**
	* return collection contain all workers
	 * @throws DataAccessException 
	*/
	public Collection<IWorker> getAllWorkers(String sort) throws DataAccessException;
	/**
    * Method returns department by identifier.
	 * @throws DataAccessException 
    */
	public IDept getDeptByID(BigInteger id) throws DataAccessException;
	/**
    * Method returns department by title.
	 * @throws DataAccessException 
    */
	public IDept getDeptByTitle(String title) throws DataAccessException;
    /**
    * Method returns job by identifier.
     * @throws DataAccessException 
    */
	public IJob getJobByID(BigInteger id) throws DataAccessException;
	/**
    * Method returns job by title.
	 * @throws DataAccessException 
    */
	public IJob getJobByTitle(String title) throws DataAccessException;
	/**
    * Method returns office by identifier.
	 * @throws DataAccessException 
    */
	public IOffice getOfficeByID(BigInteger id) throws DataAccessException;
	/**
    * Method returns office by title.
	 * @throws DataAccessException 
    */
	public IOffice getOfficeByTitle (String title) throws DataAccessException;
   /**
    * Method returns worker by identifier.
 * @throws DataAccessException 
    */
	public IWorker getWorkerByID(BigInteger id) throws DataAccessException;
    /**
    * Method returns worker by last name.
     * @throws DataAccessException 
    */
	public Collection<IWorker> getWorkersByName(String lname) throws DataAccessException;
    /**
    * Method returns worker by identifier.
     * @throws DataAccessException 
    */
	public Collection<IWorker> getWorkersByMgrID(BigInteger id, String sort) throws DataAccessException;
     /**
	 * method remove department from data source.
	 * @param id department's identifier for removing 
	 * @throws DataAccessException if got data source error.
	 */
	public void removeDept(BigInteger id) throws DataAccessException;
     /**
	 * method remove job from data source.
	 * @param id job's identifier for removing 
	 * @throws DataAccessException if got data source error.
	 */
	public void removeJob(BigInteger id) throws DataAccessException;
     /**
	 * method remove office from data source.
	 * @param id office's identifier for removing 
	 * @throws DataAccessException if got data source error.
	 */
	public void removeOffice(BigInteger id) throws DataAccessException;
     /**
	 * method remove worker from data source.
	 * @param id worker's identifier for removing 
	 * @throws DataAccessException if got data source error.
	 */
	public void removeWorker(BigInteger id) throws DataAccessException;
     /**
	 * method update department in the data source.
	 * @param id department's identifier for updating 
	 * @throws DataAccessException if got data source error.
	 */
	public void updateDept(IDept dept) throws DataAccessException;
    /**
	 * method update job in the data source.
	 * @param id job's identifier for updating 
	 * @throws DataAccessException if got data source error.
	 */
	public void updateJob(IJob job) throws DataAccessException;
     /**
	 * method update office in the data source.
	 * @param id office's identifier for updating 
	 * @throws DataAccessException if got data source error.
	 */
	public void updateOffice(IOffice off) throws DataAccessException;
     /**
	 * method update worker in the data source.
	 * @param id worker's identifier for updating 
	 * @throws DataAccessException if got data source error.
	 */
	public void updateWorker(IWorker worker) throws DataAccessException;
    public Collection<IWorker> getPath(BigInteger id) throws DataAccessException;
    public Collection<IWorker> filteringWorker(BigInteger id, HashMap<String,String> filters) throws DataAccessException;
}
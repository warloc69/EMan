package model;
import exception.*;
import java.util.*;
public interface IDataAccessor {
	/**
	 * method add department into data source.
	 * @param dept adder department
	 * @throws DataAccessException if got data source error.
	 */
	public addDept(IDept dept) throws DataAccessException;
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
	/**
	* return collection contain all departments
	*/
	public Collection<IDept> getAllDept();
	/**
	* return collection contain all jobs
	*/
	public Collection<IJob> getAllJobs();
	/**
	* return collection contain all offices
	*/
	public Collection<IOffice> getAllOffice();
	/**
	* return collection contain all workers
	*/
	public Collection<IWorker> getAllWorker();
	/**
    * Method returns department by identifier.
    */
	public IDept getDeptByID(BigInteger id);
	/**
    * Method returns department by title.
    */
	public IDept getDeptByTitle(String title);
    /**
    * Method returns job by identifier.
    */
	public IJob getJobByID(BigInteger id);
	/**
    * Method returns job by title.
    */
	public IJob getJobByTitle(String title);
	/**
    * Method returns office by identifier.
    */
	public IOffice getOfficeByID(BigInteger id);
	/**
    * Method returns office by title.
    */
	public IOffice getOfficeByTitle (String title);
   /**
    * Method returns worker by identifier.
    */
	public IWorker getWorkerByID(BigInteger id);
    /**
    * Method returns worker by last name.
    */
	public IWorker getWorkerByLastName(String lname);
    /**
    * Method returns worker by identifier.
    */
	public IWorker getWorkerByMgrID(BigInteger id);
     /**
	 * method remove department from data source.
	 * @param id department's identifier for removing 
	 * @throws DataAccessException if got data source error.
	 */
	public void removeDept(BigInteger id);
     /**
	 * method remove job from data source.
	 * @param id job's identifier for removing 
	 * @throws DataAccessException if got data source error.
	 */
	public void removeJob(BigInteger id);
     /**
	 * method remove office from data source.
	 * @param id office's identifier for removing 
	 * @throws DataAccessException if got data source error.
	 */
	public void removeOffice(BigInteger id);
     /**
	 * method remove worker from data source.
	 * @param id worker's identifier for removing 
	 * @throws DataAccessException if got data source error.
	 */
	public void removeWorker(BigInteger id);
     /**
	 * method update department in the data source.
	 * @param id department's identifier for updating 
	 * @throws DataAccessException if got data source error.
	 */
	public void updateDept(IDept dept);
    /**
	 * method update job in the data source.
	 * @param id job's identifier for updating 
	 * @throws DataAccessException if got data source error.
	 */
	public void updateJob(IJob job);
     /**
	 * method update office in the data source.
	 * @param id office's identifier for updating 
	 * @throws DataAccessException if got data source error.
	 */
	public void updateOffice(IOffice off);
     /**
	 * method update worker in the data source.
	 * @param id worker's identifier for updating 
	 * @throws DataAccessException if got data source error.
	 */
	public void updateWorker(IWorker worker);

}
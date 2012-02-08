package model;
import exception.*;
/**
* Interface presents access to the object IWorker
*/
public interface IWorker {
	/**
	* Method returns department's identifier.
	*/
	public BigInteger getDepartmentID();
	/**
	* method returns worker's first name.
	*/
	public String getFirstName();
	/**
    * Method returns job's identifier.
    */
	public BigInteger getJobID();
	/**
	* method returns worker's last name.
	*/
	public String getLastName();
    /**
    * Method returns manager's identifier.
    */
	public BigInteger getManagerID();
    /**
    * Method returns office's identifier.
    */
	public BigInteger getOfficeID();
	/**
	* method returns worker's salegrade.
	*/
	public int getSalegrade();
	/**
    * method sets department's identifier
	* @param id inserting identifier.
    * @throws DataValidateException if inserting data incorrect.
    */
	public void setDepartmentID(BigInteger id) throws DataValidateException;
	/**
	 * method inserts worker's first name.
	 * @param name is worker's first name.
	 * @throws DataValidateException if inserting data incorrect.
	 */
	public void setFirstName(String name) throws DataValidateException;
	/**
    * method sets worker's identifier
	* @param id inserting identifier.
    * @throws DataValidateException if inserting data incorrect.
    */
	public void setID(BigInteger id) throws DataValidateException;
	/**
    * method sets job's identifier
	* @param id inserting identifier.
    * @throws DataValidateException if inserting data incorrect.
    */
	public void setJobID(BigInteger id) throws DataValidateException;
    /**
	 * method inserts worker's last name.
	 * @param name is worker's last name.
	 * @throws DataValidateException if inserting data incorrect.
	 */
	public void setLastName(String name) throws DataValidateException;
    /**
    * method sets manager's identifier 
	* @param id inserting id.
    * @throws DataValidateException if inserting data incorrect.
    */
	public void setManagerID(BigInteger id) throws DataValidateException;
	/**
    * method sets office's identifier
	* @param id inserting id.
    * @throws DataValidateException if inserting data incorrect.
    */
	public void setOfficeID(BigInteger ID) throws DataValidateException;
	/**
	 * method sets worker's salegrade.
	 * @param sale is worker's salegrade.
	 * @throws DataValidateException if inserting data incorrect.
	 */
	public void setSalegrade(int sale) throws DataValidateException;

}
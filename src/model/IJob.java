package model;
import exception.*;
/**
* Interface presents access to the object IJob
*/
public interface IJob {
    /**
    * Method returns job's identifier.
    */
    public BigInteger getID();
    /**
    * returns job's title.
    */
    public String getTitle();
	/**
    * returns job's description.
    */
    public String getDescription();
    /**
    * method sets job's identifier
	* @param id inserting identifier.
    * @throws DataValidateException if inserting data incorrect.
    */
    public void setID(BigInteger id) throws DataValidateException;
    /**
    * method sets job's title. 
	* @param title inserting title
    * @throws DataValidateException if inserting data incorrect.
    */
    public void setTitle(String title) throws DataValidateException;
	/**
    * method sets job's description. 
	* @param desc inserting description
    * @throws DataValidateException if inserting data incorrect.
    */
    public void setDescription(String desc) throws DataValidateException;
}
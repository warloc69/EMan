package model;
import exception.*;
/**
* Interface presents access to the object IDept
*/
public interface IDept {
    /**
    * Method returns department's identifier.
    */
    public BigInteger getID();
    /**
    * returns department's title.
    */
    public String getTitle();
	/**
    * returns department's description.
    */
    public String getDescription();
    /**
    * method sets department's identifier
	* @param id inserting identifier.
    * @throws DataValidateException if inserting data incorrect.
    */
    public void setID(BigInteger id) throws DataValidateException;
    /**
    * method sets department's title. 
	* @param title inserting title
    * @throws DataValidateException if inserting data incorrect.
    */
    public void setTitle(String title) throws DataValidateException;
	/**
    * method sets department's description. 
	* @param desc inserting description
    * @throws DataValidateException if inserting data incorrect.
    */
    public void setDescription(String desc) throws DataValidateException;
}
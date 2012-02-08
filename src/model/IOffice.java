package model;
import exception.*;
/**
* Interface presents access to the object IOffice
*/
public interface IOffice {
    /**
    * Method returns address.
    */
    public String getAddress();
    /**
    * Method returns office's identifier.
    */
    public BigInteger getID();
    /**
    * Method returns manager's identifier.
    */
    public BigInteger getManagerID();
    /**
    * returns office's title.
    */
    public String getTitle();
    /**
    * method inserts into the object the address.
	* @param adr inserting address. 
    * @throws DataValidateException if inserting data incorrect.
    */
    public void setAddress(String adr) throws DataValidateException;
    /**
    * method sets office's identifier
	* @param id inserting id.
    * @throws DataValidateException if inserting data incorrect.
    */
    public void setID(BigInteger id) throws DataValidateException;
    /**
    * method sets manager's identifier 
	* @param id inserting id.
    * @throws DataValidateException if inserting data incorrect.
    */
    public void setManagerID(BigInteger id) throws DataValidateException;
    /**
    * method sets office's title. 
	* @param title inserting title
    * @throws DataValidateException if inserting data incorrect.
    */
    public void setTitle(String title) throws DataValidateException;
}
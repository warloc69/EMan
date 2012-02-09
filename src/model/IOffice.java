package model;
import java.math.*;
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
    */
    public void setAddress(String adr);
    /**
    * method sets office's identifier
	* @param id inserting id.
    */
    public void setID(BigInteger id);
    /**
    * method sets manager's identifier 
	* @param id inserting id.
    */
    public void setManagerID(BigInteger id) ;
    /**
    * method sets office's title. 
	* @param title inserting title
    */
    public void setTitle(String title);
}
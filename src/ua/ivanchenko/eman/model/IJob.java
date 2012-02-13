package ua.ivanchenko.eman.model;
import java.math.*;
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
    */
    public void setID(BigInteger id);
    /**
    * method sets job's title. 
    * @param title inserting title
    */
    public void setTitle(String title) ;
    /**
    * method sets job's description. 
    * @param desc inserting description
    */
    public void setDescription(String desc) ;
}
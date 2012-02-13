package ua.ivanchenko.eman.model;
import java.math.*;
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
    */
    public void setID(BigInteger id);
    /**
    * method sets department's title. 
    * @param title inserting title
    */
    public void setTitle(String title);
    /**
    * method sets department's description. 
    * @param desc inserting description
    */
    public void setDescription(String desc);
}
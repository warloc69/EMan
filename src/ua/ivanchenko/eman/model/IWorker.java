package ua.ivanchenko.eman.model;
import java.math.*;
/**
* Interface presents access to the object IWorker
*/
public interface IWorker {
    /**
     * method return worker id.
     */
    public BigInteger getID();
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
    public double getSalegrade();
    /**
    * method sets department's identifier
    * @param id inserting identifier.
    */
    public void setDepartmentID(BigInteger id);
    /**
     * method inserts worker's first name.
     * @param name is worker's first name.
     */
    public void setFirstName(String name);
    /**
    * method sets worker's identifier
    * @param id inserting identifier.
    */
    public void setID(BigInteger id);
    /**
    * method sets job's identifier
    * @param id inserting identifier.
    */
    public void setJobID(BigInteger id);
    /**
     * method inserts worker's last name.
     * @param name is worker's last name.
     */
    public void setLastName(String name);
    /**
    * method sets manager's identifier 
    * @param id inserting id.
    */
    public void setManagerID(BigInteger id);
    /**
    * method sets office's identifier
    * @param id inserting id.
    */
    public void setOfficeID(BigInteger id);
    /**
     * method sets worker's salegrade.
     * @param sale is worker's salegrade.
     */
    public void setSalegrade(double sale);

}
package ua.ivanchenko.eman.model.ejb.worker;
import java.math.*;
import java.rmi.RemoteException;

import javax.ejb.EJBObject;
/**
* Interface presents access to the object WorkerRemote
*/
public interface WorkerRemote extends EJBObject{
    /**
     * method return worker id.
     */
    public BigInteger getID() throws RemoteException;
    /**
    * Method returns department's identifier.
    */
    public BigInteger getDepartmentID() throws RemoteException;
    /**
    * method returns worker's first name.
    */
    public String getFirstName() throws RemoteException;
    /**
    * Method returns job's identifier.
    */
    public BigInteger getJobID() throws RemoteException;
    /**
    * method returns worker's last name.
    */
    public String getLastName() throws RemoteException;
    /**
    * Method returns manager's identifier.
    */
    public BigInteger getManagerID() throws RemoteException;
    /**
    * Method returns office's identifier.
    */
    public BigInteger getOfficeID() throws RemoteException;
    /**
    * method returns worker's salegrade.
    */
    public double getSalegrade() throws RemoteException;
    /**
    * method sets department's identifier
    * @param id inserting identifier.
    */
    public void setDepartmentID(BigInteger id) throws RemoteException;
    /**
     * method inserts worker's first name.
     * @param name is worker's first name.
     */
    public void setFirstName(String name) throws RemoteException;
    /**
    * method sets job's identifier
    * @param id inserting identifier.
    */
    public void setJobID(BigInteger id) throws RemoteException;
    /**
     * method inserts worker's last name.
     * @param name is worker's last name.
     */
    public void setLastName(String name) throws RemoteException;
    /**
    * method sets manager's identifier 
    * @param id inserting id.
    */
    public void setManagerID(BigInteger id) throws RemoteException;
    /**
    * method sets office's identifier
    * @param id inserting id.
    */
    public void setOfficeID(BigInteger id) throws RemoteException;
    /**
     * method sets worker's salegrade.
     * @param sale is worker's salegrade.
     */
    public void setSalegrade(double sale) throws RemoteException;
   
}
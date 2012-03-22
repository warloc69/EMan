package ua.ivanchenko.eman.model.ejb.job;
import java.math.*;
import java.rmi.RemoteException;

import javax.ejb.EJBObject;
/**
* Interface presents access to the object JobRemote
*/
public interface JobRemote  extends EJBObject{
    /**
    * Method returns job's identifier.
    */
    public BigInteger getID() throws RemoteException;
    /**
    * returns job's title.
    */
    public String getTitle() throws RemoteException;
    /**
    * returns job's description.
    */
    public String getDescription() throws RemoteException;
    
    /**
    * method sets job's title. 
    * @param title inserting title
    */
    public void setTitle(String title) throws RemoteException;
    /**
    * method sets job's description. 
    * @param desc inserting description
    */
    public void setDescription(String desc) throws RemoteException;
}
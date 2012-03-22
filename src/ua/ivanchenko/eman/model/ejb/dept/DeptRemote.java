package ua.ivanchenko.eman.model.ejb.dept;
import java.math.*;
import java.rmi.RemoteException;

import javax.ejb.EJBObject;
/**
* Interface presents access to the object DeptRemote
*/
public interface DeptRemote  extends EJBObject{
    /**
    * Method returns department's identifier.
    */
    public BigInteger getID() throws RemoteException;
    /**
    * returns department's title.
    */
    public String getTitle() throws RemoteException;
    /**
    * returns department's description.
    */
    public String getDescription() throws RemoteException;
    /**
    * method sets department's title. 
    * @param title inserting title
    */
    public void setTitle(String title) throws RemoteException;
    /**
    * method sets department's description. 
    * @param desc inserting description
    */
    public void setDescription(String desc) throws RemoteException;
}
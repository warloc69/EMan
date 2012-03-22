package ua.ivanchenko.eman.model.ejb.office;
import java.math.*;
import java.rmi.RemoteException;

import javax.ejb.EJBObject;
/**
* Interface presents access to the object OfficeRemote
*/
public interface OfficeRemote  extends EJBObject{
    /**
    * Method returns address.
    */
    public String getAddress() throws RemoteException;
    /**
    * Method returns office's identifier.
    */
    public BigInteger getID() throws RemoteException;
    /**
    * Method returns manager's identifier.
    */
    public BigInteger getManagerID() throws RemoteException;
    /**
    * returns office's title.
    */
    public String getTitle() throws RemoteException;
    /**
    * method inserts into the object the address.
    * @param adr inserting address. 
    */
    public void setAddress(String adr) throws RemoteException;
    
    /**
    * method sets manager's identifier 
    * @param id inserting id.
    */
    public void setManagerID(BigInteger id) throws RemoteException;
    /**
    * method sets office's title. 
    * @param title inserting title
    */
    public void setTitle(String title) throws RemoteException;
}
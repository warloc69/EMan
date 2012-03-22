package ua.ivanchenko.eman.model.ejb.office;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface OfficeHome extends EJBHome {
	public OfficeRemote create(String title,String address,BigInteger mgrid)
			throws RemoteException,   CreateException;
	public Collection<OfficeRemote> findAll(String sort) throws FinderException, RemoteException;
	public OfficeRemote findByPrimaryKey(BigInteger id)
			throws RemoteException,   FinderException;
	public OfficeRemote findByTitle(String title)
			throws RemoteException,   FinderException;

}

package ua.ivanchenko.eman.model.ejb.job;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface JobHome extends EJBHome {
	public JobRemote create(String title, String desc)
			throws RemoteException,   CreateException;
	public Collection<JobRemote> findAll(String sort) throws RemoteException, FinderException;
	public JobRemote findByPrimaryKey(BigInteger id)
			throws RemoteException,   FinderException;
	public JobRemote findByTitle(String title)
			throws RemoteException,   FinderException;

}

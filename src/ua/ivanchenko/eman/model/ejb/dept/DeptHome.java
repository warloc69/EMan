package ua.ivanchenko.eman.model.ejb.dept;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface DeptHome extends EJBHome {
	public DeptRemote create(String title, String desc)
			throws RemoteException,   CreateException;
	public Collection<DeptRemote> findAll(String sort) throws RemoteException, FinderException;
	public DeptRemote findByPrimaryKey(BigInteger id)
			throws RemoteException,   FinderException;
	public DeptRemote findByTitle(String title)
			throws RemoteException,   FinderException;
}

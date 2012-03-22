package ua.ivanchenko.eman.model.ejb.worker;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface WorkerHome extends EJBHome {
	public WorkerRemote create(String fname, String lname, BigInteger mgr_id, BigInteger office_id, BigInteger job_id, BigInteger dept_id, double sale)
			throws RemoteException,   CreateException;
	public Collection<WorkerRemote> findTopManager(String sort) throws RemoteException, FinderException;
	public WorkerRemote findByPrimaryKey(BigInteger id)
			throws RemoteException,   FinderException;
	public Collection<WorkerRemote> findByManagerID(BigInteger id)
			throws RemoteException,   FinderException;
	public Collection<WorkerRemote> findByName(String name)
			throws RemoteException,   FinderException;
	public Collection<WorkerRemote> findAll(String sort)
			throws RemoteException,   FinderException;
	public Collection<WorkerRemote> findPath(BigInteger id)
			throws RemoteException,   FinderException;
	public Collection<WorkerRemote> findFilteringWorker(BigInteger id, HashMap<String,String> filters)
			throws RemoteException,   FinderException;
}

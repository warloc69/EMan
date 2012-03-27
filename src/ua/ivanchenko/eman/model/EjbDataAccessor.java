/**
 * 
 */
package ua.ivanchenko.eman.model;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import ua.ivanchenko.eman.exceptions.DataAccessException;
import ua.ivanchenko.eman.model.ejb.dept.DeptHome;
import ua.ivanchenko.eman.model.ejb.dept.DeptRemote;
import ua.ivanchenko.eman.model.ejb.job.JobHome;
import ua.ivanchenko.eman.model.ejb.job.JobRemote;
import ua.ivanchenko.eman.model.ejb.office.OfficeHome;
import ua.ivanchenko.eman.model.ejb.office.OfficeRemote;
import ua.ivanchenko.eman.model.ejb.worker.WorkerHome;
import ua.ivanchenko.eman.model.ejb.worker.WorkerRemote;


public final class EjbDataAccessor  implements IDataAccessor {
	private Logger log = Logger.getLogger(EjbDataAccessor.class);
	private DeptHome getDeptHome() throws DataAccessException {
		try { 
			javax.naming.Context initial = new javax.naming.InitialContext();
	        Object objRef = initial.lookup("DeptBean");
	        return (DeptHome) javax.rmi.PortableRemoteObject.narrow(objRef, DeptHome.class); 
		} catch (NamingException e) {
			log.info("Can't lookup DeptBean",e);
			throw new  DataAccessException("Can't lookup DeptBean", e);
		}
	}
	private JobHome getJobHome()  throws DataAccessException {
		try {
			javax.naming.Context initial = new javax.naming.InitialContext();
	        Object objRef = initial.lookup("JobBean");
	        return (JobHome) javax.rmi.PortableRemoteObject.narrow(objRef, JobHome.class);
		} catch (NamingException e) {
			log.info("Can't lookup JobBean",e);
			throw new  DataAccessException("Can't lookup JobBean", e);
		}
	}
	private OfficeHome getOfficeHome()  throws DataAccessException {
		try {
			javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("OfficeBean");
            return (OfficeHome) javax.rmi.PortableRemoteObject.narrow(objRef, OfficeHome.class);
		}  catch (NamingException e) {
			log.info("Can't lookup OfficeBean",e);
			throw new  DataAccessException("Can't lookup OfficeBean", e);
		}
	}
	private WorkerHome getWorkerHome() throws DataAccessException {
		try {
			javax.naming.Context initial = new javax.naming.InitialContext();
	        Object objRef = initial.lookup("WorkerBean");
	        return (WorkerHome) javax.rmi.PortableRemoteObject.narrow(objRef, WorkerHome.class);	
		}  catch (NamingException e) {
			log.info("Can't lookup WorkerBean",e);
			throw new  DataAccessException("Can't lookup WorkerBean", e);
		}    
	}
    public EjbDataAccessor() {
    }
    /**
     * method adds department into data source.
     * @param dept adder department
     * @throws DataAccessException if got data source error.
     */
    public void addDept(IDept dept) throws DataAccessException {        
            try {
				getDeptHome().create(dept.getTitle(), dept.getDescription());
			} catch (RemoteException e) {
				log.info("(EJBDataAccessor.addDept) Can't get remote object",e);
				throw new  DataAccessException("(EJBDataAccessor.addDept) Can't get remote object", e);
			} catch (CreateException e) {
				log.info("(EJBDataAccessor.addDept) Can't create DeptBean object",e);
				throw new  DataAccessException("(EJBDataAccessor.addDept) Can't create DeptBean object", e);
			}
    }
    /**
     * method add job into data source.
     * @param job adder job
     * @throws DataAccessException if got data source error.
     */
    public void addJob(IJob job) throws DataAccessException {
    	try {
            getJobHome().create(job.getTitle(), job.getDescription());
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.addJob) Can't get remote object",e);
			throw new  DataAccessException("(EJBDataAccessor.addJob) Can't get remote object", e);
		} catch (CreateException e) {
			log.info("(EJBDataAccessor.addJob) Can't create JobBean object",e);
			throw new  DataAccessException("(EJBDataAccessor.addJob) Can't create JobBean object", e);
		}
    }
    /**
    * method add office into data source.
    * @param off adder office
    * @throws DataAccessException if got data source error.
    */
    public void addOffice(IOffice off) throws DataAccessException {
    	try {        	
            getOfficeHome().create(off.getTitle(), off.getAddress(), off.getManagerID());
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.addOffice) Can't get remote object",e);
			throw new  DataAccessException("(EJBDataAccessor.addOffice) Can't get remote object", e);
		} catch (CreateException e) {
			log.info("(EJBDataAccessor.addOffice) Can't create OfficeBean object",e);
			throw new  DataAccessException("(EJBDataAccessor.addOffice) Can't create OfficeBean object", e);
		}
    }
    /**
     * method add worker into data source.
     * @param worker adder worker
     * @throws DataAccessException if got data source error.
     */
    public void addWorker(IWorker worker) throws DataAccessException {
    	try {        	
            getWorkerHome().create(worker.getFirstName(), worker.getLastName(), worker.getManagerID(),worker.getOfficeID(),
            		worker.getJobID(),worker.getDepartmentID(),worker.getSalegrade());
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.addWorker) Can't get remote object",e);
			throw new  DataAccessException("(EJBDataAccessor.addWorker) Can't get remote object", e);
		} catch (CreateException e) {
			log.info("(EJBDataAccessor.addWorker) Can't create WorkerBean object",e);
			throw new  DataAccessException("(EJBDataAccessor.addWorker) Can't create WorkerBean object", e);
		}
    }
    /**
    * return collection contain all departments
    * @throws DataAccessException if got data source error.
    */
    public Collection<IDept> getAllDepts(String sort) throws DataAccessException {
              Collection<DeptRemote> deptRem;
			try {
				deptRem = getDeptHome().findAll(sort);	
				ArrayList<IDept> dep = new ArrayList<IDept>();
				if (deptRem != null) {
		            for(DeptRemote rem: deptRem) {
		               IDept tempDept = new Dept();
		               tempDept.setID(rem.getID());
		               tempDept.setTitle(rem.getTitle());
		               tempDept.setDescription(rem.getDescription());
		               dep.add(tempDept);
		            }
				}
	            return dep;
			} catch (RemoteException e) {
				log.info("(EJBDataAccessor.getAllDepts) Can't get remote object DeptBean",e);
				throw new  DataAccessException("(EJBDataAccessor.getAllDepts) Can't get remote object DeptBean", e);
			} catch (FinderException e) {
				log.info("(EJBDataAccessor.getAllDepts) Can't find remote object DeptBean",e);
				throw new  DataAccessException("(EJBDataAccessor.getAllDepts) Can't find remote object DeptBean", e);
			}
    }
    /**
    * return collection contain all jobs
    * @throws DataAccessException if got data source error.
    */
    public Collection<IJob> getAllJobs(String sort) throws DataAccessException {
    	try {
            Collection<JobRemote> jobRem = getJobHome().findAll(sort);
            ArrayList<IJob> job = new ArrayList<IJob>();
            if (jobRem != null) {
	            for(JobRemote rem: jobRem) {
	          	  IJob tempJob = new Job();
	          	  tempJob.setID(rem.getID());
	          	  tempJob.setTitle(rem.getTitle());
	          	  tempJob.setDescription(rem.getDescription());
	          	  job.add(tempJob);
	            }
            }
            return job;
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.addJob) Can't get remote object",e);
			throw new  DataAccessException("(EJBDataAccessor.addJob) Can't get remote object", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.addJob) Can't find remote object JobBean",e);
			throw new  DataAccessException("(EJBDataAccessor.addJob) Can't find remote object JobBean", e);
		}
    }
    /**
    * return collection contain all offices
    * @throws DataAccessException if got data source error.
    */
    public Collection<IOffice> getAllOffices(String sort) throws DataAccessException {
    	try {
            Collection<OfficeRemote> officeRem = getOfficeHome().findAll(sort);
            ArrayList<IOffice> office = new ArrayList<IOffice>();
            if (officeRem != null) {
	            for(OfficeRemote rem: officeRem) {
	          	  IOffice tempOffice = new Office();
	          	  tempOffice.setID(rem.getID());
	          	  tempOffice.setTitle(rem.getTitle());
	          	  tempOffice.setAddress(rem.getAddress());
	          	  tempOffice.setManagerID(rem.getManagerID());
	          	  office.add(tempOffice);
	            }
            }
            return office;
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.getAllOffices) Can't get remote object",e);
			throw new  DataAccessException("(EJBDataAccessor.getAllOffices) Can't get remote object", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.getAllOffices) Can't find remote object OfficeBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getAllOffices) Can't find remote object OfficeBean", e);
		}
    }
    /**
    * return collection contain all workers
    * @throws DataAccessException if got data source error.
    */
    public Collection<IWorker> getAllWorkers(String sort) throws DataAccessException {
    	try {
            Collection<WorkerRemote> workerRem = getWorkerHome().findAll(sort);
            ArrayList<IWorker> worker = new ArrayList<IWorker>();
            if (workerRem != null) {
	            for(WorkerRemote rem: workerRem) {
	          	  IWorker tempWorker = new Worker();
	          	  tempWorker.setID(rem.getID());
	          	  tempWorker.setFirstName(rem.getFirstName());
	          	  tempWorker.setLastName(rem.getLastName());
	          	  tempWorker.setDepartmentID(rem.getDepartmentID());
	          	  tempWorker.setJobID(rem.getJobID());
	          	  tempWorker.setManagerID(rem.getManagerID());
	          	  tempWorker.setOfficeID(rem.getOfficeID());
	          	  tempWorker.setSalegrade(rem.getSalegrade());
	          	  worker.add(tempWorker);
	            }
            }
            return worker;
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.getAllWorkers) Can't get remote object",e);
			throw new  DataAccessException("(EJBDataAccessor.getAllWorkers) Can't get remote object", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.getAllWorkers) Can't find remote object WorkerBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getAllWorkers) Can't find remote object WorkerBean", e);
		}
    }
    /**
    * Method returns department by identifier.
    * @throws DataAccessException if got data source error.
    */
    public IDept getDeptByID(BigInteger id) throws DataAccessException {
    	try {
            DeptRemote deptRem = getDeptHome().findByPrimaryKey(id);
          	if (deptRem == null) {
          		return null;
          	}
            IDept tempDept = new Dept();
          	tempDept.setID(deptRem.getID());
          	tempDept.setTitle(deptRem.getTitle());
          	tempDept.setDescription(deptRem.getDescription());
            return tempDept;
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.getDeptByID) Can't get remote object DeptBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getDeptByID) Can't get remote object DeptBean", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.getDeptByID) Can't find remote object DeptBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getDeptByID) Can't find remote object DeptBean", e);
		}
    }
    /**
    * Method returns department by title.
    * @throws DataAccessException if got data source error.
    */
    public IDept getDeptByTitle(String title) throws DataAccessException {
    	try {
            DeptRemote deptRem = getDeptHome().findByTitle(title);
            if (deptRem == null) {
          		return null;
          	}
          	IDept tempDept = new Dept();
          	tempDept.setID(deptRem.getID());
          	tempDept.setTitle(deptRem.getTitle());
          	tempDept.setDescription(deptRem.getDescription());
            return tempDept;
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.getDeptByTitle) Can't get remote object DeptBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getDeptByTitle) Can't get remote object DeptBean", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.getDeptByTitle) Can't find remote object DeptBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getDeptByTitle) Can't find remote object DeptBean", e);
		}
    }
    /**
    * Method returns job by identifier.
    * @throws DataAccessException if got data source error.
    */
    public IJob getJobByID(BigInteger id) throws DataAccessException {
    	try {
            JobRemote jobRem = getJobHome().findByPrimaryKey(id);
            if (jobRem == null) {
          		return null;
          	}
          	IJob tempJob = new Job();
          	tempJob.setID(jobRem.getID());
          	tempJob.setTitle(jobRem.getTitle());
          	tempJob.setDescription(jobRem.getDescription());
            return tempJob;
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.getJobByID) Can't get remote object JobBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getJobByID) Can't get remote object JobBean", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.getJobByID) Can't find remote object JobBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getJobByID) Can't find remote object JobBean", e);
		}
    }
    /**
    * Method returns job by title.
    * @throws DataAccessException if got data source error.
    */
    public IJob getJobByTitle(String title) throws DataAccessException {
    	try {
            JobRemote jobRem = getJobHome().findByTitle(title);
            if (jobRem == null) {
          		return null;
          	}
          	IJob tempJob = new Job();
          	tempJob.setID(jobRem.getID());
          	tempJob.setTitle(jobRem.getTitle());
          	tempJob.setDescription(jobRem.getDescription());
            return tempJob;
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.getJobByTitle) Can't get remote object JobBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getJobByTitle) Can't get remote object JobBean", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.getJobByTitle) Can't find remote object JobBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getJobByTitle) Can't find remote object JobBean", e);
		}
    }
    /**
    * Method returns office by identifier.
    * @throws DataAccessException if got data source error.
    */
    public IOffice getOfficeByID(BigInteger id) throws DataAccessException {
    	try {
            OfficeRemote officeRem = getOfficeHome().findByPrimaryKey(id);
            if (officeRem == null) {
          		return null;
          	}
          	IOffice tempOffice = new Office();
          	tempOffice.setID(officeRem.getID());
          	tempOffice.setTitle(officeRem.getTitle());
          	tempOffice.setManagerID(officeRem.getManagerID());
          	tempOffice.setAddress(officeRem.getAddress());
            return tempOffice;
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.getOfficeByID) Can't get remote object OfficeBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getOfficeByID) Can't get remote object OfficeBean", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.getOfficeByID) Can't find remote object OfficeBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getOfficeByID) Can't find remote object OfficeBean", e);
		}
    }
    /**
    * Method returns office by title.
    * @throws DataAccessException if got data source error.
    */
    public IOffice getOfficeByTitle(String title) throws DataAccessException {
    	try {
            OfficeRemote officeRem = getOfficeHome().findByTitle(title);
            if (officeRem == null) {
          		return null;
          	}
          	IOffice tempOffice = new Office();
          	tempOffice.setID(officeRem.getID());
          	tempOffice.setTitle(officeRem.getTitle());
          	tempOffice.setManagerID(officeRem.getManagerID());
          	tempOffice.setAddress(officeRem.getAddress());
            return tempOffice;
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.getOfficeByTitle) Can't get remote object OfficeBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getOfficeByTitle) Can't get remote object OfficeBean", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.getOfficeByTitle) Can't find remote object OfficeBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getOfficeByTitle) Can't find remote object OfficeBean", e);
		}
    }
    /**
    * Method returns worker by identifier.
    * @throws DataAccessException if got data source error.
    */
    public IWorker getWorkerByID(BigInteger id) throws DataAccessException {
    	try {
            WorkerRemote workerRem = getWorkerHome().findByPrimaryKey(id);
            if (workerRem == null) {
          		return null;
          	}
          	IWorker tempWorker = new Worker();
          	tempWorker.setID(workerRem.getID());
          	tempWorker.setManagerID(workerRem.getManagerID());
          	tempWorker.setFirstName(workerRem.getFirstName());
          	tempWorker.setLastName(workerRem.getLastName());
          	tempWorker.setJobID(workerRem.getJobID());
          	tempWorker.setDepartmentID(workerRem.getDepartmentID());
          	tempWorker.setOfficeID(workerRem.getOfficeID());
          	tempWorker.setSalegrade(workerRem.getSalegrade());
            return tempWorker;
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.getWorkerByID) Can't get remote object WorkerBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getWorkerByID) Can't get remote object WorkerBean", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.getWorkerByID) Can't find remote object WorkerBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getWorkerByID) Can't find remote object WorkerBean", e);
		}
    }
    /**
    * Method returns worker by last name.
    * @throws DataAccessException if got data source error.
    */
    public Collection<IWorker> getWorkersByName(String lname) throws DataAccessException {
    	try {
            Collection<WorkerRemote> workerRem = getWorkerHome().findByName(lname);
            if (workerRem == null) {
          		return null;
          	}
            ArrayList<IWorker> worker = new ArrayList<IWorker>();
            for(WorkerRemote rem: workerRem) {
          	  IWorker tempWorker = new Worker();
          	  tempWorker.setID(rem.getID());
          	  tempWorker.setFirstName(rem.getFirstName());
          	  tempWorker.setLastName(rem.getLastName());
          	  tempWorker.setDepartmentID(rem.getDepartmentID());
          	  tempWorker.setJobID(rem.getJobID());
          	  tempWorker.setManagerID(rem.getManagerID());
          	  tempWorker.setOfficeID(rem.getOfficeID());
          	  tempWorker.setSalegrade(rem.getSalegrade());
          	  worker.add(tempWorker);
            }
            return worker;
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.getWorkersByName) Can't get remote object WorkerBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getWorkersByName) Can't get remote object WorkerBean", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.getWorkersByName) Can't find remote object WorkerBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getWorkersByName) Can't find remote object WorkerBean", e);
		}
    }
    /**
    * Method returns worker by identifier.
    * @throws DataAccessException if got data source error.
    */
    public Collection<IWorker> getWorkersByMgrID(BigInteger id, String sort) throws DataAccessException {
    	try {
            Collection<WorkerRemote> workerRem = getWorkerHome().findByManagerID(id);
            if (workerRem == null) {
          		return null;
          	}
            ArrayList<IWorker> worker = new ArrayList<IWorker>();
            for(WorkerRemote rem: workerRem) {
          	  IWorker tempWorker = new Worker();
          	  tempWorker.setID(rem.getID());
          	  tempWorker.setFirstName(rem.getFirstName());
          	  tempWorker.setLastName(rem.getLastName());
          	  tempWorker.setDepartmentID(rem.getDepartmentID());
          	  tempWorker.setJobID(rem.getJobID());
          	  tempWorker.setManagerID(rem.getManagerID());
          	  tempWorker.setOfficeID(rem.getOfficeID());
          	  tempWorker.setSalegrade(rem.getSalegrade());
          	  worker.add(tempWorker);
            }
            return worker;
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.getWorkersByMgrID) Can't get remote object WorkerBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getWorkersByMgrID) Can't get remote object WorkerBean", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.getWorkersByMgrID) Can't find remote object WorkerBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getWorkersByMgrID) Can't find remote object WorkerBean", e);
		}
    }
    public Collection<IWorker> getTopManagers(String sort) throws DataAccessException {
    	try {
            Collection<WorkerRemote> workerRem = getWorkerHome().findTopManager(sort);
            if (workerRem == null) {
          		return null;
          	}
            ArrayList<IWorker> worker = new ArrayList<IWorker>();
            for(WorkerRemote rem: workerRem) {
          	  IWorker tempWorker = new Worker();
          	  tempWorker.setID(rem.getID());
          	  tempWorker.setFirstName(rem.getFirstName());
          	  tempWorker.setLastName(rem.getLastName());
          	  tempWorker.setDepartmentID(rem.getDepartmentID());
          	  tempWorker.setJobID(rem.getJobID());
          	  tempWorker.setManagerID(rem.getManagerID());
          	  tempWorker.setOfficeID(rem.getOfficeID());
          	  tempWorker.setSalegrade(rem.getSalegrade());
          	  worker.add(tempWorker);
            }
            return worker;
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.getTopManagers) Can't get remote object WorkerBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getTopManagers) Can't get remote object WorkerBean", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.getTopManagers) Can't find remote object WorkerBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getTopManagers) Can't find remote object WorkerBean", e);
		}
    }
    /**
    * method remove department from data source.
    * @param id department's identifier for removing 
    * @throws DataAccessException if got data source error.
    */
    public void removeDept(BigInteger id) throws DataAccessException {
    	try {
			DeptRemote dep = getDeptHome().findByPrimaryKey(id);
			if (dep != null) {
				dep.remove();
			}
		} catch (RemoveException e) {
			log.info("(EJBDataAccessor.removeDept) Can't remove object DeptBean",e);
			throw new  DataAccessException("(EJBDataAccessor.removeDept) Can't remove remote object DeptBean", e);	
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.removeDept) Can't get remote object DeptBean",e);
			throw new  DataAccessException("(EJBDataAccessor.removeDept) Can't get remote object DeptBean", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.removeDept) Can't find remote object DeptBean",e);
			throw new  DataAccessException("(EJBDataAccessor.removeDept) Can't find remote object DeptBean", e);
		}
    }
    /**
    * method remove job from data source.
    * @param id job's identifier for removing 
    * @throws DataAccessException if got data source error.
    */
    public void removeJob(BigInteger id) throws DataAccessException {
    	try {
           JobRemote job =  getJobHome().findByPrimaryKey(id);
           if (job != null) {
        	   job.remove();
           }
    	} catch (RemoveException e) {
			log.info("(EJBDataAccessor.removeJob) Can't remove object JobBean",e);
			throw new  DataAccessException("(EJBDataAccessor.removeJob) Can't remove remote object JobBean", e);	
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.removeJob) Can't get remote object JobBean",e);
			throw new  DataAccessException("(EJBDataAccessor.removeJob) Can't get remote object JobBean", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.removeJob) Can't find remote object JobBean",e);
			throw new  DataAccessException("(EJBDataAccessor.removeJob) Can't find remote object JobBean", e);
		}
    }
    /**
    * method remove office from data source.
    * @param id office's identifier for removing 
    * @throws DataAccessException if got data source error.
    */
    public void removeOffice(BigInteger id) throws DataAccessException {
    	try {
            OfficeRemote office = getOfficeHome().findByPrimaryKey(id);
            if (office != null) {
            	office.remove();
            }
    	} catch (RemoveException e) {
			log.info("(EJBDataAccessor.removeOffice) Can't remove object OfficeBean",e);
			throw new  DataAccessException("(EJBDataAccessor.removeOffice) Can't remove remote object OfficeBean", e);	
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.removeOffice) Can't get remote object OfficeBean",e);
			throw new  DataAccessException("(EJBDataAccessor.removeOffice) Can't get remote object OfficeBean", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.removeOffice) Can't find remote object OfficeBean",e);
			throw new  DataAccessException("(EJBDataAccessor.removeOffice) Can't find remote object OfficeBean", e);
		}
    }
    /**
    * method remove worker from data source.
    * @param id worker's identifier for removing 
    * @throws DataAccessException if got data source error.
    */
    public void removeWorker(BigInteger id) throws DataAccessException {
    	try {
           WorkerRemote worker =  getWorkerHome().findByPrimaryKey(id);
           if (worker != null) {
        	   worker.remove();
           }
    	} catch (RemoveException e) {
			log.info("(EJBDataAccessor.removeWorker) Can't remove object WorkerBean",e);
			throw new  DataAccessException("(EJBDataAccessor.removeWorker) Can't remove remote object WorkerBean", e);	
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.removeWorker) Can't get remote object WorkerBean",e);
			throw new  DataAccessException("(EJBDataAccessor.removeWorker) Can't get remote object WorkerBean", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.removeWorker) Can't find remote object WorkerBean",e);
			throw new  DataAccessException("(EJBDataAccessor.removeWorker) Can't find remote object WorkerBean", e);
		}
    }
    /**
    * method update department in the data source.
    * @param id department's identifier for updating 
    * @throws DataAccessException if got data source error.
    */
    public void updateDept(IDept dept) throws DataAccessException {
    	try {
            DeptRemote deptRem = getDeptHome().findByPrimaryKey(dept.getID());
            if (deptRem == null) {
            	return;
            }
            deptRem.setTitle(dept.getTitle());
            deptRem.setDescription(dept.getDescription());
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.updateDept) Can't get remote object DeptBean",e);
			throw new  DataAccessException("(EJBDataAccessor.updateDept) Can't get remote object DeptBean", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.updateDept) Can't find remote object DeptBean",e);
			throw new  DataAccessException("(EJBDataAccessor.updateDept) Can't find remote object DeptBean", e);
		}
    }
    /**
     * method update job in the data source.
     * @param id job's identifier for updating 
     * @throws DataAccessException if got data source error.
     */
    public void updateJob(IJob job) throws DataAccessException {
    	try {
            JobRemote jobRem = getJobHome().findByPrimaryKey(job.getID());
            if (jobRem == null) {
            	return;
            }
            jobRem.setTitle(job.getTitle());
            jobRem.setDescription(job.getDescription());
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.updateJob) Can't get remote object JobBean",e);
			throw new  DataAccessException("(EJBDataAccessor.updateJob) Can't get remote object JobBean", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.updateJob) Can't find remote object JobBean",e);
			throw new  DataAccessException("(EJBDataAccessor.updateJob) Can't find remote object JobBean", e);
		}
    }
    /**
     * method update office in the data source.
     * @param id office's identifier for updating 
     * @throws DataAccessException if got data source error.
     */
    public void updateOffice(IOffice off) throws DataAccessException {
    	try {
            OfficeRemote officeRem = getOfficeHome().findByPrimaryKey(off.getID());
            if (officeRem == null) {
            	return;
            }
            officeRem.setTitle(off.getTitle());
            officeRem.setAddress(off.getAddress());
            officeRem.setManagerID(off.getManagerID());
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.updateOffice) Can't get remote object OfficeBean",e);
			throw new  DataAccessException("(EJBDataAccessor.updateOffice) Can't get remote object OfficeBean", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.updateOffice) Can't find remote object OfficeBean",e);
			throw new  DataAccessException("(EJBDataAccessor.updateOffice) Can't find remote object OfficeBean", e);
		}
    }
    /**
     * method update worker in the data source.
     * @param id worker's identifier for updating 
     * @throws DataAccessException if got data source error.
     */
    public void updateWorker(IWorker worker) throws DataAccessException {
    	try {
            WorkerRemote workerRem = getWorkerHome().findByPrimaryKey(worker.getID());
            if (workerRem == null) {
            	return;
            }
            workerRem.setFirstName(worker.getFirstName());
            workerRem.setLastName(worker.getLastName());
            workerRem.setDepartmentID(worker.getDepartmentID());
            workerRem.setJobID(worker.getJobID());
            workerRem.setManagerID(worker.getManagerID());
            workerRem.setOfficeID(worker.getOfficeID());
            workerRem.setSalegrade(worker.getSalegrade());
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.updateWorker) Can't get remote object WorkerBean",e);
			throw new  DataAccessException("(EJBDataAccessor.updateWorker) Can't get remote object WorkerBean", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.updateWorker) Can't find remote object WorkerBean",e);
			throw new  DataAccessException("(EJBDataAccessor.updateWorker) Can't find remote object WorkerBean", e);
		}
    }
    public Collection<IWorker> getPath(BigInteger id) throws DataAccessException {
    	try {
            Collection<WorkerRemote> workerRem = getWorkerHome().findPath(id);
            
            LinkedList<IWorker> worker = new LinkedList<IWorker>();
            if (workerRem != null) { 
	            for(WorkerRemote rem: workerRem) {
	          	  IWorker tempWorker = new Worker();
	          	  tempWorker.setID(rem.getID());
	          	  tempWorker.setLastName(rem.getLastName());
	          	  worker.add(tempWorker);
	            }
            }
            return worker;
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.getPath) Can't get remote object WorkerBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getPath) Can't get remote object WorkerBean", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.getPath) Can't find remote object WorkerBean",e);
			throw new  DataAccessException("(EJBDataAccessor.getPath) Can't find remote object WorkerBean", e);
		}
    }
    public Collection<IWorker> filteringWorker(BigInteger id, HashMap<String,String> filters) throws DataAccessException {
    	try {
            Collection<WorkerRemote> workerRem = getWorkerHome().findFilteringWorker(id, filters);
            LinkedList<IWorker> worker = new LinkedList<IWorker>();
            if (workerRem !=  null) {
	            for(WorkerRemote rem: workerRem) {
	          	  IWorker tempWorker = new Worker();
	          	  tempWorker.setID(rem.getID());
	          	  tempWorker.setFirstName(rem.getFirstName());
	          	  tempWorker.setLastName(rem.getLastName());
	          	  tempWorker.setDepartmentID(rem.getDepartmentID());
	          	  tempWorker.setJobID(rem.getJobID());
	          	  tempWorker.setManagerID(rem.getManagerID());
	          	  tempWorker.setOfficeID(rem.getOfficeID());
	          	  tempWorker.setSalegrade(rem.getSalegrade());
	          	  worker.add(tempWorker);
	            }
            }
            return worker;
    	} catch (RemoteException e) {
			log.info("(EJBDataAccessor.filteringWorker) Can't get remote object WorkerBean",e);
			throw new  DataAccessException("(EJBDataAccessor.filteringWorker) Can't get remote object WorkerBean", e);
		} catch (FinderException e) {
			log.info("(EJBDataAccessor.filteringWorker) Can't find remote object WorkerBean",e);
			throw new  DataAccessException("(EJBDataAccessor.filteringWorker) Can't find remote object WorkerBean", e);
		}
   }
}
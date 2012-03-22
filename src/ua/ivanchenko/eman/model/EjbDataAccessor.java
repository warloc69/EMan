/**
 * 
 */
package ua.ivanchenko.eman.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

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
    public EjbDataAccessor() {
    }
    /**
     * method adds department into data source.
     * @param dept adder department
     * @throws DataAccessException if got data source error.
     */
    public void addDept(IDept dept) throws DataAccessException {
        try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("DeptBean");
            DeptHome deptHome = (DeptHome) javax.rmi.PortableRemoteObject.narrow(objRef, DeptHome.class);
            deptHome.create(dept.getTitle(), dept.getDescription());
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    /**
     * method add job into data source.
     * @param job adder job
     * @throws DataAccessException if got data source error.
     */
    public void addJob(IJob job) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("JobBean");
            JobHome jobHome = (JobHome) javax.rmi.PortableRemoteObject.narrow(objRef, JobHome.class);
            jobHome.create(job.getTitle(), job.getDescription());
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    /**
    * method add office into data source.
    * @param off adder office
    * @throws DataAccessException if got data source error.
    */
    public void addOffice(IOffice off) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("OfficeBean");
            OfficeHome officeHome = (OfficeHome) javax.rmi.PortableRemoteObject.narrow(objRef, OfficeHome.class);
            officeHome.create(off.getTitle(), off.getAddress(), off.getManagerID());
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    /**
     * method add worker into data source.
     * @param worker adder worker
     * @throws DataAccessException if got data source error.
     */
    public void addWorker(IWorker worker) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("WorkerBean");
            WorkerHome workerHome = (WorkerHome) javax.rmi.PortableRemoteObject.narrow(objRef, WorkerHome.class);
            workerHome.create(worker.getFirstName(), worker.getLastName(), worker.getManagerID(),worker.getOfficeID(),
            		worker.getJobID(),worker.getDepartmentID(),worker.getSalegrade());
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    /**
    * return collection contain all departments
    * @throws DataAccessException if got data source error.
    */
    public Collection<IDept> getAllDepts(String sort) throws DataAccessException {
    	  try {
          	  javax.naming.Context initial = new javax.naming.InitialContext();
              Object objRef = initial.lookup("DeptBean");
              DeptHome deptHome = (DeptHome) javax.rmi.PortableRemoteObject.narrow(objRef, DeptHome.class);
              Collection<DeptRemote> deptRem = deptHome.findAll(sort);
              ArrayList<IDept> dep = new ArrayList<IDept>();
              for(DeptRemote rem: deptRem) {
            	  IDept tempDept = new Dept();
            	  tempDept.setID(rem.getID());
            	  tempDept.setTitle(rem.getTitle());
            	  tempDept.setDescription(rem.getDescription());
            	  dep.add(tempDept);
              }
              return dep;
          } catch (Exception e) {
          	log.error(e);
              throw new DataAccessException(e);
          }
    }
    /**
    * return collection contain all jobs
    * @throws DataAccessException if got data source error.
    */
    public Collection<IJob> getAllJobs(String sort) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("JobBean");
            JobHome jobHome = (JobHome) javax.rmi.PortableRemoteObject.narrow(objRef, JobHome.class);
            Collection<JobRemote> jobRem = jobHome.findAll(sort);
            ArrayList<IJob> job = new ArrayList<IJob>();
            for(JobRemote rem: jobRem) {
          	  IJob tempJob = new Job();
          	  tempJob.setID(rem.getID());
          	  tempJob.setTitle(rem.getTitle());
          	  tempJob.setDescription(rem.getDescription());
          	  job.add(tempJob);
            }
            return job;
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    /**
    * return collection contain all offices
    * @throws DataAccessException if got data source error.
    */
    public Collection<IOffice> getAllOffices(String sort) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("OfficeBean");
            OfficeHome officeHome = (OfficeHome) javax.rmi.PortableRemoteObject.narrow(objRef, OfficeHome.class);
            Collection<OfficeRemote> officeRem = officeHome.findAll(sort);
            ArrayList<IOffice> office = new ArrayList<IOffice>();
            for(OfficeRemote rem: officeRem) {
          	  IOffice tempOffice = new Office();
          	  tempOffice.setID(rem.getID());
          	  tempOffice.setTitle(rem.getTitle());
          	  tempOffice.setAddress(rem.getAddress());
          	  tempOffice.setManagerID(rem.getManagerID());
          	  office.add(tempOffice);
            }
            return office;
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    /**
    * return collection contain all workers
    * @throws DataAccessException if got data source error.
    */
    public Collection<IWorker> getAllWorkers(String sort) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("WorkerBean");
            WorkerHome workerHome = (WorkerHome) javax.rmi.PortableRemoteObject.narrow(objRef, WorkerHome.class);  
            Collection<WorkerRemote> workerRem = workerHome.findAll(sort);
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
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    /**
    * Method returns department by identifier.
    * @throws DataAccessException if got data source error.
    */
    public IDept getDeptByID(BigInteger id) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("DeptBean");
            DeptHome deptHome = (DeptHome) javax.rmi.PortableRemoteObject.narrow(objRef, DeptHome.class);
            DeptRemote deptRem = deptHome.findByPrimaryKey(id);
          	IDept tempDept = new Dept();
          	tempDept.setID(deptRem.getID());
          	tempDept.setTitle(deptRem.getTitle());
          	tempDept.setDescription(deptRem.getDescription());
            return tempDept;
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    /**
    * Method returns department by title.
    * @throws DataAccessException if got data source error.
    */
    public IDept getDeptByTitle(String title) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("DeptBean");
            DeptHome deptHome = (DeptHome) javax.rmi.PortableRemoteObject.narrow(objRef, DeptHome.class);
            DeptRemote deptRem = deptHome.findByTitle(title);
          	IDept tempDept = new Dept();
          	tempDept.setID(deptRem.getID());
          	tempDept.setTitle(deptRem.getTitle());
          	tempDept.setDescription(deptRem.getDescription());
            return tempDept;
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    /**
    * Method returns job by identifier.
    * @throws DataAccessException if got data source error.
    */
    public IJob getJobByID(BigInteger id) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("JobBean");
            JobHome jobHome = (JobHome) javax.rmi.PortableRemoteObject.narrow(objRef, JobHome.class);
            JobRemote jobRem = jobHome.findByPrimaryKey(id);
          	IJob tempJob = new Job();
          	tempJob.setID(jobRem.getID());
          	tempJob.setTitle(jobRem.getTitle());
          	tempJob.setDescription(jobRem.getDescription());
            return tempJob;
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    /**
    * Method returns job by title.
    * @throws DataAccessException if got data source error.
    */
    public IJob getJobByTitle(String title) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("JobBean");
            JobHome jobHome = (JobHome) javax.rmi.PortableRemoteObject.narrow(objRef, JobHome.class);
            JobRemote jobRem = jobHome.findByTitle(title);
          	IJob tempJob = new Job();
          	tempJob.setID(jobRem.getID());
          	tempJob.setTitle(jobRem.getTitle());
          	tempJob.setDescription(jobRem.getDescription());
            return tempJob;
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    /**
    * Method returns office by identifier.
    * @throws DataAccessException if got data source error.
    */
    public IOffice getOfficeByID(BigInteger id) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("OfficeBean");
            OfficeHome officeHome = (OfficeHome) javax.rmi.PortableRemoteObject.narrow(objRef, OfficeHome.class);
            OfficeRemote officeRem = officeHome.findByPrimaryKey(id);
          	IOffice tempOffice = new Office();
          	tempOffice.setID(officeRem.getID());
          	tempOffice.setTitle(officeRem.getTitle());
          	tempOffice.setManagerID(officeRem.getManagerID());
          	tempOffice.setAddress(officeRem.getAddress());
            return tempOffice;
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    /**
    * Method returns office by title.
    * @throws DataAccessException if got data source error.
    */
    public IOffice getOfficeByTitle(String title) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("OfficeBean");
            OfficeHome officeHome = (OfficeHome) javax.rmi.PortableRemoteObject.narrow(objRef, OfficeHome.class);
            OfficeRemote officeRem = officeHome.findByTitle(title);
          	IOffice tempOffice = new Office();
          	tempOffice.setID(officeRem.getID());
          	tempOffice.setTitle(officeRem.getTitle());
          	tempOffice.setManagerID(officeRem.getManagerID());
          	tempOffice.setAddress(officeRem.getAddress());
            return tempOffice;
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    /**
    * Method returns worker by identifier.
    * @throws DataAccessException if got data source error.
    */
    public IWorker getWorkerByID(BigInteger id) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("WorkerBean");
            WorkerHome workerHome = (WorkerHome) javax.rmi.PortableRemoteObject.narrow(objRef, WorkerHome.class);
            WorkerRemote workerRem = workerHome.findByPrimaryKey(id);
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
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    /**
    * Method returns worker by last name.
    * @throws DataAccessException if got data source error.
    */
    public Collection<IWorker> getWorkersByName(String lname) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("WorkerBean");
            WorkerHome workerHome = (WorkerHome) javax.rmi.PortableRemoteObject.narrow(objRef, WorkerHome.class);
            Collection<WorkerRemote> workerRem = workerHome.findByName(lname);
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
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    /**
    * Method returns worker by identifier.
    * @throws DataAccessException if got data source error.
    */
    public Collection<IWorker> getWorkersByMgrID(BigInteger id, String sort) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("WorkerBean");
            WorkerHome workerHome = (WorkerHome) javax.rmi.PortableRemoteObject.narrow(objRef, WorkerHome.class);
            Collection<WorkerRemote> workerRem = workerHome.findByManagerID(id);
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
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    public Collection<IWorker> getTopManagers(String sort) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("WorkerBean");
            WorkerHome workerHome = (WorkerHome) javax.rmi.PortableRemoteObject.narrow(objRef, WorkerHome.class);
            Collection<WorkerRemote> workerRem = workerHome.findTopManager(sort);
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
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    /**
    * method remove department from data source.
    * @param id department's identifier for removing 
    * @throws DataAccessException if got data source error.
    */
    public void removeDept(BigInteger id) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("DeptBean");
            DeptHome deptHome = (DeptHome) javax.rmi.PortableRemoteObject.narrow(objRef, DeptHome.class);
            deptHome.findByPrimaryKey(id).remove();
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    /**
    * method remove job from data source.
    * @param id job's identifier for removing 
    * @throws DataAccessException if got data source error.
    */
    public void removeJob(BigInteger id) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("JobBean");
            JobHome jobHome = (JobHome) javax.rmi.PortableRemoteObject.narrow(objRef, JobHome.class);
            jobHome.findByPrimaryKey(id).remove();
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }

    }
    /**
    * method remove office from data source.
    * @param id office's identifier for removing 
    * @throws DataAccessException if got data source error.
    */
    public void removeOffice(BigInteger id) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("OfficeBean");
            OfficeHome officeHome = (OfficeHome) javax.rmi.PortableRemoteObject.narrow(objRef, OfficeHome.class);
            officeHome.findByPrimaryKey(id).remove();
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    /**
    * method remove worker from data source.
    * @param id worker's identifier for removing 
    * @throws DataAccessException if got data source error.
    */
    public void removeWorker(BigInteger id) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("WorkerBean");
            WorkerHome workerHome = (WorkerHome) javax.rmi.PortableRemoteObject.narrow(objRef, WorkerHome.class);
            workerHome.findByPrimaryKey(id).remove();
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    /**
    * method update department in the data source.
    * @param id department's identifier for updating 
    * @throws DataAccessException if got data source error.
    */
    public void updateDept(IDept dept) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("DeptBean");
            DeptHome deptHome = (DeptHome) javax.rmi.PortableRemoteObject.narrow(objRef, DeptHome.class);
            DeptRemote deptRem = deptHome.findByPrimaryKey(dept.getID());
            deptRem.setTitle(dept.getTitle());
            deptRem.setDescription(dept.getDescription());
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    /**
     * method update job in the data source.
     * @param id job's identifier for updating 
     * @throws DataAccessException if got data source error.
     */
    public void updateJob(IJob job) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("JobBean");
            JobHome jobHome = (JobHome) javax.rmi.PortableRemoteObject.narrow(objRef, JobHome.class);
            JobRemote jobRem = jobHome.findByPrimaryKey(job.getID());
            jobRem.setTitle(job.getTitle());
            jobRem.setDescription(job.getDescription());
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    /**
     * method update office in the data source.
     * @param id office's identifier for updating 
     * @throws DataAccessException if got data source error.
     */
    public void updateOffice(IOffice off) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("OfficeBean");
            OfficeHome officeHome = (OfficeHome) javax.rmi.PortableRemoteObject.narrow(objRef, OfficeHome.class);
            OfficeRemote officeRem = officeHome.findByPrimaryKey(off.getID());
            officeRem.setTitle(off.getTitle());
            officeRem.setAddress(off.getAddress());
            officeRem.setManagerID(off.getManagerID());
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    /**
     * method update worker in the data source.
     * @param id worker's identifier for updating 
     * @throws DataAccessException if got data source error.
     */
    public void updateWorker(IWorker worker) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("WorkerBean");
            WorkerHome workerHome = (WorkerHome) javax.rmi.PortableRemoteObject.narrow(objRef, WorkerHome.class);
            WorkerRemote workerRem = workerHome.findByPrimaryKey(worker.getID());
            workerRem.setFirstName(worker.getFirstName());
            workerRem.setLastName(worker.getLastName());
            workerRem.setDepartmentID(worker.getDepartmentID());
            workerRem.setJobID(worker.getJobID());
            workerRem.setManagerID(worker.getManagerID());
            workerRem.setOfficeID(worker.getOfficeID());
            workerRem.setSalegrade(worker.getSalegrade());
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    public Collection<IWorker> getPath(BigInteger id) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("WorkerBean");
            WorkerHome workerHome = (WorkerHome) javax.rmi.PortableRemoteObject.narrow(objRef, WorkerHome.class);
            Collection<WorkerRemote> workerRem = workerHome.findPath(id);
            LinkedList<IWorker> worker = new LinkedList<IWorker>();
            for(WorkerRemote rem: workerRem) {
          	  IWorker tempWorker = new Worker();
          	  tempWorker.setID(rem.getID());
          	  tempWorker.setLastName(rem.getLastName());
          	  worker.add(tempWorker);
            }
            return worker;
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
    }
    public Collection<IWorker> filteringWorker(BigInteger id, HashMap<String,String> filters) throws DataAccessException {
    	try {
        	javax.naming.Context initial = new javax.naming.InitialContext();
            Object objRef = initial.lookup("WorkerBean");
            WorkerHome workerHome = (WorkerHome) javax.rmi.PortableRemoteObject.narrow(objRef, WorkerHome.class);
            Collection<WorkerRemote> workerRem = workerHome.findFilteringWorker(id, filters);
            LinkedList<IWorker> worker = new LinkedList<IWorker>();
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
        } catch (Exception e) {
        	log.error(e);
            throw new DataAccessException(e);
        }
   }
}
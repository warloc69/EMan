package ua.ivanchenko.eman.processors;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.ivanchenko.eman.exceptions.ConfigLoaderException;
import ua.ivanchenko.eman.exceptions.DataAccessException;
import ua.ivanchenko.eman.model.IDataAccessor;
import ua.ivanchenko.eman.model.IDept;
import ua.ivanchenko.eman.model.IJob;
import ua.ivanchenko.eman.model.IOffice;
import ua.ivanchenko.eman.model.IWorker;
public class ViewWorkerActionProcessor implements ActionProcessor {
	private Logger log = Logger.getLogger("emanlogger");
	public static HashMap<BigInteger,String> getHashMapWorkers(HashSet<IWorker> set) {
		HashMap<BigInteger,String> workersname = new  HashMap<BigInteger,String>();
		for (IWorker worker: set) {
			if (worker != null) {
				workersname.put(worker.getID(),worker.getLastName());
			}
		}
		return workersname;
	}
    public void process(HttpServletRequest req, HttpServletResponse resp, IDataAccessor access) throws DataAccessException, ConfigLoaderException {
        if("view_worker".equals(req.getParameter("action_id"))) {
                try {
                	log.info("ViewWorkerActionPorcessor get id {id:"+req.getParameter("id")+"}");
                	Collection<IWorker> workers  = access.getAllWorkers();
                    IWorker worker = access.getWorkerByID(new BigInteger(req.getParameter("id")));
                    HashSet<IWorker> managersname = new  HashSet<IWorker>();
                    for(IWorker worker1: workers) {
                		if (worker1.getManagerID() != null)
                			managersname.add(access.getWorkerByID(worker1.getManagerID()));             	
                	}
                    req.getSession().setAttribute("w_worker", worker);
                    req.getSession().setAttribute("w_workers", null);
                    req.getSession().setAttribute("w_manager", getHashMapWorkers(managersname));
                    try {
                    	 log.info("redirect to index.jsp?action_id=view_worker");
                    	 resp.sendRedirect("index.jsp?action_id=view_worker&id=" + req.getParameter("id"));
                    } catch (IOException e) {
                        log.error("can't redirect on the showworkers.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                } return;
         } else if (req.getParameter("last_name") != null) {
                try {
                	log.info("ViewWorkerActionPorcessor get last_name {last_name:"+req.getParameter("last_name")+"}");
                    Collection<IWorker> workers = access.getWorkersByLastName(req.getParameter("last_name"));
                    req.setAttribute("w_workers", workers);
                    try {
                    	log.info("redirect to showworkers.jsp");
                        resp.sendRedirect("showworkers.jsp");
                    } catch (IOException e) {
                        log.error("can't redirect on the showworkers.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                } return;
            } else if("view_top_manager".equals(req.getParameter("action_id"))) {
                try {
                	log.info(" td ViewWorkerActionPorcessor get mgr_id { id:"+req.getParameter("id")+"}");
                	Collection<IWorker> workers = null;
                	Collection<IWorker> allworkers = access.getAllWorkers();
                	IWorker wor = null;
                	if (req.getParameter("id") != null) {
                		wor = access.getWorkerByID(new BigInteger(req.getParameter("id")));
                	}
                	if (req.getParameter("id") == null) {
                		workers = access.getTopManagers();
                	} else {
                		workers = access.getWorkersByMgrID(new BigInteger(req.getParameter("id")));
                	}
                    HashMap<BigInteger,String> deptsname = new  HashMap<BigInteger,String>();
                    HashMap<BigInteger,String> managersname = new  HashMap<BigInteger,String>();
                	HashMap<BigInteger,String> officesname = new  HashMap<BigInteger,String>();
                	HashMap<BigInteger,String> jobsname = new HashMap<BigInteger,String>();
                	Collection<IJob> jobs= access.getAllJobs();
                	for(IJob job: jobs){
                		jobsname.put(job.getID(),job.getTitle());
                	}
                	Collection<IOffice> offices= access.getAllOffices();
                	for(IOffice office: offices){
                		officesname.put(office.getID(),office.getTitle());
                	}
                	Collection<IDept> depts= access.getAllDepts();
                	for(IDept dept: depts){
                		deptsname.put(dept.getID(),dept.getTitle());
                	}
                	for(IWorker worker: allworkers) {
                			managersname.put(worker.getID(),worker.getLastName());             	
                	}
                	req.getSession().setAttribute("wor", wor);
                	req.getSession().setAttribute("w_worker", null);
                    req.getSession().setAttribute("w_workers", workers);
                    req.getSession().setAttribute("w_departments", deptsname);
                    req.getSession().setAttribute("w_offices",officesname);
                    req.getSession().setAttribute("w_jobs", jobsname);
                    log.info("ViewWorkerActionPorcessor get all worker {managersname :" + managersname+"}");
                    req.getSession().setAttribute("w_manager",managersname);
                    try {
                    	if (req.getParameter("tab") == null){
	                    	log.info("redirect to showworkers.jsp");
	                    	if (req.getParameter("select") == null) {
	                    		resp.sendRedirect("index.jsp?action_id=view_top_manager&id=" + req.getParameter("id"));
	                    	} else {
	                    		resp.sendRedirect("index.jsp?action_id=view_top_manager&select=true&id=" + req.getParameter("id"));
	                    	}
	                    	
                    	} else {
                    		log.info("redirect to showworkers.jsp");
	                        resp.sendRedirect("index.jsp?action_id=view_top_manager&tab=details&id=" + req.getParameter("id"));
                    	} 
                    } catch (IOException e) {
                        log.error("can't redirect on the showworkers.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                } return;
            } else {
                try {
                	log.info("ViewWorkerActionPorcessor get all worker");
                	Collection<IWorker> workers  = access.getAllWorkers();
                	HashMap<BigInteger,String> deptsname = new  HashMap<BigInteger,String>();
                	HashSet<IWorker> managersname = new  HashSet<IWorker>();
                	HashMap<BigInteger,String> officesname = new  HashMap<BigInteger,String>();
                	HashMap<BigInteger,String> jobsname = new HashMap<BigInteger,String>();
                	Collection<IJob> jobs= access.getAllJobs();
                	for(IJob job: jobs){
                		jobsname.put(job.getID(),job.getTitle());
                	}
                	Collection<IOffice> offices= access.getAllOffices();
                	for(IOffice office: offices){
                		officesname.put(office.getID(),office.getTitle());
                	}
                	Collection<IDept> depts= access.getAllDepts();
                	for(IDept dept: depts){
                		deptsname.put(dept.getID(),dept.getTitle());
                	}
                	for(IWorker worker: workers) {
                		if (worker.getManagerID() != null)
                			managersname.add(access.getWorkerByID(worker.getManagerID()));             	
                	}
                	req.getSession().setAttribute("w_worker", null);
                    req.getSession().setAttribute("w_workers", workers);
                    req.getSession().setAttribute("w_departments", deptsname);
                    req.getSession().setAttribute("w_offices",officesname);
                    req.getSession().setAttribute("w_jobs", jobsname);
                    log.info("ViewWorkerActionPorcessor get all worker {managersname :" + managersname+"}");
                    req.getSession().setAttribute("w_manager", getHashMapWorkers(managersname));
                    try {
                    	log.info("redirect to redirect to index.jsp");
                        resp.sendRedirect("index.jsp");
                    } catch (IOException e) {
                        log.error("can't redirect on the showworker.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                } 
            }  
    }

}

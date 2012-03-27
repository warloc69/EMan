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
	/**
     * method processes the request from user and generate response with workers list
     * @param req it's request
     * @param resp it's response
     * @throws ConfigLoaderException  when got incorrect configs file.
     * @throws DataAccessException when can't access to data.
     */
    public void process(HttpServletRequest req, HttpServletResponse resp, IDataAccessor access) throws DataAccessException {
        if("view_worker".equalsIgnoreCase(req.getParameterValues("action_id")[req.getParameterValues("action_id").length-1])) {
                try {
                	log.info("ViewWorkerActionPorcessor get id {id:"+req.getParameter("id")+"}");
                	Collection<IWorker> workers  = access.getAllWorkers(null);
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
        
            } else if("view_top_manager".equalsIgnoreCase(req.getParameterValues("action_id")[req.getParameterValues("action_id").length-1])) {
                try {
                	log.info(" ViewWorkerActionPorcessor get id { id:"+req.getParameter("id")+"} query string :" + req.getQueryString());
                	Collection<IWorker> workers = null;
                	Collection<IWorker> allworkers = null;
                	if (req.getParameter("sort") != null) {
                		log.info(" ViewWorkerActionPorcessor get mgr_id sort");
                		allworkers  = access.getAllWorkers(req.getParameter("sort"));
                	} else {
                		log.info(" ViewWorkerActionPorcessor get mgr_id not sort");
                		allworkers  = access.getAllWorkers(null);
                	}
                	IWorker wor = null;
                	if (req.getParameter("id") != null) { 
                		if ("null".equals(req.getParameter("id"))) { 
                			log.info("redirect to index.jsp?action_id=view_top_manager");
                			try {
								resp.sendRedirect("index.jsp?action_id=view_top_manager");
							} catch (IOException e) {
								log.error("can't redirect on the showworkers.jsp",e);
							}
                			return;
                		}
                		log.info("id === "+req.getParameter("id"));           
                		req.getSession().setAttribute("path", access.getPath(new BigInteger(req.getParameter("id"))));
                		wor = access.getWorkerByID(new BigInteger(req.getParameter("id")));
                		
                	}
                	if (req.getParameter("id") == null) {
                		if (req.getParameter("sort") == null) {  
                			workers = access.getTopManagers(null);
                		} else {
                			workers = access.getTopManagers(req.getParameter("sort"));
                		}
                	} else {
                		if (req.getParameter("sort") == null) {                			
                			workers = access.getWorkersByMgrID(new BigInteger(req.getParameter("id")),null);
                			log.info("ViewWorkerActionPorcessor get all worker {not sorted :" + workers+"}");
                		} else {
                			workers = access.getWorkersByMgrID(new BigInteger(req.getParameter("id")),req.getParameter("sort"));
                			log.info("ViewWorkerActionPorcessor get all worker {sorted :" + workers+"}");
                		}
                	}
                    HashMap<BigInteger,String> deptsname = new  HashMap<BigInteger,String>();
                    HashMap<BigInteger,String> managersname = new  HashMap<BigInteger,String>();
                	HashMap<BigInteger,String> officesname = new  HashMap<BigInteger,String>();
                	HashMap<BigInteger,String> jobsname = new HashMap<BigInteger,String>();
                	Collection<IJob> jobs= access.getAllJobs(null);
                	for(IJob job: jobs){
                		jobsname.put(job.getID(),job.getTitle());
                	}
                	Collection<IOffice> offices= access.getAllOffices(null);
                	for(IOffice office: offices){
                		officesname.put(office.getID(),office.getTitle());
                	}
                	Collection<IDept> depts = access.getAllDepts(null);
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
                    req.getSession().setAttribute("w_manager",managersname);
                    try {
                    	if (req.getParameter("tab") == null){
	                    	
	                    	if (req.getParameter("select") == null) {
	                    		if(req.getParameter("sort") == null) {
	                    			if (req.getParameter("id") != null){
		                    			log.info("redirect to index.jsp?action_id=view_top_manager&id=" + req.getParameter("id"));
		                    			resp.sendRedirect("index.jsp?action_id=view_top_manager&id=" + req.getParameter("id"));
	                    			} else {
	                    				log.info("redirect to index.jsp?action_id=view_top_manager");
		                    			resp.sendRedirect("index.jsp?action_id=view_top_manager");
	                    			}
	                    		} else {
	                    			if (req.getParameter("id") != null){
		                    			log.info("redirect to index.jsp?action_id=view_top_manager&id=" + req.getParameter("id")+"&sort="+req.getParameter("sort"));
		                    			resp.sendRedirect("index.jsp?action_id=view_top_manager&id=" + req.getParameter("id")+"&sort="+req.getParameter("sort"));
	                    			} else {
	                    				log.info("1 redirect to index.jsp?action_id=view_top_manager"+"&sort="+req.getParameter("sort"));
		                    			resp.sendRedirect("index.jsp?action_id=view_top_manager"+"&sort="+req.getParameter("sort"));
	                    			}
	                    		}
	                    	} else {
	                    		if ( req.getParameter("id") != null) {
		                    		log.info("redirect to index.jsp?action_id=view_top_manager&select=true&id=" + req.getParameter("id"));
		                    		resp.sendRedirect("index.jsp?action_id=view_top_manager&select=true&id=" + req.getParameter("id"));
	                    		} else {
	                    			log.info("redirect to index.jsp?action_id=view_top_manager&select=true");
		                    		resp.sendRedirect("index.jsp?action_id=view_top_manager&select=true");
	                    		}
	                    	}
                    	} else {
                    		log.info("redirect to index.jsp?action_id=view_top_manager&tab=details&id=" + req.getParameter("id"));
	                        resp.sendRedirect("index.jsp?action_id=view_top_manager&tab=details&id=" + req.getParameter("id"));
                    	} 
                    } catch (IOException e) {
                        log.error("can't redirect on the showworkers.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                } return;
            } else if ("search".equalsIgnoreCase(req.getParameterValues("action_id")[req.getParameterValues("action_id").length-1])) {
                    try {
                    	log.info("ViewWorkerActionPorcessor get id {last_name:"+req.getParameter("id")+"}");
                        Collection<IWorker> workers = access.getWorkersByName(req.getParameter("id"));
                        req.getSession().setAttribute("w_workers", workers);
                        log.info("workers : "+ workers );
                        try {
                        	log.info("redirect to showworkers.jsp");
                        	if (req.getParameter("select")== null ) {
                        		resp.sendRedirect("index.jsp?action_id=search&id="+req.getParameter("id"));
                        	} else {
                        		resp.sendRedirect("index.jsp?action_id=search&select=true&id="+req.getParameter("id"));
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
                	Collection<IWorker> workers  = null;
                	if (req.getParameter("sort") != null) {
                		workers  = access.getAllWorkers(req.getParameter("sort"));
                	} else {
                		workers  = access.getAllWorkers(null);
                	}
                	HashMap<BigInteger,String> deptsname = new  HashMap<BigInteger,String>();
                	HashSet<IWorker> managersname = new  HashSet<IWorker>();
                	HashMap<BigInteger,String> officesname = new  HashMap<BigInteger,String>();
                	HashMap<BigInteger,String> jobsname = new HashMap<BigInteger,String>();
                	Collection<IJob> jobs= access.getAllJobs(null);
                	for(IJob job: jobs){
                		jobsname.put(job.getID(),job.getTitle());
                	}
                	Collection<IOffice> offices = access.getAllOffices(null);
                	for(IOffice office: offices){
                		officesname.put(office.getID(),office.getTitle());
                	}
                	Collection<IDept> depts = null;
                	if (req.getParameter("sort") != null) {
                		depts= access.getAllDepts(req.getParameter("sort"));
                	} else {
                		depts= access.getAllDepts(null);
                	}
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

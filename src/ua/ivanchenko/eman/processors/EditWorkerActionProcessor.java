package ua.ivanchenko.eman.processors;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.ivanchenko.eman.exceptions.ConfigLoaderException;
import ua.ivanchenko.eman.exceptions.DataAccessException;
import ua.ivanchenko.eman.model.IDataAccessor;
import ua.ivanchenko.eman.model.IWorker;
import ua.ivanchenko.eman.model.Worker;
public class EditWorkerActionProcessor implements ActionProcessor {
	private Logger log = Logger.getLogger("emanlogger");
    public void process(HttpServletRequest req, HttpServletResponse resp, IDataAccessor access) throws DataAccessException, ConfigLoaderException {
    	if("edit_worker_add".equals(req.getParameter("action_id"))) {   
    		if(req.getParameter("lname") == null) {
    			try {
    				log.info("edit add id: "+req.getParameter("id"));
    				if(!"null".equals(req.getParameter("id"))){
	    				IWorker wor = access.getWorkerByID(new BigInteger(req.getParameter("id")));
						req.getSession().setAttribute("e_wor", wor);
    				}
    				resp.sendRedirect("editworker.jsp?action_id=edit_worker_add");
				} catch (IOException e) {
					log.error("can't redirect on the editjob.jsp",e);
				}
    		} else {
    			try {
                    IWorker worker  = new Worker();
                    worker.setFirstName(req.getParameter("fname"));
                    worker.setLastName(req.getParameter("lname"));
                    if (!"null".equals(req.getParameter("mgr_id"))) {
                    	worker.setManagerID(new BigInteger(req.getParameter("mgr_id")));
                    } else {
                    	worker.setManagerID(null);
                    }
                    worker.setDepartmentID(new BigInteger(req.getParameter("dept_id")));
                    worker.setJobID(new BigInteger(req.getParameter("job_id")));
                    worker.setOfficeID(new BigInteger(req.getParameter("office_id")));
                    worker.setSalegrade(Double.parseDouble(req.getParameter("sal")));
                    access.addWorker(worker);
                    try {
                    	if (req.getParameter("mgr_id") != null) {
                    		resp.sendRedirect("index.jsp?action_id=view_top_manager&id=" + req.getParameter("mgr_id"));
                    	} else {
                    		resp.sendRedirect("index.jsp");
                    	}
                    } catch (IOException e) {
                        log.error("can't redirect on the showworkers.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                } 
    		}
    	} else if ("edit_worker_update".equals(req.getParameter("action_id"))) {
            	if(req.getParameter("lname") == null) {
        			IWorker worker = access.getWorkerByID(new BigInteger(req.getParameter("id")));
        			req.getSession().setAttribute("e_worker", worker);
        			HashMap<String,String> info = new HashMap<String,String>();
        			if (worker.getManagerID() != null) {
        				info.put("mgr_id",access.getWorkerByID(worker.getManagerID()).getLastName());
        			} else {
        				info.put("mgr_id",null);
        			}
        			info.put("office_id", access.getOfficeByID(worker.getOfficeID()).getTitle());
        			info.put("dept_id", access.getDeptByID(worker.getDepartmentID()).getTitle());
        			info.put("job_id", access.getJobByID(worker.getJobID()).getTitle());
        			req.getSession().setAttribute("info", info);
        			try {
						resp.sendRedirect("editworker.jsp?action_id=edit_worker_update");
					} catch (IOException e) {
						log.error("can't redirect on the editjob.jsp",e);
					}
        		} else {
	            	try {
	                    IWorker worker  = new Worker();
	                    worker.setID(new BigInteger(req.getParameter("id")));
	                    worker.setFirstName(req.getParameter("fname"));
	                    worker.setLastName(req.getParameter("lname"));
	                    if (!"null".equals(req.getParameter("mgr_id"))) {
	                    	worker.setManagerID(new BigInteger(req.getParameter("mgr_id")));
	                    } else {
	                    	worker.setManagerID(null);
	                    }
	                    worker.setDepartmentID(new BigInteger(req.getParameter("dept_id")));
	                    worker.setJobID(new BigInteger(req.getParameter("job_id")));
	                    worker.setOfficeID(new BigInteger(req.getParameter("office_id")));
	                    worker.setSalegrade(Double.parseDouble(req.getParameter("sal")));
	                    access.updateWorker(worker);
	                    try {
	                        resp.sendRedirect("index.jsp?action_id=view_worker&id=" + req.getParameter("id"));
	                    } catch (IOException e) {
	                        log.error("can't redirect on the showworkers.jsp",e);
	                    }
	                } catch (DataAccessException e) {
	                    log.error("can't gets data from IDataAccessor",e);
	                } 
        		}
    	 } else if ("edit_worker_remove".equals(req.getParameter("action_id")))  {
             try {	
            	 	BigInteger mgr_id = access.getWorkerByID(new BigInteger(req.getParameter("id"))).getManagerID();
                   access.removeWorker(new BigInteger(req.getParameter("id")));
                    try {
                    	log.info("remove: ");
                        resp.sendRedirect("index.jsp?action_id=view_top_manager&id="+mgr_id);
                    } catch (IOException e) {
                        log.error("can't redirect on the showworkers.jsp",e);
                    }
                } catch (DataAccessException e) {
                	try {
						resp.sendRedirect("error.jsp?error_id="+e.getMessage()+e.getCause());
					} catch (IOException e1) {
						log.error("can't redirect on the showworkr.jsp",e);
					}
                }
                        
        } 


    }

}

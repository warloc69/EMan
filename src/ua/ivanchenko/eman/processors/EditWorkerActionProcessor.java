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
	/**
     * method processes the request from user and generate response
     * @param req it's request
     * @param resp it's response
     * @throws ConfigLoaderException  when got incorrect configs file.
     * @throws DataAccessException when can't access to data.
     */
    public void process(HttpServletRequest req, HttpServletResponse resp, IDataAccessor access) throws DataAccessException {
    	if("edit_worker_add".equalsIgnoreCase(req.getParameterValues("action_id")[req.getParameterValues("action_id").length-1])) {   
    		if(req.getMethod().equalsIgnoreCase("GET")) {
    			try {
    				if(req.getParameter("id") != null){
	    				IWorker wor = access.getWorkerByID(new BigInteger(req.getParameter("id")));
						req.getSession().setAttribute("e_wor", wor);
    				} else {
    					req.getSession().setAttribute("e_wor",null);
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
                    if (!"null".equalsIgnoreCase(req.getParameter("mgr_id"))) {
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
                    	if (req.getParameter("mgr_id") != null ) {
                    		if(!"null".equalsIgnoreCase(req.getParameter("mgr_id"))) {
                    			resp.sendRedirect("index.jsp?action_id=view_top_manager&id=" + req.getParameter("mgr_id"));
                    		} else {
                        		resp.sendRedirect("index.jsp");
                        	}
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
    	} else if ("edit_worker_update".equalsIgnoreCase(req.getParameterValues("action_id")[req.getParameterValues("action_id").length-1])) {
            	if(req.getMethod().equalsIgnoreCase("GET")) {
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
						resp.sendRedirect("editworker.jsp?action_id=edit_worker_update&id="+req.getParameter("id"));
					} catch (IOException e) {
						log.error("can't redirect on the editjob.jsp",e);
					}
        		} else {
	            	try {
	                    IWorker worker  = new Worker();
	                    worker.setID(new BigInteger(req.getParameter("id")));
	                    worker.setFirstName(req.getParameter("fname"));
	                    worker.setLastName(req.getParameter("lname"));
	                    if (!"null".equalsIgnoreCase(req.getParameter("mgr_id"))) {
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
    	 } else if ("edit_worker_remove".equalsIgnoreCase(req.getParameterValues("action_id")[req.getParameterValues("action_id").length-1]))  {
             try {	
            	  BigInteger mgr_id = access.getWorkerByID(new BigInteger(req.getParameter("id"))).getManagerID();            	 	
            	  access.removeWorker(new BigInteger(req.getParameter("id")));
                  if (mgr_id != null) { 
                  	resp.sendRedirect("index.jsp?action_id=view_top_manager&id="+mgr_id);
                  } else {
                  	resp.sendRedirect("index.jsp");
                  }
                    
             } catch (DataAccessException e) {
                try {
					resp.sendRedirect("error.jsp?error_id="+e.getMessage());
				} catch (IOException e1) {
					log.error("can't redirect on the showworkr.jsp",e);
				}
             } catch (IOException e) {
                    log.error("can't redirect on the showworkers.jsp",e);
             }        
        } 
    }
}

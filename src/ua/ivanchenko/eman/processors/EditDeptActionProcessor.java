package ua.ivanchenko.eman.processors;

import java.io.IOException;
import java.math.BigInteger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.ivanchenko.eman.exceptions.ConfigLoaderException;
import ua.ivanchenko.eman.exceptions.DataAccessException;
import ua.ivanchenko.eman.model.Dept;
import ua.ivanchenko.eman.model.IDataAccessor;
import ua.ivanchenko.eman.model.IDept;
import ua.ivanchenko.eman.model.OracleDataAccessorConst;

public class EditDeptActionProcessor implements ActionProcessor {
	private Logger log = Logger.getLogger("emanlogger");
	/**
     * method processes the request from user and generate response
     * @param req it's request
     * @param resp it's response
     * @throws ConfigLoaderException  when got incorrect configs file.
     * @throws DataAccessException when can't access to data.
     */
    public void process(HttpServletRequest req, HttpServletResponse resp , IDataAccessor access) throws DataAccessException, ConfigLoaderException {
    	if("edit_dept_add".equals(req.getParameter("action_id"))) {   
    		if(req.getParameter("title")== null) {
    			try {
					resp.sendRedirect("editdept.jsp?action_id=edit_dept_add");
				} catch (IOException e) {
					log.error("can't redirect on the editjob.jsp",e);
				}
    		} else {
    			try {
                    IDept dept  = new Dept();
                    dept.setTitle(req.getParameter("title"));
                    dept.setDescription(req.getParameter("desc"));
                    access.addDept(dept);
                    try {
                        resp.sendRedirect("index.jsp?action_id=view_dept");
                    } catch (IOException e) {
                        log.error("can't redirect on the showdepts.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                }  
    		}
    	} else if ("edit_dept_update".equals(req.getParameter("action_id"))) {
    		if(req.getParameter("title")== null) {
        			IDept dept = access.getDeptByID(new BigInteger(req.getParameter("id")));
        			req.getSession().setAttribute("e_dept", dept);
        			try {
						resp.sendRedirect("editdept.jsp?action_id=edit_dept_update");
					} catch (IOException e) {
						log.error("can't redirect on the editjob.jsp",e);
					}
        		} else {
	            	try {
	                    IDept dept  = new Dept();
	                    dept.setID(new BigInteger(req.getParameter("id")));
	                    dept.setTitle(req.getParameter("title"));
	                    dept.setDescription(req.getParameter("desc"));
	                    access.updateDept(dept);
	                    try {
	                        resp.sendRedirect("index.jsp?action_id=view_dept");
	                    } catch (IOException e) {
	                        log.error("can't redirect on the showdepts.jsp",e);
	                    }
	                } catch (DataAccessException e) {
	                    log.error("can't gets data from IDataAccessor",e);
	                } 
        		}
    		} else if ("edit_dept_remove".equals(req.getParameter("action_id")))  {
                try {
                	if (!access.isWorkerExist(OracleDataAccessorConst.GET_WORKER_BY_DEPT_ID,(new BigInteger(req.getParameter("id"))))) {
                		access.removeDept(new BigInteger(req.getParameter("id")));
                	} else {
                		throw new DataAccessException("Cannot remove department becose department is use");
                	}                    
                    try {
                        resp.sendRedirect("index.jsp?action_id=view_dept");
                    } catch (IOException e) {
                        log.error("can't redirect on the showdepts.jsp",e);
                    }
                } catch (DataAccessException e) {
                	try {
						resp.sendRedirect("error.jsp?error_id="+e.getMessage());
					} catch (IOException e1) {
						log.error("can't redirect on the showdepts.jsp",e);
					}
                }
            }            
         

    }

}

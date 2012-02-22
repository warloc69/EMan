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

public class EditDeptActionProcessor implements ActionProcessor {
	private Logger log = Logger.getLogger("emanlogger");
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
                    access.removeDept(new BigInteger(req.getParameter("id")));
                    try {
                        resp.sendRedirect("index.jsp?action_id=view_dept");
                    } catch (IOException e) {
                        log.error("can't redirect on the showdepts.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                }
            }            
         

    }

}

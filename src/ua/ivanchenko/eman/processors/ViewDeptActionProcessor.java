package ua.ivanchenko.eman.processors;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.ivanchenko.eman.exceptions.ConfigLoaderException;
import ua.ivanchenko.eman.exceptions.DataAccessException;
import ua.ivanchenko.eman.model.IDataAccessor;
import ua.ivanchenko.eman.model.IDept;
public class ViewDeptActionProcessor implements ActionProcessor {
	private Logger log = Logger.getLogger("emanlogger");
	/**
     * method processes the request from user and generate response departments list
     * @param req it's request
     * @param resp it's response
     * @throws ConfigLoaderException  when got incorrect configs file.
     * @throws DataAccessException when can't access to data.
     */
    public void process(HttpServletRequest req, HttpServletResponse resp, IDataAccessor access) throws DataAccessException {
    	if (req.getParameter("id") != null) {
                try {
                    IDept dept = access.getDeptByID(new BigInteger(req.getParameter("id")));
                    req.getSession().setAttribute("d_dept", dept);
                    req.getSession().setAttribute("d_depts", null);
                    try {
                        resp.sendRedirect("index.jsp?action_id=view_dept");
                    } catch (IOException e) {
                        log.error("can't redirect on the showdepts.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                }
                return;
            } else if(req.getParameter("title") != null) {
                try {
                    IDept dept = access.getDeptByTitle(req.getParameter("title"));
                    req.getSession().setAttribute("d_dept", dept);
                    req.getSession().setAttribute("d_depts", null);
                    try {
                        resp.sendRedirect("showdepts.jsp");
                    } catch (IOException e) {
                        log.error("can't redirect on the showdepts.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                }
                return;
            } else {
                try {
                	Collection<IDept> depts = null;
                	if (req.getParameter("sort") != null) {
                		depts  = access.getAllDepts(req.getParameter("sort"));
                	} else {
                		depts  = access.getAllDepts(null);
                	}
                    req.getSession().setAttribute("d_depts", depts);
                    try {
                    	if (req.getParameter("select") == null) {
                    		if(req.getParameter("sort") == null) {
                    			log.error("index.jsp?action_id=view_dept");
                    			resp.sendRedirect("index.jsp?action_id=view_dept");
                    		} else {
                    			log.error("index.jsp?action_id=view_dept&sort="+req.getParameter("sort"));
                    			resp.sendRedirect("index.jsp?action_id=view_dept&sort="+req.getParameter("sort"));
                    		}
                    	} else {
                    		if(req.getParameter("sort") == null) {
                    			resp.sendRedirect("index.jsp?action_id=view_dept&select=true");
                    		} else {
                    			resp.sendRedirect("index.jsp?action_id=view_dept&select=true&sort="+req.getParameter("sort"));
                    		}
                    	}
                    } catch (IOException e) {
                        log.error("can't redirect on the showdepts.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                } return;
            }
        
    }
}

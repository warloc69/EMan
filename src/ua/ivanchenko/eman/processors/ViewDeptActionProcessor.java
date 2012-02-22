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
    public void process(HttpServletRequest req, HttpServletResponse resp, IDataAccessor access) throws DataAccessException, ConfigLoaderException {
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
                	Collection<IDept> depts  = access.getAllDepts();
                    req.getSession().setAttribute("d_depts", depts);
                    try {
                    	if (req.getParameter("select") == null) {
                    		resp.sendRedirect("index.jsp?action_id=view_dept");
                    	} else {
                    		resp.sendRedirect("index.jsp?action_id=view_dept&select=true");
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

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
import ua.ivanchenko.eman.model.IJob;
public class ViewJobActionProcessor implements ActionProcessor {
	private Logger log = Logger.getLogger("emanlogger");
	/**
     * method processes the request from user and generate response with jobs list
     * @param req it's request
     * @param resp it's response
     * @throws ConfigLoaderException  when got incorrect configs file.
     * @throws DataAccessException when can't access to data.
     */
    public void process(HttpServletRequest req, HttpServletResponse resp, IDataAccessor access) throws DataAccessException {
        if(req.getParameter("id") != null){
                try {
                    IJob job = access.getJobByID(new BigInteger(req.getParameter("id")));
                    req.getSession().setAttribute("j_jobs", null);
                    req.getSession().setAttribute("j_job", job);
                    try {
                        resp.sendRedirect("index.jsp?action_id=view_job");
                    } catch (IOException e) {
                        log.error("can't redirect on the showjobs.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                }
                return;
            } else if(req.getParameter("title") != null) {
                try {
                    IJob job = access.getJobByTitle(req.getParameter("title"));
                    req.getSession().setAttribute("j_jobs", null);
                    req.getSession().setAttribute("j_job", job);
                    try {
                        resp.sendRedirect("showjobs.jsp");
                    } catch (IOException e) {
                        log.error("can't redirect on the showjobs.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                }
                return;
            } else {
                try {
                	Collection<IJob> jobs  = null;
                	if (req.getParameter("sort") != null) {
                		jobs  = access.getAllJobs(req.getParameter("sort"));
                	} else {
                		jobs  = access.getAllJobs(null);
                	}
                    req.getSession().setAttribute("j_jobs", jobs);
                    try {
                    	if (req.getParameter("select") == null) {
                    		if(req.getParameter("sort") == null) {
                    			resp.sendRedirect("index.jsp?action_id=view_job");
                    		} else {
                    			resp.sendRedirect("index.jsp?action_id=view_job&sort="+req.getParameter("sort"));
                    		}
                    	} else {
                    		if(req.getParameter("sort") == null) {
                    			resp.sendRedirect("index.jsp?action_id=view_job&select=true");
                    		} else {
                    			resp.sendRedirect("index.jsp?action_id=view_job&select=true&sort="+req.getParameter("sort"));
                    		}
                    	}
                    } catch (IOException e) {
                        log.error("can't redirect on the showjobs.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                } return;
            }
    }
}

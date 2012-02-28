package ua.ivanchenko.eman.processors;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.ivanchenko.eman.exceptions.ConfigLoaderException;
import ua.ivanchenko.eman.exceptions.DataAccessException;
import ua.ivanchenko.eman.model.IDataAccessor;
import ua.ivanchenko.eman.model.IWorker;

public class FilterActionProcessor implements ActionProcessor {
	private Logger log = Logger.getLogger("emanlogger");
	/**
     * method processes the request from user and generate response
     * @param req it's request
     * @param resp it's response
     * @throws ConfigLoaderException  when got incorrect configs file.
     * @throws DataAccessException when can't access to data.
     */
	public void process(HttpServletRequest req, HttpServletResponse resp,
			IDataAccessor access) throws DataAccessException,
				ConfigLoaderException {
		HashMap<String, String> filters = new HashMap<String, String>();
		if (req.getParameter(JspConst.fname) != null && !req.getParameter(JspConst.fname).isEmpty()) {
			filters.put(JspConst.fname,req.getParameter(JspConst.fname));
		}
		if (req.getParameter(JspConst.lname) != null && !req.getParameter(JspConst.lname).isEmpty()) {
			filters.put(JspConst.lname,req.getParameter(JspConst.lname));
		}
		if (req.getParameter(JspConst.job) != null && !req.getParameter(JspConst.job).isEmpty()) {
			filters.put(JspConst.job,req.getParameter(JspConst.job));
		}
		if (req.getParameter(JspConst.dept) != null && !req.getParameter(JspConst.dept).isEmpty()) {
			filters.put(JspConst.dept,req.getParameter(JspConst.dept));
		}		
		if (req.getParameter(JspConst.office) != null && !req.getParameter(JspConst.office).isEmpty()) {
			filters.put(JspConst.office,req.getParameter(JspConst.office));
		}	
		Collection<IWorker> workers = null;
		if (req.getParameter("id") != null && !"null".equals(req.getParameter("id"))) {
			workers = access.filteringWorker(new BigInteger(req.getParameter("id")),filters);		
		} else {
			workers = access.filteringWorker(null,filters);
		}
			req.getSession().setAttribute("w_workers", workers);
		try {
			StringBuffer re = new StringBuffer("index.jsp?action_id=view_top_manager");
			   if (filters.get(JspConst.fname) != null) 
					re.append("&fname="+req.getParameter(JspConst.fname));	
			   if (filters.get(JspConst.lname) != null) 
						re.append("&lname="+req.getParameter(JspConst.lname));
			 	if (filters.get(JspConst.job) != null) 
					re.append("&job="+req.getParameter(JspConst.job));
			 	if (filters.get(JspConst.office) != null) 
					re.append("&office="+req.getParameter(JspConst.office));
			 	if (filters.get(JspConst.dept) != null) 
					re.append("&dept="+req.getParameter(JspConst.dept));
			 	if (req.getParameter("id") != null && !"null".equals(req.getParameter("id"))) 
			 		re.append("&id="+req.getParameter("id"));			
			 	re.append("&filtre=true");								
				log.info(re.toString());
			if (req.getParameter("select") == null ) {		   
				resp.sendRedirect(re.toString());
			} else {
				re.append("&select=true");
				resp.sendRedirect(re.toString());
			}
		} catch (IOException e) {
			log.error("can't redirect from ",e);
		}
	}
}

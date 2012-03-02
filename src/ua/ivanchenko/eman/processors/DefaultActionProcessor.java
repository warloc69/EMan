package ua.ivanchenko.eman.processors;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import ua.ivanchenko.eman.exceptions.ConfigLoaderException;
import ua.ivanchenko.eman.exceptions.DataAccessException;
import ua.ivanchenko.eman.model.IDataAccessor;

public class DefaultActionProcessor implements ActionProcessor {
	private Logger log = Logger.getLogger("emanlogger");
	/**
     * method processes the request from user and generate response
     * @param req it's request
     * @param resp it's response
     * @throws ConfigLoaderException  when got incorrect configs file.
     * @throws DataAccessException when can't access to data.
     */
    public void process(HttpServletRequest req, HttpServletResponse resp, IDataAccessor accessor) {
    	try {
        	log.info("redirect to index.jsp from DeafultActionProcessor");
        	if(req.getParameter("sort")== null)
            resp.sendRedirect("index.jsp");
        } catch (IOException e) {
            log.error("can't redirect on the showworker.jsp",e);
        }
    }

}

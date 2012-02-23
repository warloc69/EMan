package ua.ivanchenko.eman.processors;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.ivanchenko.eman.model.IDataAccessor;

public class DefaultActionProcessor implements ActionProcessor {
	private Logger log = Logger.getLogger("emanlogger");
    public void process(HttpServletRequest req, HttpServletResponse resp, IDataAccessor accessor) {
    	try {
        	log.info("redirect to index.jsp?action_id=view_worker from DeafultActionProcessor");
        	if(req.getParameter("sort")== null)
            resp.sendRedirect("index.jsp");
        } catch (IOException e) {
            log.error("can't redirect on the showworker.jsp",e);
        }
    }

}

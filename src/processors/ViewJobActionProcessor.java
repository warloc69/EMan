package processors;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DataAccessor;
import model.IDataAccessor;
import model.IJob;
import exceptions.DataAccessException;


public class ViewJobActionProcessor implements ActionProcessor {
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ViewJobActionProcessor.class);
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) {
		Integer get_id = (Integer) req.getAttribute("get_id");
		switch (get_id) {
			case ProcessorConst.GET_ALL: {
				IDataAccessor access = DataAccessor.getInstance();
				try {
					Collection<IJob> jobs  = access.getAllJobs();
					req.setAttribute("jobs", jobs);
					try {
						resp.sendRedirect("showjobs.jsp");
					} catch (IOException e) {
						log.error("can't redirect on the showjobs.jsp",e);
					}
				} catch (DataAccessException e) {
					log.error("can't gets data from IDataAccessor",e);
				} break;
			}
			case ProcessorConst.GET_BY_ID: {
				IDataAccessor access = DataAccessor.getInstance();
				try {
					IJob job = access.getJobByID((BigInteger)req.getAttribute("id"));
					req.setAttribute("job", job);
					try {
						resp.sendRedirect("showjobs.jsp");
					} catch (IOException e) {
						log.error("can't redirect on the showjobs.jsp",e);
					}
				} catch (DataAccessException e) {
					log.error("can't gets data from IDataAccessor",e);
				}
				break;
			}
			case ProcessorConst.GET_BY_TITLE: {
				IDataAccessor access = DataAccessor.getInstance();
				try {
					IJob job = access.getJobByTitle((String)req.getAttribute("title"));
					req.setAttribute("job", job);
					try {
						resp.sendRedirect("showjobs.jsp");
					} catch (IOException e) {
						log.error("can't redirect on the showjobs.jsp",e);
					}
				} catch (DataAccessException e) {
					log.error("can't gets data from IDataAccessor",e);
				}
				break;
			}
		} 
	}

}

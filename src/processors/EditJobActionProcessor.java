package processors;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DataAccessor;
import model.IDataAccessor;
import model.IJob;
import model.Job;
import exceptions.DataAccessException;


public class EditJobActionProcessor implements ActionProcessor {
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(EditJobActionProcessor.class);
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) {
		Integer action_id = (Integer) req.getAttribute("edit_id");
		switch (action_id) {
			case ProcessorConst.ACTION_ADD: {
				IDataAccessor access = DataAccessor.getInstance();
				try {
					IJob job  = new Job();
					job.setTitle((String)req.getAttribute("title"));
					job.setDescription((String)req.getAttribute("desc"));
					access.addJob(job);
					try {
						resp.sendRedirect("showjobs.jsp");
					} catch (IOException e) {
						log.error("can't redirect on the showjobs.jsp",e);
					}
				} catch (DataAccessException e) {
					log.error("can't gets data from IDataAccessor",e);
				} break;
			}
			case ProcessorConst.ACTION_UPDATE: {
				IDataAccessor access = DataAccessor.getInstance();
				try {
					IJob job  = new Job();
					job.setID((BigInteger)req.getAttribute("id"));
					job.setTitle((String)req.getAttribute("title"));
					job.setDescription((String)req.getAttribute("desc"));
					access.updateJob(job);
					try {
						resp.sendRedirect("showjobs.jsp");
					} catch (IOException e) {
						log.error("can't redirect on the showjobs.jsp",e);
					}
				} catch (DataAccessException e) {
					log.error("can't gets data from IDataAccessor",e);
				} break;
			}
			case ProcessorConst.ACTION_REMOVE: {
				IDataAccessor access = DataAccessor.getInstance();
				try {
					access.removeJob((BigInteger)req.getAttribute("id"));
					try {
						resp.sendRedirect("showdjobs.jsp");
					} catch (IOException e) {
						log.error("can't redirect on the showdepts.jsp",e);
					}
				} catch (DataAccessException e) {
					log.error("can't gets data from IDataAccessor",e);
				}
				break;
			}			
		} 
	}

}

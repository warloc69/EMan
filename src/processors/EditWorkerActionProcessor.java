package processors;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DataAccessor;
import model.IDataAccessor;
import model.IWorker;
import model.Worker;
import exceptions.DataAccessException;


public class EditWorkerActionProcessor implements ActionProcessor {
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(EditWorkerActionProcessor.class);
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) {
		Integer action_id = (Integer) req.getAttribute("actio_id");
		switch (action_id) {
			case ProcessorConst.ACTION_ADD: {
				IDataAccessor access = DataAccessor.getInstance();
				try {
					IWorker worker  = new Worker();
					worker.setFirstName((String)req.getAttribute("fname"));
					worker.setLastName((String)req.getAttribute("lname"));
					worker.setManagerID((BigInteger)req.getAttribute("mgr_id"));
					worker.setDepartmentID((BigInteger)req.getAttribute("dep_id"));
					worker.setJobID((BigInteger)req.getAttribute("job_id"));
					worker.setOfficeID((BigInteger)req.getAttribute("office_id"));
					access.addWorker(worker);
					try {
						resp.sendRedirect("showworkers.jsp");
					} catch (IOException e) {
						log.error("can't redirect on the showworkers.jsp",e);
					}
				} catch (DataAccessException e) {
					log.error("can't gets data from IDataAccessor",e);
				} break;
			}
			case ProcessorConst.ACTION_UPDATE: {
				IDataAccessor access = DataAccessor.getInstance();
				try {
					IWorker worker  = new Worker();
					worker.setID((BigInteger)req.getAttribute("id"));
					worker.setFirstName((String)req.getAttribute("fname"));
					worker.setLastName((String)req.getAttribute("lname"));
					worker.setManagerID((BigInteger)req.getAttribute("mgr_id"));
					worker.setDepartmentID((BigInteger)req.getAttribute("dep_id"));
					worker.setJobID((BigInteger)req.getAttribute("job_id"));
					worker.setOfficeID((BigInteger)req.getAttribute("office_id"));
					access.updateWorker(worker);
					try {
						resp.sendRedirect("showworkers.jsp");
					} catch (IOException e) {
						log.error("can't redirect on the showworkers.jsp",e);
					}
				} catch (DataAccessException e) {
					log.error("can't gets data from IDataAccessor",e);
				} break;
			}
			case ProcessorConst.ACTION_REMOVE: {
				IDataAccessor access = DataAccessor.getInstance();
				try {
					access.removeWorker((BigInteger)req.getAttribute("id"));
					try {
						resp.sendRedirect("showworkers.jsp");
					} catch (IOException e) {
						log.error("can't redirect on the showworkers.jsp",e);
					}
				} catch (DataAccessException e) {
					log.error("can't gets data from IDataAccessor",e);
				}
				break;
			}			
		} 


	}

}

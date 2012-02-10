package processors;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DataAccessor;
import model.IDataAccessor;
import model.IWorker;
import exceptions.DataAccessException;

public class ViewWorkerProcessorAction implements ActionProcessor {
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ViewWorkerProcessorAction.class);
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) {
		Integer get_id = (Integer) req.getAttribute("get_id");
		switch (get_id) {
			case ProcessorConst.GET_ALL: {
				IDataAccessor access = DataAccessor.getInstance();
				try {
					Collection<IWorker> workers  = access.getAllWorker();
					req.setAttribute("workers", workers);
					try {
						resp.sendRedirect("showworkers.jsp");
					} catch (IOException e) {
						log.error("can't redirect on the showworker.jsp",e);
					}
				} catch (DataAccessException e) {
					log.error("can't gets data from IDataAccessor",e);
				} break;
			}
			case ProcessorConst.GET_BY_ID: {
				IDataAccessor access = DataAccessor.getInstance();
				try {
					IWorker worker = access.getWorkerByID((BigInteger)req.getAttribute("id"));
					req.setAttribute("worker", worker);
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
			case ProcessorConst.GET_BY_LAST_NAME: {
				IDataAccessor access = DataAccessor.getInstance();
				try {
					Collection<IWorker> workers = access.getWorkerByLastName((String)req.getAttribute("last_name"));
					req.setAttribute("workers", workers);
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
			case ProcessorConst.GET_BY_MGR_ID: {
				IDataAccessor access = DataAccessor.getInstance();
				try {
					Collection<IWorker> workers = access.getWorkerByMgrID((BigInteger)req.getAttribute("mgr_id"));
					req.setAttribute("workers", workers);
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

package ua.ivanchenko.eman.processors;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.ivanchenko.eman.exceptions.ConfigLoaderException;
import ua.ivanchenko.eman.exceptions.DataAccessException;
import ua.ivanchenko.eman.model.DataAccessor;
import ua.ivanchenko.eman.model.IDataAccessor;
import ua.ivanchenko.eman.model.IWorker;

public class ViewWorkerActionProcessor implements ActionProcessor {
	private Logger log = Logger.getLogger("<appname>logger");
    public void process(HttpServletRequest req, HttpServletResponse resp) throws DataAccessException, ConfigLoaderException {
        Integer get_id = Integer.parseInt(req.getParameter("get_id"));
        switch (get_id) {
            case ProcessorConst.GET_ALL: {
                IDataAccessor access = DataAccessor.getInstance();
                try {
                    Collection<IWorker> workers  = access.getAllWorkers();
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
                    Collection<IWorker> workers = access.getWorkersByLastName((String)req.getAttribute("last_name"));
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
                    Collection<IWorker> workers = access.getWorkersByMgrID((BigInteger)req.getAttribute("mgr_id"));
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

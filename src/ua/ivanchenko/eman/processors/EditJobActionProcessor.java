package ua.ivanchenko.eman.processors;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.ivanchenko.eman.exceptions.ConfigLoaderException;
import ua.ivanchenko.eman.exceptions.DataAccessException;
import ua.ivanchenko.eman.model.DataAccessor;
import ua.ivanchenko.eman.model.IDataAccessor;
import ua.ivanchenko.eman.model.IJob;
import ua.ivanchenko.eman.model.Job;


public class EditJobActionProcessor implements ActionProcessor {
	private Logger log = Logger.getLogger("<appname>logger");
    public void process(HttpServletRequest req, HttpServletResponse resp) throws DataAccessException, ConfigLoaderException {
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

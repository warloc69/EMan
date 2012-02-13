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
import ua.ivanchenko.eman.model.IOffice;


public class ViewOfficeActionProcessor implements ActionProcessor {
	private Logger log = Logger.getLogger("<appname>logger");
    public void process(HttpServletRequest req, HttpServletResponse resp) throws DataAccessException, ConfigLoaderException {
        Integer get_id = (Integer) req.getAttribute("get_id");
        switch (get_id) {
            case ProcessorConst.GET_ALL: {
                IDataAccessor access = DataAccessor.getInstance();
                try {
                    Collection<IOffice> offices  = access.getAllOffices();
                    req.setAttribute("offices", offices);
                    try {
                        resp.sendRedirect("showoffices.jsp");
                    } catch (IOException e) {
                        log.error("can't redirect on the showoffices.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                } break;
            }
            case ProcessorConst.GET_BY_ID: {
                IDataAccessor access = DataAccessor.getInstance();
                try {
                    IOffice office = access.getOfficeByID((BigInteger)req.getAttribute("id"));
                    req.setAttribute("office", office);
                    try {
                        resp.sendRedirect("showoffices.jsp");
                    } catch (IOException e) {
                        log.error("can't redirect on the showoffices.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                }
                break;
            }
            case ProcessorConst.GET_BY_TITLE: {
                IDataAccessor access = DataAccessor.getInstance();
                try {
                    IOffice office = access.getOfficeByTitle((String)req.getAttribute("title"));
                    req.setAttribute("office", office);
                    try {
                        resp.sendRedirect("showoffices.jsp");
                    } catch (IOException e) {
                        log.error("can't redirect on the showoffices.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                }
                break;
            }
        } 
    }

    
}

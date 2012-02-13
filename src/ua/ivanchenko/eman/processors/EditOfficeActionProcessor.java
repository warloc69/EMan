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
import ua.ivanchenko.eman.model.IOffice;
import ua.ivanchenko.eman.model.Office;


public class EditOfficeActionProcessor implements ActionProcessor {
	private Logger log = Logger.getLogger("<appname>logger");
    public void process(HttpServletRequest req, HttpServletResponse resp) throws DataAccessException, ConfigLoaderException {
        Integer action_id = (Integer) req.getAttribute("action_id");
        switch (action_id) {
            case ProcessorConst.ACTION_ADD: {
                IDataAccessor access = DataAccessor.getInstance();
                try {
                    IOffice office  = new Office();
                    office.setTitle((String)req.getAttribute("title"));
                    office.setAddress((String)req.getAttribute("adr"));
                    office.setManagerID((BigInteger)req.getAttribute("mgr_id"));
                    access.addOffice(office);
                    try {
                        resp.sendRedirect("showoffices.jsp");
                    } catch (IOException e) {
                        log.error("can't redirect on the showoffices.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                } break;
            }
            case ProcessorConst.ACTION_UPDATE: {
                IDataAccessor access = DataAccessor.getInstance();
                try {
                    IOffice office  = new Office();
                    office.setTitle((String)req.getAttribute("title"));
                    office.setAddress((String)req.getAttribute("adr"));
                    office.setManagerID((BigInteger)req.getAttribute("mgr_id"));
                    office.setID((BigInteger)req.getAttribute("id"));
                    access.updateOffice(office);
                    try {
                        resp.sendRedirect("showoffices.jsp");
                    } catch (IOException e) {
                        log.error("can't redirect on the showoffice.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                } break;
            }
            case ProcessorConst.ACTION_REMOVE: {
                IDataAccessor access = DataAccessor.getInstance();
                try {
                    access.removeOffice((BigInteger)req.getAttribute("id"));
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

package ua.ivanchenko.eman.processors;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.ivanchenko.eman.exceptions.ConfigLoaderException;
import ua.ivanchenko.eman.exceptions.DataAccessException;
import ua.ivanchenko.eman.model.IDataAccessor;
import ua.ivanchenko.eman.model.IOffice;
import ua.ivanchenko.eman.model.Office;


public class EditOfficeActionProcessor implements ActionProcessor {
	private Logger log = Logger.getLogger("emanlogger");
    public void process(HttpServletRequest req, HttpServletResponse resp, IDataAccessor access) throws DataAccessException, ConfigLoaderException {
    	if("edit_office_add".equals(req.getParameter("action_id"))) {   
    		if(req.getParameter("title")== null) {
    			try {
					resp.sendRedirect("editoffice.jsp?action_id=edit_office_add");
				} catch (IOException e) {
					log.error("can't redirect on the editjob.jsp",e);
				}
    		} else {
    			try {
                    IOffice office  = new Office();
                    office.setTitle(req.getParameter("title"));
                    office.setAddress(req.getParameter("adr"));
                    if (!"null".equals(req.getParameter("mgr_id"))) {
                    	office.setManagerID(new BigInteger(req.getParameter("mgr_id")));
                    } else {
                    	office.setManagerID(null);
                    }
                    access.addOffice(office);
                    try {
                        resp.sendRedirect("index.jsp?action_id=view_office");
                    } catch (IOException e) {
                        log.error("can't redirect on the showoffices.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                } 
    		}
    		} else if ("edit_office_update".equals(req.getParameter("action_id"))) {
            	if(req.getParameter("title")== null) {
        			IOffice office = access.getOfficeByID(new BigInteger(req.getParameter("id")));
        			HashMap<String,String> info = new HashMap<String,String>();
        			if (office.getManagerID() != null ) {
        				if (access.getWorkerByID(office.getManagerID()) != null) {
        					info.put("mgr_id",access.getWorkerByID(office.getManagerID()).getLastName());
        				} else {
        					office.setManagerID(null);
        				}
        			}
        			req.getSession().setAttribute("e_office", office);
        			req.getSession().setAttribute("info", info);
        			try {
						resp.sendRedirect("editoffice.jsp?action_id=edit_office_update");
					} catch (IOException e) {
						log.error("can't redirect on the editjob.jsp",e);
					}
        		} else {
	                try {
	                    IOffice office  = new Office();
	                    office.setTitle(req.getParameter("title"));
	                    office.setAddress(req.getParameter("adr"));
	                    if (!"null".equals(req.getParameter("mgr_id"))){
	                    	office.setManagerID(new BigInteger(req.getParameter("mgr_id")));
	                    } else {
	                    	office.setManagerID(null);
	                    }
	                    office.setID(new BigInteger(req.getParameter("id")));
	                    access.updateOffice(office);
	                    try {
	                        resp.sendRedirect("index.jsp?action_id=view_office");
	                    } catch (IOException e) {
	                        log.error("can't redirect on the showoffice.jsp",e);
	                    }
	                } catch (DataAccessException e) {
	                    log.error("can't gets data from IDataAccessor",e);
	                } 
        		}    
    		} else if ("edit_office_remove".equals(req.getParameter("action_id")))  {
                try {
                    access.removeOffice(new BigInteger(req.getParameter("id")));
                    try {
                        resp.sendRedirect("index.jsp?action_id=view_office");
                    } catch (IOException e) {
                        log.error("can't redirect on the showoffices.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                }
                
                        
        } 

    }

}

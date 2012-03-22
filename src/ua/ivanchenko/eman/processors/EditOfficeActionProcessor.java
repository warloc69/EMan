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
	/**
     * method processes the request from user and generate response
     * @param req it's request
     * @param resp it's response
     * @throws ConfigLoaderException  when got incorrect configs file.
     * @throws DataAccessException when can't access to data.
     */
    public void process(HttpServletRequest req, HttpServletResponse resp, IDataAccessor access) throws DataAccessException {
    	if("edit_office_add".equalsIgnoreCase(req.getParameterValues("action_id")[req.getParameterValues("action_id").length-1])) {   
    		if(req.getMethod().equalsIgnoreCase("GET")) {
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
                    if (!"null".equalsIgnoreCase(req.getParameter("mgr_id"))) {
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
    		} else if ("edit_office_update".equalsIgnoreCase(req.getParameterValues("action_id")[req.getParameterValues("action_id").length-1])) {
            	if(req.getMethod().equalsIgnoreCase("GET")) {
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
						resp.sendRedirect("editoffice.jsp?action_id=edit_office_update&id="+req.getParameter("id"));
					} catch (IOException e) {
						log.error("can't redirect on the editjob.jsp",e);
					}
        		} else {
	                try {
	                    IOffice office  = new Office();
	                    office.setTitle(req.getParameter("title"));
	                    office.setAddress(req.getParameter("adr"));
	                    if (!"null".equalsIgnoreCase(req.getParameter("mgr_id"))){
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
    		} else if ("edit_office_remove".equalsIgnoreCase(req.getParameterValues("action_id")[req.getParameterValues("action_id").length-1]))  {
                try {
                	access.removeOffice(new BigInteger(req.getParameter("id")));                    
                    resp.sendRedirect("index.jsp?action_id=view_office");                    
                } catch (DataAccessException e) {
                	try {
						resp.sendRedirect("error.jsp?error_id="+e.getMessage());
					} catch (IOException e1) {
						log.error("can't redirect on the showoffices.jsp",e);
					}
                } catch (IOException e) {
                    log.error("can't redirect on the showoffices.jsp",e);
                }       
        } 

    }

}

package ua.ivanchenko.eman.processors;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.ivanchenko.eman.exceptions.ConfigLoaderException;
import ua.ivanchenko.eman.exceptions.DataAccessException;
import ua.ivanchenko.eman.model.IDataAccessor;
import ua.ivanchenko.eman.model.IOffice;
import ua.ivanchenko.eman.model.IWorker;
public class ViewOfficeActionProcessor implements ActionProcessor {
	private Logger log = Logger.getLogger("emanlogger");
	/**
     * method processes the request from user and generate response with offices list
     * @param req it's request
     * @param resp it's response
     * @throws ConfigLoaderException  when got incorrect configs file.
     * @throws DataAccessException when can't access to data.
     */
    public void process(HttpServletRequest req, HttpServletResponse resp, IDataAccessor access) throws DataAccessException {
     if(req.getParameter("id") != null) {
                try {
                    IOffice office = access.getOfficeByID(new BigInteger(req.getParameter("id")));
                    Collection<IWorker> workers = access.getAllWorkers(null);
                	HashMap<BigInteger,String> managersname = new  HashMap<BigInteger,String>();
                	for(IWorker worker: workers) {
                		managersname.put(worker.getID(), worker.getLastName());             	
                	}
                    req.getSession().setAttribute("o_offices", null);
                    req.getSession().setAttribute("o_office", office);
                    req.getSession().setAttribute("o_manager", managersname);
                    try {
                        resp.sendRedirect("index.jsp?action_id=view_office");
                    } catch (IOException e) {
                        log.error("can't redirect on the showoffices.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                }
                return;
            } else if(req.getParameter("title") != null) {
                try {
                    IOffice office = access.getOfficeByTitle(req.getParameter("title"));
                    req.getSession().setAttribute("o_offices", null);
                    req.getSession().setAttribute("o_office", office);
                    try {
                        resp.sendRedirect("showoffices.jsp");
                    } catch (IOException e) {
                        log.error("can't redirect on the showoffices.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                }
                return;
            } else {
                try {
                	  Collection<IOffice> offices = null;
                	if (req.getParameter("sort") != null) {
                		offices  = access.getAllOffices(req.getParameter("sort"));
                	} else {
                		offices  = access.getAllOffices(null);
                	}
                	Collection<IWorker> workers = access.getAllWorkers(null);
                	HashMap<BigInteger,String> managersname = new  HashMap<BigInteger,String>();
                	for(IWorker worker: workers) {
                		managersname.put(worker.getID(), worker.getLastName());             	
                	}
                	for (IOffice office: offices) {
                		if (office.getManagerID() != null ) {
            				if (access.getWorkerByID(office.getManagerID()) == null) {
            					office.setManagerID(null);
            				}
            			}
                	}
                    req.getSession().setAttribute("o_offices", offices);
                    req.getSession().setAttribute("o_manager", managersname);
                    try {
                      if (req.getParameter("select") == null) {
                    	  if(req.getParameter("sort") == null) {
                    		  resp.sendRedirect("index.jsp?action_id=view_office");
                    	  } else {
                    		  resp.sendRedirect("index.jsp?action_id=view_office&sort="+req.getParameter("sort"));
                    	  }
                    	} else {
                    		if(req.getParameter("sort") == null) {
                    			resp.sendRedirect("index.jsp?action_id=view_office&select=true");
                    		} else {
                    			resp.sendRedirect("index.jsp?action_id=view_office&select=true&sort="+req.getParameter("sort"));
                    		}
                    	}
                    } catch (IOException e) {
                        log.error("can't redirect on the showoffices.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                } return;
            }
    }
}

package ua.ivanchenko.eman.processors;
import java.io.IOException;
import java.math.BigInteger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.ivanchenko.eman.exceptions.ConfigLoaderException;
import ua.ivanchenko.eman.exceptions.DataAccessException;
import ua.ivanchenko.eman.model.IDataAccessor;
import ua.ivanchenko.eman.model.IJob;
import ua.ivanchenko.eman.model.Job;
public class EditJobActionProcessor implements ActionProcessor {
	private Logger log = Logger.getLogger("emanlogger");
	/**
     * method processes the request from user and generate response
     * @param req it's request
     * @param resp it's response
     * @throws ConfigLoaderException  when got incorrect configs file.
     * @throws DataAccessException when can't access to data.
     */
    public void process(HttpServletRequest req, HttpServletResponse resp, IDataAccessor access) throws DataAccessException {
    	log.info("EditJobActionProcessor{action_id:"+req.getParameter("action_id")+"} {URI:"+req.getRequestURI()+"}");
        if("edit_job_add".equalsIgnoreCase(req.getParameter("action_id"))) {            
        	if(req.getMethod().equalsIgnoreCase("GET")) {
    			try {
					resp.sendRedirect("editjob.jsp?action_id=edit_job_add");
				} catch (IOException e) {
					log.error("can't redirect on the editjob.jsp",e);
				}
    		} else {
        		try {
                    IJob job  = new Job();
                    job.setTitle(req.getParameter("title"));
                    job.setDescription(req.getParameter("desc"));
                    access.addJob(job);
                    try {
                        resp.sendRedirect("index.jsp?action_id=view_job");
                    } catch (IOException e) {
                        log.error("can't redirect on the showjobs.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                }
    		}
        } else if ("edit_job_update".equalsIgnoreCase(req.getParameter("action_id"))) {
            	log.info("EditJobActionProcessor (update){title:"+req.getParameter("title")+"} {id:"+req.getParameter("id")+"}");
            		if(req.getMethod().equalsIgnoreCase("GET")) {
            			IJob job = access.getJobByID(new BigInteger(req.getParameter("id")));
            			req.getSession().setAttribute("e_job", job);
            			try {
							resp.sendRedirect("editjob.jsp?action_id=edit_job_update");
						} catch (IOException e) {
							log.error("can't redirect on the editjob.jsp",e);
						}
            		} else {
		                try {
		                    IJob job  = new Job();
		                    job.setID(new BigInteger(req.getParameter("id")));
		                    job.setTitle(req.getParameter("title"));
		                    job.setDescription(req.getParameter("desc"));
		                    access.updateJob(job);
		                    try {
		                        resp.sendRedirect("index.jsp?action_id=view_job");
		                    } catch (IOException e) {
		                        log.error("can't redirect on the showjobs.jsp",e);
		                    }
		                } catch (DataAccessException e) {
		                    log.error("can't gets data from IDataAccessor",e);
		                } 
            		}
            } else if ("edit_job_remove".equalsIgnoreCase(req.getParameter("action_id")))  {
                try {                	
                    access.removeJob(new BigInteger(req.getParameter("id")));
                	resp.sendRedirect("index.jsp?action_id=view_job");
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                    try {
						resp.sendRedirect("error.jsp?error_id="+e.getMessage());
					} catch (IOException e1) {
						log.error("can't redirect on the showjobs.jsp",e);
					}
                } catch (IOException e) {
                    log.error("can't redirect on the showdepts.jsp",e);
                }
            }
    }
}

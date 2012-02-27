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
    public void process(HttpServletRequest req, HttpServletResponse resp, IDataAccessor access) throws DataAccessException, ConfigLoaderException {
    	log.info("EditJobActionProcessor{action_id:"+req.getParameter("action_id")+"} {URI:"+req.getRequestURI()+"}");
        if("edit_job_add".equals(req.getParameter("action_id"))) {            
        	if(req.getParameter("title")== null) {
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
        } else if ("edit_job_update".equals(req.getParameter("action_id"))) {
            	log.info("EditJobActionProcessor (update){title:"+req.getParameter("title")+"} {id:"+req.getParameter("id")+"}");
            		if(req.getParameter("title")== null) {
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
            } else if ("edit_job_remove".equals(req.getParameter("action_id")))  {
                try {
                	log.info("remove job: size ="+ access.getWorkerByJobID(new BigInteger(req.getParameter("id"))).size());
                	if (access.getWorkerByJobID(new BigInteger(req.getParameter("id"))).size() == 0 ) {
                		access.removeJob(new BigInteger(req.getParameter("id")));
                	} else {
                		throw new DataAccessException("Cannot remove job becose job is use");
                	}
                    try {
                        resp.sendRedirect("index.jsp?action_id=view_job");
                    } catch (IOException e) {
                        log.error("can't redirect on the showdepts.jsp",e);
                    }
                } catch (DataAccessException e) {
                    log.error("can't gets data from IDataAccessor",e);
                    try {
						resp.sendRedirect("error.jsp?error_id="+e.getMessage());
					} catch (IOException e1) {
						log.error("can't redirect on the showjobs.jsp",e);
					}
                }
            }
    }
}

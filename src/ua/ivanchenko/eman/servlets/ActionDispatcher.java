package ua.ivanchenko.eman.servlets;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.ivanchenko.eman.exceptions.ConfigLoaderException;
import ua.ivanchenko.eman.exceptions.CreateProcessorException;
import ua.ivanchenko.eman.exceptions.DataAccessException;
import ua.ivanchenko.eman.model.ConfigLoader;
import ua.ivanchenko.eman.model.DataAccessor;
import ua.ivanchenko.eman.model.IConfig;
import ua.ivanchenko.eman.model.IDataAccessor;
import ua.ivanchenko.eman.processors.ActionProcessor;
import ua.ivanchenko.eman.processors.ActionProcessorsFactory;
/**
 * Servlet implementation class ActionDispatcher
 */
public class ActionDispatcher extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger("emanlogger");       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActionDispatcher() {
        super();
    }
	/**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
        	log.info("do get");
			IConfig config = (IConfig) getServletContext().getAttribute("config_class");
			IDataAccessor accessor = DataAccessor.getInstance(config);
			ActionProcessorsFactory apf = new ActionProcessorsFactory();
			ActionProcessor proc = apf.getProcessor(request.getParameterValues("action_id")[request.getParameterValues("action_id").length-1],config);
			log.info("doGet{action_id:"+request.getParameterValues("action_id")[request.getParameterValues("action_id").length-1]+"} {URI:"+request.getRequestURI()+"} {id:"+request.getParameter("id")+"} query string:"+ request.getQueryString());
			log.info(" query string:"+ request.getQueryString());
			proc.process(request, response, accessor);
		} catch (CreateProcessorException e) {
			log.error("can't create the processor ", e);
			response.sendRedirect("error.jsp");
		} catch (DataAccessException e) {
			log.error("can't get access to the processor ", e);
			response.sendRedirect("error.jsp");
		}
    }
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			log.info("do post");
			IConfig config = (IConfig) getServletContext().getAttribute("config_class");
			IDataAccessor accessor = DataAccessor.getInstance(config);
			ActionProcessorsFactory apf = new ActionProcessorsFactory();
			ActionProcessor proc = apf.getProcessor(request.getParameterValues("action_id")[request.getParameterValues("action_id").length-1],config);
			proc.process(request, response, accessor);
			log.info((String) request.getParameter("hidden"));
		} catch (CreateProcessorException e) {
			log.error("can't create the processor ", e);
			response.sendRedirect("error.jsp");
		} catch (DataAccessException e) {
			log.error("can't get access to the processor ", e);
			response.sendRedirect("error.jsp");
		} 
	}
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);		
		IConfig conf =(IConfig) config.getServletContext().getAttribute("config_class");
		if (conf == null) {
			String config_path = config.getServletContext().getRealPath("/") + config.getInitParameter("config");
			IConfig config_file;
			try {
				config_file = ConfigLoader.loadConfig(new File(config_path));
			} catch (ConfigLoaderException e) {
				log.error("can't load config file",e);
				throw new ServletException("can't load config file",e);
			}
			log.info("config file :" + config);
			config.getServletContext().setAttribute("config_class", config_file);
		}
		
	}
}

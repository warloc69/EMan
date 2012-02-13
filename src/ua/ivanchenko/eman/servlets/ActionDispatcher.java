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
import ua.ivanchenko.eman.model.IConfig;
import ua.ivanchenko.eman.processors.ActionProcessorsFactory;

/**
 * Servlet implementation class ActionDispatcher
 */
public class ActionDispatcher extends HttpServlet {
    private static final long serialVersionUID = 1L;
	public static String CONFIG_FILE = "config"+System.getProperty("file.separator")+"config.properties";
	private Logger log = Logger.getLogger("<appname>logger");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActionDispatcher() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("hi hi hi");
        log.error(CONFIG_FILE);
    }
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IConfig config = null;
		log.info((String) request.getParameter("hidden"));		
		try {
			config = ConfigLoader.loadConfig(new File(ActionDispatcher.CONFIG_FILE));
		} catch (ConfigLoaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			try {
				new ActionProcessorsFactory().getProcessor(request.getParameter("action_id"),config).process(request, response);
			} catch (DataAccessException e) {
				log.error("can't get access to the processor ", e);
				response.sendRedirect("error.jsp");
			} catch (ConfigLoaderException e) {
				log.error("can't get config file ", e);
				response.sendRedirect("error.jsp");
			}
		} catch (CreateProcessorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		CONFIG_FILE =  config.getServletContext().getRealPath("/") + CONFIG_FILE;
		
	}
}

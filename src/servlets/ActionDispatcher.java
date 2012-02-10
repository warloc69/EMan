package servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ConfigLoader;
import model.IConfig;

import processors.ActionProcessorFactory;


/**
 * Servlet implementation class ActionDispatcher
 */
public class ActionDispatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String configfile = "";
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ActionDispatcher.class);   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActionDispatcher() {
        super();
        // TODO Auto-generated constructor stub
    }
    public  void init(ServletConfig config) throws ServletException {
    	config.getInitParameter("configfile");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.getWriter().println("hi");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			IConfig config = new ConfigLoader().loadConfig(new File(configfile));
			new ActionProcessorFactory().getProcessor(request.getParameter("action_id"),config).process(request, response);
		} catch (ClassNotFoundException e) {
			log.error("can't creats processor",e);
		}
	}

}

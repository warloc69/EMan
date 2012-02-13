package ua.ivanchenko.eman.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

/**
 * Application Lifecycle Listener implementation class ApplicationServletContextListener
 *
 */
public class ApplicationServletContextListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ApplicationServletContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event) {
    	ServletContext ctx = event.getServletContext();        
    	String prefix =  ctx.getRealPath("/");    
    	String file = "config"+System.getProperty("file.separator")+"log4j.properties";
    	PropertyConfigurator.configure(prefix+file);
    	System.out.println("Log4J Logging started for application: " + prefix+file); 
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}

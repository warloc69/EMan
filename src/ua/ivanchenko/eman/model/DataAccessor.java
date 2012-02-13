package ua.ivanchenko.eman.model;

import java.io.File;

import org.apache.log4j.Logger;

import ua.ivanchenko.eman.exceptions.ConfigLoaderException;
import ua.ivanchenko.eman.exceptions.DataAccessException;
import ua.ivanchenko.eman.servlets.ActionDispatcher;

public class DataAccessor {
    private static IDataAccessor inst = null; 
    private static Logger log = Logger.getLogger("<appname>logger");
    public static synchronized IDataAccessor getInstance() throws DataAccessException, ConfigLoaderException  {
        if ( inst == null) {
        	
        	String accessor = ConfigLoader.loadConfig(new File(ActionDispatcher.CONFIG_FILE)).getParameter("data_accessor");
        	log.info("DataAccessor accessor: "+ accessor);
        	try {
				Class<?> c = Class.forName(accessor);
				try {
					Object	obj = c.newInstance();
				if (obj instanceof IDataAccessor) {
                    return (IDataAccessor) obj;
                }
				} catch (InstantiationException e) {
					log.error("Can't get instance for data accessor: "+ accessor,e);
					throw new DataAccessException("Can't get instance for data accessor: "+ accessor,e);
				} catch (IllegalAccessException e) {
					log.error("illegal access to the accessor: "+ accessor,e);
					throw new DataAccessException("illegal access to the accessor: "+ accessor,e);
				}
   			} catch (ClassNotFoundException e) {
	   			 log.error("class not found, accessor: "+ accessor,e);
	             throw new DataAccessException("class not found, accessor: "+ accessor,e);
			}
        }
        return inst;
    }
}

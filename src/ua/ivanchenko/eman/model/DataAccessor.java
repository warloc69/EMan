package ua.ivanchenko.eman.model;

import org.apache.log4j.Logger;

import ua.ivanchenko.eman.exceptions.DataAccessException;

public class DataAccessor {
    private static IDataAccessor inst = null; 
    private static Logger log = Logger.getLogger("emanlogger");
    public static synchronized IDataAccessor getInstance(IConfig config) throws DataAccessException  {
        if ( inst == null) {
        	String accessor = config.getParameter("data_accessor");
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

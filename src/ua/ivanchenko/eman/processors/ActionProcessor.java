package ua.ivanchenko.eman.processors;
import javax.servlet.http.*;
import ua.ivanchenko.eman.exceptions.ConfigLoaderException;
import ua.ivanchenko.eman.exceptions.DataAccessException;
import ua.ivanchenko.eman.model.IDataAccessor;
/**
* Interface presents access to the some objects that processes the request.
*/
public interface ActionProcessor {
    /**
     * method processes the request from user and generate response
     * @param req it's request
     * @param resp it's response
     * @throws ConfigLoaderException 
     * @throws DataAccessException 
     */
    public void process(HttpServletRequest req, HttpServletResponse resp, IDataAccessor accessor) throws DataAccessException, ConfigLoaderException;
}
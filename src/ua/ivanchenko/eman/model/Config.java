package ua.ivanchenko.eman.model;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
/**
 * Class parses config properties.
 */
public class Config implements IConfig {
    private HashMap<String,String> map = new HashMap<String,String>();
    /**
     * parses properties
     */
    public void addFromProp(Properties p) {
        Enumeration<?> listname = p.propertyNames();
        while (listname.hasMoreElements()) {
            String key = (String)listname.nextElement();
            map.put(key,p.getProperty(key));            
        }
    }
    /**
     * return parameter value
     */
    public String getParameter(String key) {
        return map.get(key);
    }
    /**
     * return action value.
     */
    public String getProcessorImplByAction(String actionShortName) {
        return map.get(actionShortName);
    }
    

    
}

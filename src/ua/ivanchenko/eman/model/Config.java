package ua.ivanchenko.eman.model;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

public class Config implements IConfig {
    private HashMap<String,String> map = new HashMap<String,String>();
    
    public void addFromProp(Properties p) {
        Enumeration<?> listname = p.propertyNames();
        while (listname.hasMoreElements()) {
            String key = (String)listname.nextElement();
            map.put(key,p.getProperty(key));            
        }
    }

    public String getParameter(String key) {
        return map.get(key);
    }

    public String getProcessorImplByAction(String actionShortName) {
        return map.get(actionShortName);
    }
    

    
}

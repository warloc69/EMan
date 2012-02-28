package ua.ivanchenko.eman.model;

import java.util.Properties;
/**
 * Interface represent  methods for the config properties.
 */
public interface IConfig {
	/**
     * parses properties
     */
    public void addFromProp(Properties p);
    /**
     * return parameter value
     */
    public String getParameter(String key);
    /**
     * return action value.
     */
    public String getProcessorImplByAction(String actionShortName);
}

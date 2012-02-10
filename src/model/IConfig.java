package model;

import java.util.Properties;

public interface IConfig {
	public void addFromProp(Properties p);
	public String getParameter(String key);
	public String getProcessorImplByAction(String actionShortName);
}

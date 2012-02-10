package processors;
import model.IConfig;

public class ActionProcessorFactory {
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ActionProcessorFactory.class);
	public ActionProcessor getProcessor(String actionShortName, IConfig config) throws ClassNotFoundException {	
		String actionProcessor = config.getProcessorImplByAction(actionShortName);
		if (actionProcessor != null) {
			Class<?> c = Class.forName(actionProcessor);
			try {
				Object obj = c.newInstance();
				if (obj instanceof ActionProcessor) {
					return (ActionProcessor) obj;
				}
			} catch (InstantiationException e) {
				log.error(e);
			} catch (IllegalAccessException e1) {
				log.error(e1);
			}
		} 
		 return new DefaultActionProcessor();		
	}
}

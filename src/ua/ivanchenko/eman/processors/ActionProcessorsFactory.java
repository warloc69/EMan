package ua.ivanchenko.eman.processors;
import org.apache.log4j.Logger;

import ua.ivanchenko.eman.exceptions.CreateProcessorException;
import ua.ivanchenko.eman.model.IConfig;

public class ActionProcessorsFactory {
	private Logger log = Logger.getLogger("<appname>logger");
    public ActionProcessor getProcessor(String actionShortName, IConfig config) throws CreateProcessorException {    
        String actionProcessor = config.getProcessorImplByAction(actionShortName);
        if (actionProcessor != null) {
            Class<?> c;
			try {
				c = Class.forName(actionProcessor);
			} catch (ClassNotFoundException e2) {
				 log.error("class not found, processor: "+ actionProcessor,e2);
	             throw new CreateProcessorException("class not found, processor: "+ actionProcessor,e2);
			}
            try {
                Object obj = c.newInstance();
                if (obj instanceof ActionProcessor) {
                	log.info("create processor :"+ actionProcessor);
                    return (ActionProcessor) obj;
                }
            } catch (InstantiationException e) {
                log.error("Can't get instance for processor:"+ actionProcessor,e);
                throw new CreateProcessorException("Can't get instance for the processor:"+ actionProcessor,e);
            } catch (IllegalAccessException e1) {
                log.error("illegal access to the processor:"+ actionProcessor,e1);
                throw new CreateProcessorException("illegal access to the processor:"+ actionProcessor,e1);
            }
        } 
         return new DefaultActionProcessor();        
    }
}

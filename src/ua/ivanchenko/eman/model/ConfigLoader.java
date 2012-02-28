package ua.ivanchenko.eman.model;
/**
 * Class load config file.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import ua.ivanchenko.eman.exceptions.ConfigLoaderException;
public class ConfigLoader {
	public static IConfig loadConfig(File f) throws ConfigLoaderException {
        Properties prop = new Properties();
        try {
			prop.load(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			throw new ConfigLoaderException("Config file not found",e);
		} catch (IOException e) {
			throw new ConfigLoaderException("Input/Output error for loads config file",e);
		}
        IConfig conf = new Config();
        conf.addFromProp(prop);
        return conf;
    }
}

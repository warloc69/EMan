package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
public class ConfigLoader {
	public IConfig loadConfig(File f) throws FileNotFoundException, IOException {
		Properties prop = new Properties();
		prop.load(new FileInputStream(f));
		IConfig conf = new Config();
		conf.addFromProp(prop);
		return conf;
	}
}

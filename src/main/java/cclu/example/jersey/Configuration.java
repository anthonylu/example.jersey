package cclu.example.jersey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configuration {
	private static final Logger log = LoggerFactory.getLogger(Configuration.class);
	private final Properties property;
	public Configuration() {
		property = new Properties();
		try {
			property.load(this.getClass().getClassLoader().getResourceAsStream("default.properties"));
		} catch (IOException e) {
			log.error("Fail to read default.properties", e);
		}
	}
	
	private String getProperty(String key, String defaultValue) {
		return property.getProperty(key, defaultValue);
	}
	
	public String getString(String key, String defaultValue) {
		return getProperty(key, defaultValue);
	}
	
	public boolean getBoolean(String key, boolean defaultValue) {
		String value = getProperty(key, "");
		if (value.isEmpty()) return defaultValue;
		else return Boolean.valueOf(value);
	}
	
	public long getLong(String key, long defaultValue) {
		String value = getProperty(key, "");
		if (value.isEmpty()) return defaultValue;
		else {
			try {
				return Long.valueOf(value);
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
	}
	
	public int getInt(String key, int defaultValue) {
		String value = getProperty(key, "");
		if (value.isEmpty()) return defaultValue;
		else {
			try {
				return Integer.valueOf(value);
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
	}
	
	public float getFloat(String key, float defaultValue) {
		String value = getProperty(key, "");
		if (value.isEmpty()) return defaultValue;
		else {
			try {
				return Float.valueOf(value);
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
	}
	
	public double getDouble(String key, double defaultValue) {
		String value = getProperty(key, "");
		if (value.isEmpty()) return defaultValue;
		else {
			try {
				return Double.valueOf(value);
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
	}
	
	public List<String> getList(String key, String sep) {
		String values = getProperty(key, "");
		List<String> result = new ArrayList<String>();
		if (values.isEmpty()) return result;
		else {
			for (String value:values.split(sep)) {
				result.add(value);
			}
			return result;
		}
	}
}

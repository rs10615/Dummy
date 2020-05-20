package com.dummy.ids;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GdprcpirConfiguration {
	public static Properties props = new Properties();
	final static Logger LOGGER = LoggerFactory.getLogger(GdprcpirConfiguration.class);
	
	static{
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor(); 
		encryptor.setPassword("arc"); 
		props = new EncryptableProperties(encryptor);
		InputStream inStream = GdprcpirConfiguration.class.getResourceAsStream("/application.properties");
		try {
			loadConfigurations(inStream);
		} catch (IOException ex) {
			String errMsg = "Exception in loading configuration file. Please check if application.properties file is present in classpath.";
			ExceptionUtils.throwRuntimeException(errMsg, ex, LOGGER);
		}
	}
	
	public static void loadConfigurations(InputStream inputStream) throws IOException{
		props.load(inputStream);
	}
}

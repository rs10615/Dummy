package com.dummy.ids;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





public class ConnectionFactory {

    public static final Logger logger = LoggerFactory.getLogger(ConnectionFactory.class.getName());
    
    public static Connection getConnection(Adapter adapter) {
        
        Properties prop = adapter.getProperty();
        
        logger.debug("Property " + prop);
        
        Connection conn = null;
        String driver = null;
        String url =null;
       
        String dbName = prop.getProperty("database");
       
        if(adapter instanceof HiveAdapter) {
            driver = "org.apache.hive.jdbc.HiveDriver";
            //System.out.println(prop.getProperty("local"));
            if(Boolean.valueOf(prop.getProperty("local"))) {
                url = prop.getProperty("url1");
                url = "jdbc:hive2://" + url + "/" + dbName ;
           }else {
        	   url = prop.getProperty("url");
        	   url = "jdbc:hive2://" + url + "/" + dbName + ";principal=hive/" + url.split(":")[0] + "";
           }
           
        }
       
            
        String userName = prop.getProperty("username");
        String password = prop.getProperty("password");

        try
        {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, userName, password);
          
            
			/*
			 * if(adapter instanceof HiveAdapter) { PreparedStatement prep =
			 * conn.prepareStatement("SET hive.server2.logging.operation.enabled=true");
			 * prep.execute(); }
			 */

        }
        catch (Exception e)
        {
            logger.error("Cannot make Connection : Please check configuration - application.properties", e);
            System.exit(0);
        }
        
        return conn;
        
    }
}

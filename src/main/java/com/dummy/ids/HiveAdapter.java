package com.dummy.ids;

import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class HiveAdapter implements Adapter {

    public static final Logger logger = LoggerFactory.getLogger(HiveAdapter.class.getName());
    private Properties properties;
    
    public HiveAdapter() {
        try {
            init();
        } catch (Exception e) {

            logger.error("Exeption during Hive Adapter Initializing ", e);
            System.exit(0);
        }
    }
    
    public void init() throws Exception {

        this.properties = new PropertiesLoader().getProperties();
        this.properties = generatePropertyDetailer(CONNECTION.HIVE, this.properties);
        
    }

    public Properties getProperty() {

        return this.properties;
    }
    
    public Properties generatePropertyDetailer(CONNECTION type, Properties properties) {
        
        Properties property = new Properties();
        Set<Object> keys = properties.keySet();
        
        for(Object key : keys) {
            
            if(key.toString().toLowerCase().startsWith(type.toString().toLowerCase()) ) {
                String new_key = key.toString().indexOf(type.toString().toLowerCase() + Constants.DOT)!=-1 ? key.toString().substring((type.toString().toLowerCase() + Constants.DOT).length(), key.toString().length()):key.toString();
                property.put(new_key , properties.getProperty(key.toString()));
            } else 
                property.put(key, properties.get(key));
            
        }
        
        return property;
    }

   
}

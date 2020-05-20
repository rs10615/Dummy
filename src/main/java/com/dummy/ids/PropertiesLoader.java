package com.dummy.ids;

import java.util.Properties;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesLoader {

    public static final Logger logger = LoggerFactory.getLogger(PropertiesLoader.class.getName());
    private static Properties prop = null;
    
    public Properties getProperties(){
        
        if(prop != null)
            return prop;
        
        prop = GdprcpirConfiguration.props;
        return prop;
    }
    
}

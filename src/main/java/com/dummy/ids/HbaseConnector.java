package com.dummy.ids;

import java.io.IOException;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class HbaseConnector {

	public static final Logger LOGGER = LoggerFactory.getLogger(HbaseConnector.class.getName());
	private Properties properties;
	
	public HbaseConnector() {
		
		 this.properties = new PropertiesLoader().getProperties();
	}

	public Connection getConnection() {
		
		LOGGER.info("Initiating Connection to Hbase " );
		Configuration conf = HBaseConfiguration.create();
		conf.set(
				"hbase.zookeeper.quorum",
				properties.getProperty(Constants.ZOOKEEPER_QUORUM));
		conf.set("hadoop.security.authorization", "true");
		conf.set("fs.defaultFS", properties.getProperty(Constants.HBASE_DEFAULT_FS));

		conf.set("fs.hdfs.impl", DistributedFileSystem.class.getName());

		conf.set("hadoop.security.authentication", "Kerberos");
		conf.set("hbase.security.authentication", "kerberos");
		conf.set("hbase.cluster.distributed", "true");
		conf.set("hbase.master.kerberos.principal",
				"");
		conf.set("hbase.regionserver.kerberos.principal",
				"");

		UserGroupInformation.setConfiguration(conf);

		try {
			UserGroupInformation.loginUserFromKeytab(
					"",
					properties.getProperty(Constants.KEYTAB_ABSOLUTE_PATH));

			Connection con = ConnectionFactory.createConnection(conf);
			
			LOGGER.info("Connection Initiated Successfully " + con);
			
			return con;

		} catch (IOException e) {
			LOGGER.error("Exception while getting Connection " + conf , e);
		}

		
		return null;
	}
	




}


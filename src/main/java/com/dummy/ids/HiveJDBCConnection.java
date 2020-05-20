package com.dummy.ids;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

public class HiveJDBCConnection {

	private static String driverName = "org.apache.hive.jdbc.HiveDriver";
	private static String JDBC_URL = "";

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		conf.set("hadoop.security.authentication", "kerberos");
		UserGroupInformation.setConfiguration(conf);
		URL file = HiveJDBCConnection.class.getClassLoader().getResource("");
		UserGroupInformation.loginUserFromKeytab("", file.getPath());

		// To authenticate using a keytab, use following
		//UserGroupInformation.loginUserFromKeytab("idscah@ADOBENET.GLOBAL.ADOBE.COM", "/etc/security/keytabs/idscah.keytab");
		
		// To authenticate using local cache (that gets initialized from kinit command), use this
		UserGroupInformation.loginUserFromSubject(null);
		UserGroupInformation.setLoginUser(UserGroupInformation.createRemoteUser("idscah"));

		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Hive JDBC Driver: " + driverName + "didn't find, please make sure it is in classpath");
		}



		Connection con = DriverManager.getConnection(JDBC_URL);

		Statement stmt = con.createStatement();
		ResultSet res = null;


		stmt.execute("use gdpr");


		// show tables
		String sql = "show tables";
		System.out.println("Running: " + sql);
		res = stmt.executeQuery(sql);

		while (res.next()) {
			System.out.println(res.getString(1));
		}

		res.close();
		stmt.close();
		con.close();
	}

}
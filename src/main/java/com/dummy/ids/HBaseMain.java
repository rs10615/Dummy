package com.dummy.ids;

import java.io.IOException;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

public class HBaseMain {
	private static final Logger LOGGER=LoggerFactory.getLogger(HBaseMain.class);
	public static void main(String[] args) throws IOException {
		LOGGER.info("Hbase scanning started for testhb table ");
		HbaseConnector hconn=new HbaseConnector();
		Scan scan = new Scan();
		scan.setCaching(20);
		scan.addFamily(Bytes.toBytes(Constants.cf));
		TableName tableName = TableName.valueOf(Constants.TABLE_NAME);
		Connection conn = hconn.getConnection();
		Table table = conn.getTable(tableName);
		ResultScanner scanner = table.getScanner(scan);
		LOGGER.info("table scaned");
		for (Result result = scanner.next(); (result != null); result = scanner.next()) {
	        for(Cell cell : result.rawCells()) {
	        	byte[] row = CellUtil.cloneRow(cell);
	        	byte[] family = CellUtil.cloneFamily(cell);
		        byte[] column = CellUtil.cloneQualifier(cell);
		        byte[] value = CellUtil.cloneValue(cell);	           
		        LOGGER.info("Row Key: "+ Bytes.toString(row)+" ---Family: "+ Bytes.toString(family)+" --- Qualifier: " + Bytes.toString(column) + " : Value : " + Bytes.toBoolean(value));
	        }
	    }
	}
}

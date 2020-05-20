package com.dummy.ids;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Main {
	static Properties config = GdprcpirConfiguration.props;
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	static Adapter ADAPTER = new HiveAdapter();
	static Connection connection = ConnectionFactory.getConnection(ADAPTER);
	public static void main(String[] args) throws SQLException {
		List<DeleteRecord> listDel = new ArrayList<>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar start = Calendar.getInstance();
		// start.add(Calendar.DATE, 1); String startDate =
		String date = formatter.format(start.getTime());
		String batch = config.getProperty(Constants.HIVE_BATCH_LIMIT);
		String SingleBatch = null;
		if (args[0] != null & args[1] != null & args[2] != null) {
			batch = args[0];
			SingleBatch = args[2];
		}
		int batchSize = Integer.valueOf(batch);
		for (int i = 0; i < batchSize; i++) {
			DeleteRecord record = new DeleteRecord("abc", "1234565", "ma@ha.com", "table@1234565");
			listDel.add(record);
		}
		LOGGER.info("# of records   ==> " + batchSize + " to be procced  in batches of ==> " + SingleBatch);
		//LOGGER.info("Loading gdpr delete Requests into hive.");
		boolean jdbc = Boolean.valueOf(args[1]);
		if (jdbc) {
			long recordSize = listDel.size();
			int remaining_object = -1;
			int singleBatch = Integer.valueOf(SingleBatch);
			int batchNum = -2;
			int index = 0;
			if (batchSize > singleBatch) {
				batchNum = (int) Math.floor((double) recordSize / singleBatch);
				remaining_object = batchSize % singleBatch;
				if (batchNum > 0) {
					for (int l = 0; l < batchNum; l++) {
						StringBuffer sqlString = new StringBuffer("INSERT INTO "
								+ config.getProperty(Constants.HIVE_GDPR_DB) + ".del_req_guid PARTITION(req_dts='"
								+ date + "') (req_id,user_id,email,table_guid)  VALUES (?,?,?,?)");
						for (int i = 0; i < singleBatch - 1; i++) {
							sqlString.append(", (?,?,?,?)");
						}
						PreparedStatement stmt = connection.prepareStatement(sqlString.toString());
				
						// PreparedStatement stmt = connection.prepareStatement(sqlString);
						int i = 0;
						for (int k = 0; k < singleBatch; k++) {
							// LOGGER.info("count for number of object to be inserted = " +k );
							DeleteRecord request = listDel.get(index);
							// stmt.setString(1, request.getReq_dts());
							stmt.setString(++i, request.getReq_id());
							stmt.setString(++i, request.getUser_id());
							stmt.setString(++i, request.getEmail());
							stmt.setString(++i, request.getTable_guid());
							index++;
							// stmt.addBatch();
						}
						i = 0;
						Instant startTime = null;
						try {
							startTime = Instant.now();
							LOGGER.info("Processing the batch & start time is " + startTime);
							stmt.executeUpdate();
							
							
						} catch (SQLException e) {
							if (e.getMessage().contains("unique constraint violated")) {
								LOGGER.warn(
										"Unique constraint violated while inserting the record. Probably, a duplicate Record Found. Skipping the record");
							} else {
								throw e;
							}
						} finally {
							Instant finishFinish = Instant.now();
							long timeElapsed = Duration.between(startTime, finishFinish).getSeconds();
							LOGGER.info("# of records  " + singleBatch + "  time  in sec  " + timeElapsed);
							// connection.close();
						}
					}
				}
			}
			StringBuffer sqlString = new StringBuffer(
					"INSERT INTO " + config.getProperty(Constants.HIVE_GDPR_DB) + ".del_req_guid PARTITION(req_dts='"
							+ date + "') (req_id,user_id,email,table_guid)  VALUES (?,?,?,?)");
			if (remaining_object < 0) {
				remaining_object = singleBatch;
			}
			if (remaining_object > 0) {
				for (int i = 0; i < remaining_object - 1; i++) {
					sqlString.append(", (?,?,?,?)");
				}
				PreparedStatement stmt = connection.prepareStatement(sqlString.toString());
				// PreparedStatement stmt = connection.prepareStatement(sqlString);
				int i = 0;
				for (int k = 0; k < remaining_object; k++) {
					// LOGGER.info("count for number of object to be inserted = " +k );
					DeleteRecord request = listDel.get(index);
					// stmt.setString(1, request.getReq_dts());
					stmt.setString(++i, request.getReq_id());
					stmt.setString(++i, request.getUser_id());
					stmt.setString(++i, request.getEmail());
					stmt.setString(++i, request.getTable_guid());
					index++;
					// stmt.addBatch();
				}
				Instant startTime = null;
				try {
					startTime = Instant.now();
					LOGGER.info("Start time of processing the records into hive  " + startTime);
					
					
							
					stmt.executeUpdate();
					//stmt.execute("SET hive.server2.logging.operation.enabled=true");
					
					
					LOGGER.info("End time after the records processed into hive" + Instant.now());
				} catch (SQLException e) {
					if (e.getMessage().contains("unique constraint violated")) {
						LOGGER.warn(
								"Unique constraint violated while inserting the record. Probably, a duplicate Record Found. Skipping the record");
					} else {
						throw e;
					}
				} finally {
					Instant finishFinish = Instant.now();
					long timeElapsed = Duration.between(startTime, finishFinish).getSeconds();
					LOGGER.info(String.format("Total time spent to process the %s records %s sec",batchSize,timeElapsed));
					// connection.close();
				}
			}
		} else {
			LocalSinkService localSink = new LocalSinkService();
			try {
				localSink.createConnection(Constants.INIT_FOLDER + "/" + CommonUtils.formatCurrentTimeStamp());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Instant startTime = Instant.now();
			int result = localSink.writeToLocal(listDel,true);
			Instant finishFinish = Instant.now();
			long timeElapsed = Duration.between(startTime, finishFinish).getSeconds();
			LOGGER.info("Time took in writing the records into to local " + SingleBatch + "  time taken   "
					+ timeElapsed + " sec");
			if (result == 0) {
				LOGGER.info(String.format("record %s has been written on disk sucessfully", listDel.size()));
				int flag = 0;
				// read records from local file
				String localPath = Constants.INIT_FOLDER;
				String[] fNames = CommonUtils.getFileNames(localPath);
				if (fNames == null) {
					LOGGER.error(
							"This is unexpected scenario. Application will be killed. Probable issue is during ingestion from Hoolihan.");
					throw new RuntimeException();
				}
				Set<DeleteRecord> collectRecords = new HashSet<>();
				LocalSourceService localSourceService = new LocalSourceService();
				try {
					for (int i = 0; i < fNames.length; i++) {
						localSourceService.createConnection(localPath + "/" + fNames[i]);
						collectRecords.addAll(localSourceService.readRecordsFromFile());
						localSourceService.closeConnection();
					}
					HdfsSinkService hdfsSinkServ = new HdfsSinkService();
					// int clearTempRequestTableStatus =
					// hdfsSinkServ.clearDirectory(Constants.GDPR_HIVE_REQ_TEMP_PATH);
					Instant startTime1 = Instant.now();
					int copyStatus = hdfsSinkServ.copyLocalToHdfs(localPath, Constants.GDPR_HIVE_REQ_TEMP_PATH);
					Instant endTime1 = Instant.now();
					long timeElapsed1 = Duration.between(startTime1, endTime1).getSeconds();
					LOGGER.info("Time taken to copy the records from local to hdfs ===>>  " + timeElapsed1 + " sec");
					// LOGGER.info("No. of records = " + collectRecords.size());
					if (copyStatus == 0) {
						LOGGER.info("data ingested to stage table is successfull ,now time to merge the stage to final table");
						Instant startTime2 = Instant.now();
						Statement stmt = connection.createStatement();
						boolean resHivePropertyTest = stmt.execute("SET hive.exec.dynamic.partition = true");
						resHivePropertyTest = stmt.execute("SET hive.exec.dynamic.partition.mode = nonstrict");
						stmt.execute(
								"insert into table gdpr.del_req_guid PARTITION(req_dts) select * ,current_date() as req_dts from del_req_guid_stg");
						Instant endTime2 = Instant.now();
						long timeElapsed2 = Duration.between(startTime2, endTime2).getSeconds();
						LOGGER.info("Time taken to merge del_req_guid_tmp into del_req_guid ===>" + timeElapsed2
								+ " sec total records copied");
					} else {
						LOGGER.info("Unable to copy  the records into stage table");
					}
				} catch (Exception ee) {
					LOGGER.info("error encounter while copying the records to hdfs " + ee);
				} finally {
					connection.close();
				}
			}
		}
	}
}
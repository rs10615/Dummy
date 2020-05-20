package com.dummy.ids;

public class Constants {
public static enum CONNECTION{HIVE,HANA,MYSQL,HBASE}
	 public static final String DOT = ".";
	public static final String GDPR_KAF_SRC_TOPIC_NAME = "gdpr.kafka.topic.name";
	public static final String GDPR_KAF_SRC_SESSION_TIMEOUT = "gdpr.kafka.session.timeout";
	public static final String GDPR_KAF_SRC_KEY_DESERIAL = "gdpr.kafka.key.deserializer";
	public static final String GDPR_KAF_SRC_VALUE_DESERIAL = "gdpr.kafka.value.deserializer";
	public static final String GDPR_KAF_SRC_GROUP_ID = "gdpr.kafka.group.id";
	public static final String GDPR_KAF_SRC_BOOTSTRAP_SERVER = "gdpr.kafka.bootstrap.server";
	public static final String GDPR_KAF_AUTO_OFFSET_RESET = "gdpr.kafka.auto.offset.reset";
	public static final String GDPR_KAF_AUTO_COMMIT_FLAG = "gdpr.kafka.auto.commit";
	
	public static final String GDPR_KAF_PII_TOPIC_NAME = "gdpr.kafka.pii.topic.name";
	public static final String GDPR_KAF_PII_GROUP_ID = "gdpr.kafka.pii.group.id";
	
	// Kafka Default Properties
	public static final String KAFKA_DEFAULT_AUTO_COMMIT = "false";
	public static final String KAFKA_DEFAULT_OFFSET_RESET = "earliest";
	public static final String KAFKA_DEFAULT_TIMEOUT = "60000";
	public static final String KAFKA_DEFAULT_BOOTSTRAP_SERVER = "locahost:9092";
	public static final String KAFKA_STRING_DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";
	public static final String KAFKA_STRING_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";
	
	//Table Constants
	public static final String PII_COLS_TBL_GUID = "tbl_guid";
	public static final String PII_COLS_DB_NAME = "db_name";
	public static final String PII_COLS_DB_TYPE = "db_type";
	public static final String PII_COLS_TBL_NAME = "tbl_name";
	public static final String CHECKPOINT_DB_TYPE = PII_COLS_DB_TYPE;
	public static final String CHECKPOINT = "checkpoint";
	public static final String DB_TYPE_HANA = "HANA";
	public static final String DB_TYPE_HIVE = "HIVE";
	
	//Hive Connection Properties
	public static final String HIVE_URL = "hive.url";
	public static final String HIVE_USERNAME = "hive.username";
	
	//Hana Connection Properties
	public static final String HANA_URL = "hana.url";
	public static final String HANA_USERNAME = "hana.username";
	public static final String HANA_PASSWORD = "hana.password";
	
	//IMS API Properties
	public static final String IMS_URL = "ims.url";
	public static final String IMS_GRANT_TYPE = "ims.grant.type";
	public static final String IMS_CLIENT_SECRET = "ims.client.secret";
	public static final String IMS_CLIENT_ID = "ims.client.id";
	public static final String IMS_CODE = "ims.code";
	//RoQ parameters
	public static final String API_KEY = "api.key";
	public static final String ROQ_META_URL = "roq.meta.url";
	public static final String ROQ_EVENT_URL = "roq.event.url";
	//hive gdpr db name
	public static final String HIVE_GDPR_DB = "hive.database";
	//hana gdpr db name
	public static final String HANA_GDPR_DB = "hana.database";
	//principal for hive
	public static final String PRINCIPAL_HIVE = "principal.hive";
	//hive temp table path for req ingestion
	public static final String GDPR_HIVE_REQ_TEMP_PATH = "";	
	//hive temp table path for mapping ingestion
	public static final String GDPR_HIVE_MAPPING_TEMP_PATH = "";
	//delete requests init folder in local
	
	public static final String INIT_FOLDER = "gdpr.init.folder";
	
	public static final String ROQ_COUNT_FOLDER="roq.count.folder";		
	
	public static final String DONE_FOLDER = "gdpr.done.folder";
	public static final String LOG_DIR_PATH="log.dir.path";
	//hana batch insert limit
	public static final int HANA_BATCH_LIMIT = 10000;//10K
	public static final String PII_MERGE_LIMIT="pii.merge.limit";
	public static final String GDPR_FILTERS="gdpr.filters";
	public static final String CPIR_FILTERS="cpir.filters";
	public static final String OBJECTS_IN_FILES="object.in.file";
	/**  SMPT MAIL CONSTANTS*/
	public static final String MAIL_HOST="mail.smtp.host";
	public static final String MAIL_PORT="mail.smtp.port";
	public static final String MAIL_USERNAME="mail.smtp.username";
	public static final String MAIL_PASSWORD="mail.smtp.password";
	public static final String MAIL_FROM="mail.smtp.from";
	public static final String MAIL_TO="mail.smtp.to";
	public static final String HIVE_BATCH_LIMIT="batch.limit";
	public static final String HIVE_CONNECTION_PARAM="hive.local";
	public static final String HIVE_LOCAL_URL="hive.url1";
	public static final String HIVE_REMOTE_URL="hive.url";
	public static final String ZOOKEEPER_QUORUM = "zookeeper.quorum";
	public static final String HBASE_DEFAULT_FS = "hbase.default.fs";
	public static final String KEYTAB_ABSOLUTE_PATH = "keytab.abs.path";
	public static final String cf = "cf";
	public static final String splitDelim = "^";
	public static final int batchToProcess = 1000;
	public static final String TABLE_NAME = "testhb";

			
		
	
}

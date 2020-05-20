package com.dummy.ids;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.TimeZone;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CommonUtils {
	final static Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);



	public static String formatCurrentTimeStamp() {
		SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		dateFormatLocal.setLenient(true);
		dateFormatLocal.setTimeZone(TimeZone.getTimeZone("UTC"));
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		return dateFormatLocal.format(ts);
	}

	public static boolean FolderNotEmpty(String path) {

		File f = new File(path);
		File[] files = f.listFiles();
		if (files == null) {
			LOGGER.warn("The given path is not a directory - " + path);
			return false;
		}
		if (files.length > 0) {
			LOGGER.info("Folder - " + path + " is not empty.");
			return true;
		} else {
			LOGGER.info("Folder - " + path + " is empty.");
			return false;
		}

	}

	public static String[] getFileNames(String srcFolder) {
		File f = new File(srcFolder);
		File[] files = f.listFiles();
		if (files == null) {
			LOGGER.warn("The given path is not a directory - " + srcFolder);
			return null;
		}
	    return files.length > 0?f.list():null;
		
		
	}
}
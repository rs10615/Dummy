package com.dummy.ids;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalSinkService {
	final static Logger LOGGER = LoggerFactory.getLogger(LocalSinkService.class);
	private BufferedWriter bw = null;
	private FileWriter fw = null;
	private final String FIELD_SEPARATOR = "\u0001";

	public void createConnection(String path) throws IOException {

		fw = new FileWriter(path);
		bw = new BufferedWriter(fw);
	}

	public void closeConnection() {
		if (bw != null) {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (fw != null) {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public <T> int writeToLocal(Collection<DeleteRecord> delReqSet, boolean end) {
		Function<DeleteRecord, String> func = new Function<DeleteRecord, String>() {
			@Override
			public String apply(DeleteRecord req) {
				StringBuilder recordSb = new StringBuilder();
				recordSb.append(req.getReq_id()).append(FIELD_SEPARATOR).append(req.getUser_id())
						.append(FIELD_SEPARATOR).append(req.getEmail()).append(FIELD_SEPARATOR)
						
						.append(req.getTable_guid());
				return recordSb.toString();
			}
		};
		int flag = 0;
		try {
			final String NEW_LINE_CHAR = "\n";
			int i = 0;
			for (DeleteRecord req : delReqSet) {
				String record = func.apply(req);
				if (end) {
					record = (i == delReqSet.size() - 1) ? record : (record + NEW_LINE_CHAR);
				} else {
					record = record + NEW_LINE_CHAR;
				}
				bw.write(record);
				i++;
			}
		} catch (IOException ioe) {
			flag = 1;
			ioe.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
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

	public static boolean moveFiles(String srcFolder, String destFolder) {
		boolean moveSuccFlg = false;
		File f = new File(srcFolder);
		File[] files = f.listFiles();
		if (files == null) {
			LOGGER.warn("The given path is not a directory - " + srcFolder);
			return false;
		} else if (files.length > 0) {
			InputStream inStream = null;
			OutputStream outStream = null;
			String[] fNames = f.list();
			try {
				for (int i = 0; i < fNames.length; i++) {
					// move from src to dest
					File src = new File(srcFolder + "/" + fNames[i]);
					File dest = new File(destFolder + "/" + fNames[i]);
					inStream = new FileInputStream(src);
					outStream = new FileOutputStream(dest);
					byte[] buffer = new byte[1024];
					int length = 0;
					// copy the file content in bytes
					while ((length = inStream.read(buffer)) > 0) {
						outStream.write(buffer, 0, length);
					}
					inStream.close();
					outStream.close();
					// delete the original file
					src.delete();
					LOGGER.info("File - " + srcFolder + "/" + fNames[i] + " has been moved successfully.");
				}
				moveSuccFlg = true;
			} catch (FileNotFoundException foe) {
				moveSuccFlg = false;
				foe.printStackTrace();
			} catch (IOException ioe) {
				moveSuccFlg = false;
				ioe.printStackTrace();
			}
		} else {
			moveSuccFlg = true;
			LOGGER.info("Folder - " + srcFolder + " is empty. So no movement of files required.");
		}

		return moveSuccFlg;
	}

	public void totalRecordsOfCount(long count, String path) {

		try {
			this.createConnection(path + "/" + "hoolian_records");
			bw.write("" + count);
		} catch (IOException ioe) {

			LOGGER.error("Exception occurred during the writing the hoolian count " + ioe.getMessage());
		} finally {
			if (bw != null) {
				try {
					bw.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}

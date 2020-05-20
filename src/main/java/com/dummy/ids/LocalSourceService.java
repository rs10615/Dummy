package com.dummy.ids;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalSourceService {
	final static Logger LOGGER = LoggerFactory.getLogger(LocalSourceService.class);
	private BufferedReader br = null;
	private FileReader fr = null;
	private final String FIELD_SEPARATOR = "\u0001";

	public void createConnection(String path) throws IOException {
		fr = new FileReader(path);
		br = new BufferedReader(fr);
	}

	public void closeConnection() {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (fr != null) {
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Collection<DeleteRecord> readRecordsFromFile() {
		String rec = null;
		
		Set<DeleteRecord> delrecSet = new HashSet<>();
		
		try {
			while ((rec = br.readLine()) != null) {
				String[] fields = rec.split(FIELD_SEPARATOR);
				DeleteRecord delRec = new DeleteRecord();
				delRec.setReq_id(fields[0]);
				delRec.setUser_id(fields[1]);
				delRec.setEmail(fields[2]);
				//don't load timestamp field from file
				delRec.setTable_guid(fields[3]);;
				
				delrecSet.add(delRec);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return delrecSet;
	}

}

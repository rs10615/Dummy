package com.dummy.ids;

import java.io.BufferedInputStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.UUID;


import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.FileSystem;

import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HdfsSinkService {
	final static Logger LOGGER = LoggerFactory.getLogger(HdfsSinkService.class);	
	
	public static boolean fileExists(String path) {
		FileSystem fs = null;
		boolean flag = true;
		try {
			fs = FileSystem.get(new Configuration());
			flag = fs.exists(new Path(path));
		} catch (IllegalArgumentException | IOException e) {			
			e.printStackTrace();
		}finally {
			if(fs != null) {
				try {
					fs.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
	
	public static boolean moveFile(String src,String dest) {
		boolean moveSuccFlg = false;
		try {
			FileSystem fs = FileSystem.get(new Configuration());
			moveSuccFlg = fs.rename(new Path(src), new Path(dest + "/" + UUID.randomUUID().toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return moveSuccFlg;
	}
	
	public int copyLocalToHdfs(String srcFolder,String destFolder) {
		LOGGER.info("Copying folder " + srcFolder + " to " + destFolder);
		int flag = 0;
		Configuration conf = new Configuration();
		try {
			FileSystem fs =  FileSystem.get(conf);
			String[] fNames = CommonUtils.getFileNames(srcFolder);						
			for (int i = 0; i < fNames.length; i++) {	
				Path outPath = new Path(destFolder + "/" + fNames[i]);
				OutputStream os = fs.create(outPath);
				InputStream is = new BufferedInputStream(new FileInputStream(srcFolder + "/" + fNames[i]));
				IOUtils.copyBytes(is, os, conf);
			}
		} catch (IOException e) {
			flag = 1;
			e.printStackTrace();
		}
		return flag;
		
	} 
	public int clearDirectory(String srcFolder) {
		LOGGER.info("Cleaning directory " + srcFolder);
		int flag = 0;
		Configuration conf = new Configuration();
		try {
			FileSystem fs =  FileSystem.get(conf);
			RemoteIterator<LocatedFileStatus> itr = fs.listFiles(new Path(srcFolder), false);
			while(itr.hasNext()) {
				LocatedFileStatus lfs = itr.next();
				LOGGER.info("Deleting the part file in " + srcFolder + " = " + lfs.getPath().getName());
				fs.delete(lfs.getPath(),false);
			}
		}catch (IOException e) {
			flag = 1;
			e.printStackTrace();
		}
		return flag;
	}

}

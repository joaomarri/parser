package com.ef.service;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ef.model.AccessLog;

@Service
public class ReaderLogService {

	private static final Logger logger = LoggerFactory.getLogger(ReaderLogService.class);
	
	public void readLogFile(String fileName) {
		logger.info("Reading log file {}", fileName);
		
		try {
			Scanner scanner = new Scanner(new File(fileName));

			while (scanner.hasNext()){
				String line = scanner.nextLine();
				logger.info(line);
				
			}

		} catch (IOException e) {
			logger.error("Error when try to read the file {}", fileName, e);
		}
	}
	
	private AccessLog mapToItem(String line) {
		AccessLog log = null;
		String[] tokens = line.split("|");
		if (tokens != null) {
			log = new AccessLog();
			log.setDate(tokens[0]);
			log.setIp(tokens[1]);
			log.setRequest(tokens[2]);
			log.setStatus(tokens[3]);
			log.setUserAgent(tokens[4]);
		}
		
		return log;
	}
	
}

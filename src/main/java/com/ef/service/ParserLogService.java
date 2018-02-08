package com.ef.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ef.model.AccessLog;
import com.ef.util.DateUtil;

@Component
public class ParserLogService {

	private static final Logger logger = LoggerFactory.getLogger(ParserLogService.class);
	
	private DateUtil dtUtil = new DateUtil();
	
	@Autowired
	private WriterLogService writerLogService;
	 
	
	public void parseLogFileToDatabase(String fileName) {
		logger.info("Reading log file {}", fileName);
		
		Scanner scanner = null;
		
		try {
			List<AccessLog> logs = new ArrayList<>(); 
			scanner = new Scanner(new File(fileName));
			int count = 0;
			
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				AccessLog log = mapToItem(line);
				logs.add(log);
				count++;
				if (count == 100 || !scanner.hasNext()) {
					//persist max 100 logs each time
					writerLogService.persistLogs(logs);
					count = 0;
					logs = new ArrayList<>(); 
				}
				
				logger.info(line);
			}

		} catch (IOException e) {
			logger.error("Error when try to read the file {}", fileName, e);
		} catch (ParseException p) {
			logger.error("Error when try to parse the file {}", fileName, p);
		} catch (Exception ex) {
			logger.error("Error in the parse process", ex);
		} finally {
			if (scanner!=null) {
				scanner.close();
			}
		}
	}
	
	private AccessLog mapToItem(String line) throws ParseException {
		AccessLog log = null;
		StringTokenizer str = new StringTokenizer(line, "|");
		
		if (str.hasMoreElements()) {
			log = new AccessLog();
			log.setDate(dtUtil.toDateLog(str.nextElement().toString()));
			log.setIp(str.nextElement().toString());
			log.setRequest(str.nextElement().toString());
			log.setStatus(Integer.valueOf(str.nextElement().toString()));
			log.setUserAgent(str.nextElement().toString());
		}
		
		return log;
	}
	
}

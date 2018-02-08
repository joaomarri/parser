package com.ef.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ef.model.AccessLog;

@Component
public class ParserLogService {

	private static final Logger logger = LoggerFactory.getLogger(ParserLogService.class);
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	@Autowired
	private WriterLogService writerLogService;
	
	public void readLogFile(String fileName) {
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
					//persist max 100 logs
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
			log.setDate(dateFormat.parse(str.nextElement().toString()));
			log.setIp(str.nextElement().toString());
			log.setRequest(str.nextElement().toString());
			log.setStatus(Integer.valueOf(str.nextElement().toString()));
			log.setUserAgent(str.nextElement().toString());
		}
		
		/*String[] tokens = line.split("|");
		if (tokens != null) {
			log = new AccessLog();
			log.setDate(dateFormat.parse(tokens[0]));
			log.setIp(tokens[1]);
			log.setRequest(tokens[2]);
			log.setStatus(Integer.valueOf(tokens[3]));
			log.setUserAgent(tokens[4]);
		}*/
		
		return log;
	}
	
}
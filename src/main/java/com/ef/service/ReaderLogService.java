package com.ef.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReaderLogService {

	private static final Logger logger = LoggerFactory.getLogger(ReaderLogService.class);
	
	public void readLogFile(String name) {
		logger.info("Reading log file...");
	}
	
}

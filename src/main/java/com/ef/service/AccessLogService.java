package com.ef.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ef.dao.LogAccessDao;
import com.ef.model.AccessLog;
import com.ef.model.ParseResult;

@Service
public class AccessLogService {

	private static final Logger logger = LoggerFactory.getLogger(AccessLogService.class);
	
	@Autowired
	private LogAccessDao logAccessDao;
	
	public List<AccessLog> getAcessLogs(Date startDate, Date endDate, Integer threshold) throws Exception {
		List<AccessLog> logs = new ArrayList<>();
		
		try {
			logs = logAccessDao.getAcessLogs(startDate, endDate, threshold);
		} catch (Exception e) {
			logger.error("Error when try to get logs in database", e);
			throw e;
		}
		
		return logs;
	}
	
	public void persistParseResult(ParseResult parseResult, String filename) {
		logAccessDao.persistParseResult(parseResult, filename);
	}
}

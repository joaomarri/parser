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
import com.ef.util.DateUtil;
import com.ef.util.DurationEnum;

@Service
public class AccessLogService {

	private static final Logger logger = LoggerFactory.getLogger(AccessLogService.class);
	
	private DateUtil dtUtil = new DateUtil();
	
	@Autowired
	private LogAccessDao logAccessDao;
	
	
	public void buildParseResult(String accesslog, String duration, String startDate, Integer threshold) throws Exception {
        
		int hours = DurationEnum.getHours(duration);
		Date startdate = dtUtil.toDate(startDate);
        Date endDate = dtUtil.addHours(startdate, hours);
		List<AccessLog> logs = getAcessLogs(startdate, endDate, threshold);
		
        for (AccessLog log : logs) {
        		logger.info("This IP: {} is blocked, more than {} requests in {} hours", log.getIp(), threshold, hours);
        		ParseResult parseResult = new ParseResult(log.getIp(), "this ip is blocked: made more than "+threshold+" requests starting from "+startdate+" to "+endDate+" ("+hours+" hours)");
        		// save this ips and comments in database
        		persistParseResult(parseResult, accesslog);
		}
	}
	
	
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

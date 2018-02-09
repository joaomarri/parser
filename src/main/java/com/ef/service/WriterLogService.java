package com.ef.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ef.dao.LogAccessDao;
import com.ef.model.AccessLog;

/**
 * A writer service to persist access log in sql database
 *
 */
@Service
public class WriterLogService {
	
	@Autowired
	private LogAccessDao logAccessDao;
	
	
	public void persistLogs(List<AccessLog> logs, String fileName) {
		logAccessDao.persistLogs(logs, fileName);
	}
	
	public void deleteLogsTemp(String fileName) {
		logAccessDao.deleteLogsTemp(fileName);
	}
	
}

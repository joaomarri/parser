package com.ef.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ef.dao.WriterLogDao;
import com.ef.model.AccessLog;

/**
 * A writer service to persist access log in sql database
 *
 */
@Service
public class WriterLogService {
	
	@Autowired
	private WriterLogDao writerLogDao;
	

	public void persistLog(AccessLog log) {
		writerLogDao.persistLog(log);
	}
	
	public void persistLogs(List<AccessLog> logs) {
		writerLogDao.persistLogs(logs);
	}
	
}

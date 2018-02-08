package com.ef.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ef.dao.LogAccessDao;
import com.ef.model.AccessLog;

@Service
public class AccessLogService {

	@Autowired
	private LogAccessDao logAccessDao;
	
	public List<AccessLog> getAcessLogs(Date startDate, Date endDate, Integer threshold) {
		List<AccessLog> logs = new ArrayList<>();
		
		//select ip from accessLog
		//where
		//dateLog >= '2017-01-01 13:00:00'
		//and dateLog <= '2017-01-01 14:00:00'
		//Group by ip
		//Having count(ip) >2
		
		return logs;
	}
}

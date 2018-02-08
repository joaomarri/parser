package com.ef.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ef.model.AccessLog;

/**
 * 
 * LogAccess repository
 *
 */

@Repository
public class LogAccessDao {

	@Autowired
	@Qualifier("sql")
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	
	public void persistLogs(List<AccessLog> logs) {
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into accessLog (dateLog, ip, request, status, userAgent) ");
		sql.append(" values ");
		sql.append(" (:dateLog, :ip, :request, :status, :userAgent) ");
		
		List<Map<String, Object>> batchValues = new ArrayList<>(logs.size());
		for (AccessLog log : logs) {
			Map<String, Object> map = new HashMap<>();
			map.put("dateLog", log.getDate());
			map.put("ip", log.getIp());
			map.put("request", log.getRequest());
			map.put("status", log.getStatus());
			map.put("userAgent", log.getUserAgent());
			batchValues.add(map);
		}
		
		jdbcTemplate.batchUpdate(sql.toString(), batchValues.toArray(new Map[logs.size()]));
	}
	
}

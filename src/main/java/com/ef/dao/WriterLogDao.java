package com.ef.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ef.model.AccessLog;

@Repository
public class WriterLogDao {

	@Autowired
	@Qualifier("sql")
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	
	public void persistLog(AccessLog log) {
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into accessLog (date, ip, request, status, userAgent) ");
		sql.append(" values ");
		sql.append(" (:date, :ip, :request, :status, :userAgent) ");
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("date", log.getDate());
		namedParameters.addValue("ip", log.getIp());
		namedParameters.addValue("request", log.getRequest());
		namedParameters.addValue("status", log.getStatus());
		namedParameters.addValue("userAgent", log.getUserAgent());
		
		jdbcTemplate.update(sql.toString(), namedParameters);
	}
	
	public void persistLogs(List<AccessLog> logs) {
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into accessLog (date, ip, request, status, userAgent) ");
		sql.append(" values ");
		sql.append(" (:date, :ip, :request, :status, :userAgent) ");
		
		List<Map<String, Object>> batchValues = new ArrayList<>(logs.size());
		for (AccessLog log : logs) {
			Map<String, Object> map = new HashMap<>();
			map.put("date", log.getDate());
			map.put("ip", log.getIp());
			map.put("request", log.getRequest());
			map.put("status", log.getStatus());
			map.put("userAgent", log.getUserAgent());
			batchValues.add(map);
		}
		
		jdbcTemplate.batchUpdate(sql.toString(), batchValues.toArray(new Map[logs.size()]));
	}
	
}

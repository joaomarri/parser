package com.ef.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ef.model.AccessLog;
import com.ef.model.ParseResult;

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
	
	
	public void persistLogs(List<AccessLog> logs, String fileName) {
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into accessLog (dateLog, ip, request, status, userAgent, fileName) ");
		sql.append(" values ");
		sql.append(" (:dateLog, :ip, :request, :status, :userAgent, :fileName) ");
		
		List<Map<String, Object>> batchValues = new ArrayList<>(logs.size());
		for (AccessLog log : logs) {
			Map<String, Object> map = new HashMap<>();
			map.put("dateLog", log.getDate());
			map.put("ip", log.getIp());
			map.put("request", log.getRequest());
			map.put("status", log.getStatus());
			map.put("userAgent", log.getUserAgent());
			map.put("fileName", fileName);
			batchValues.add(map);
		}
		
		jdbcTemplate.batchUpdate(sql.toString(), batchValues.toArray(new Map[logs.size()]));
	}
	
	public List<AccessLog> getAcessLogs(Date startDate, Date endDate, Integer threshold) throws Exception {
		List<AccessLog> logs = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select ip from accessLog ");
		sql.append(" where ");
		sql.append(" dateLog >= :startDate ");
		sql.append(" and dateLog <= :endDate ");
		sql.append(" Group by ip ");
		sql.append(" Having count(ip) > :threshold "); 
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("startDate", startDate);
		namedParameters.addValue("endDate", endDate);
		namedParameters.addValue("threshold", threshold);
		
		try {
			return jdbcTemplate.query(sql.toString(), namedParameters, new ResultSetExtractor<List<AccessLog>>() {
				@Override
				public List<AccessLog> extractData(ResultSet rs) throws SQLException, DataAccessException {
					while (rs.next()) {
						AccessLog log = new AccessLog();
						log.setIp(rs.getString("ip"));
						
						logs.add(log);
					}
					return logs;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			return logs;
		} catch (Exception ex) {
			throw ex;
		}
		
	}
	
	public void persistParseResult(ParseResult parseResult, String fileName) {
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into parseResult (ip, comment, parseDate, filename) ");
		sql.append(" values ");
		sql.append(" (:ip, :comment, :parseDate, :filename) ");
		
		Date parseDate = new Date();
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("ip", parseResult.getIp());
		namedParameters.addValue("comment", parseResult.getComment());
		namedParameters.addValue("parseDate", parseDate);
		namedParameters.addValue("filename", fileName);
		
		
		jdbcTemplate.update(sql.toString(), namedParameters);
		
	}
	
	public void deleteLogsTemp(String fileName) {
		StringBuilder sql = new StringBuilder();
		sql.append(" delete from accessLog where filename = :filename ");
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("filename", fileName);
		
		jdbcTemplate.update(sql.toString(), namedParameters);
	}
}

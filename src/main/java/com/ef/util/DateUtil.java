package com.ef.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {
	
	private SimpleDateFormat dateFormatLog = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");
	
	public Date toDate(final String date) throws ParseException {
		return dateFormat.parse(date);
	}
	
	public Date toDateLog(final String date) throws ParseException {
		return dateFormatLog.parse(date);
	}
	
	public Date addHours(final Date date, int hours) {
		LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		ldt = ldt.plusHours(hours);
		Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
		Date data = Date.from(instant);
		return data;
	}

}

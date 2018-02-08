package com.ef.util;

import java.util.HashMap;
import java.util.Map;

public enum DurationEnum {
	
	hourly(1),
	daily(24);
	
	private Integer hours;
	
	private DurationEnum(Integer hour) {
		this.hours = hour;
	}
	
	private static final Map<String, Integer> map = new HashMap<String, Integer>();
	static {
		for (DurationEnum d : DurationEnum.values()) {
			map.put(d.toString(), d.hours);
		}
	}
	
	public static Integer getHours(String duration) {
		return map.get(duration);
	}

	public Integer getHours() {
		return hours;
	}
	
}

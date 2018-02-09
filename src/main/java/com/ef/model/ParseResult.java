package com.ef.model;

public class ParseResult {
	
	private String ip;
	private String comment;
	
	public ParseResult() {}
	
	public ParseResult(String ip, String comment) {
		this.ip = ip;
		this.comment = comment;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	

}

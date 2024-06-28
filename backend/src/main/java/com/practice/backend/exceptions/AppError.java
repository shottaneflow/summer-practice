package com.practice.backend.exceptions;

import java.util.Date;

public class AppError {

	private int status;
	private String message;
	private Date timestamp;
	
	public AppError(int status,String message) {
		this.message=message;
		this.status=status;
		this.timestamp=new Date();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
}

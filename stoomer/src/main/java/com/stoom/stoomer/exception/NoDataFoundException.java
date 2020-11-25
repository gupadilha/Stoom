package com.stoom.stoomer.exception;

public class NoDataFoundException extends Exception {

	private static final long serialVersionUID = java.util.Calendar.getInstance().getTimeInMillis();
	
	public NoDataFoundException() {
		super();
	}

	public NoDataFoundException(String err) {
		super(err);
	}

}

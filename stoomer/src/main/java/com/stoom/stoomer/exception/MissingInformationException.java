package com.stoom.stoomer.exception;

public class MissingInformationException extends Exception {

	private static final long serialVersionUID = java.util.Calendar.getInstance().getTimeInMillis();
	
	public MissingInformationException() {
		super();
	}
	
	public MissingInformationException(String err) {
		super(err);
	}

}

package com.stoom.stoomer.exception;

public class IntegrationException extends Exception {

	private static final long serialVersionUID = java.util.Calendar.getInstance().getTimeInMillis();
	
	public IntegrationException() {}
	
	public IntegrationException(Exception err) {
		super(err);
	}

}

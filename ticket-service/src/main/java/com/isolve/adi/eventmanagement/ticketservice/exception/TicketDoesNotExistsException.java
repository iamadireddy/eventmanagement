package com.isolve.adi.eventmanagement.ticketservice.exception;

public class TicketDoesNotExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TicketDoesNotExistsException(String message) {
		super(message);
	}
}
package com.isolve.adi.eventmanagement.ticketservice.service;

import java.time.LocalDate;
import java.util.List;

import com.isolve.adi.eventmanagement.ticketservice.exception.TicketDoesNotExistsException;
import com.isolve.adi.eventmanagement.ticketservice.exception.TicketNotCreatedException;
import com.isolve.adi.eventmanagement.ticketservice.exception.TicketNotFoundException;
import com.isolve.adi.eventmanagement.ticketservice.model.Ticket;

public interface ESTicketService {

	Ticket createTicket(Ticket ticket) throws TicketNotCreatedException;

	List<Ticket> getAllTickets();
	
	boolean deleteTicket(String id) throws TicketDoesNotExistsException;
	
	Ticket updateTicket(Ticket ticket);
	
	Ticket getTicketById(String id) throws TicketNotFoundException;

	//Integer getAllTicketsBetweenDates(LocalDate fromDate, LocalDate toDate);
}
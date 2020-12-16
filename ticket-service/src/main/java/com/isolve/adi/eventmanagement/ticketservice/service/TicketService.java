package com.isolve.adi.eventmanagement.ticketservice.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.isolve.adi.eventmanagement.ticketservice.exception.TicketDoesNotExistsException;
import com.isolve.adi.eventmanagement.ticketservice.exception.TicketNotCreatedException;
import com.isolve.adi.eventmanagement.ticketservice.exception.TicketNotFoundException;
import com.isolve.adi.eventmanagement.ticketservice.model.Ticket;

public interface TicketService {

	Ticket createTicket(Ticket ticket) throws TicketNotCreatedException;

	List<Ticket> getAllTickets();
	
	boolean deleteTicket(UUID id) throws TicketDoesNotExistsException;
	
	Ticket updateTicket(Ticket ticket);
	
	Ticket getTicketById(UUID id) throws TicketNotFoundException;

	Integer getAllTicketsBetweenDates(LocalDate fromDate, LocalDate toDate);
}
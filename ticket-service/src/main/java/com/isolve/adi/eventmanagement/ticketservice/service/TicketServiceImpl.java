package com.isolve.adi.eventmanagement.ticketservice.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.isolve.adi.eventmanagement.ticketservice.exception.TicketDoesNotExistsException;
import com.isolve.adi.eventmanagement.ticketservice.exception.TicketNotCreatedException;
import com.isolve.adi.eventmanagement.ticketservice.exception.TicketNotFoundException;
import com.isolve.adi.eventmanagement.ticketservice.model.Ticket;
import com.isolve.adi.eventmanagement.ticketservice.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {
	
	private TicketRepository ticketRepository;
	
	public TicketServiceImpl(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	@Override
	public Ticket createTicket(Ticket ticket) throws TicketNotCreatedException {

		if(ticketRepository.findById(ticket.getId()).isPresent()) {
			throw new TicketNotCreatedException("Ticket was already exists");
		}else {
			Ticket tkt = ticketRepository.insert(ticket);
			return tkt;
		}
	}

	@Override
	public List<Ticket> getAllTickets() {
		return ticketRepository.findAll();
	}

	@Override
	public boolean deleteTicket(UUID id) throws TicketDoesNotExistsException {
		
		if(!ticketRepository.findById(id).isPresent())
			throw new TicketDoesNotExistsException("Ticket was not found");
		else {
			ticketRepository.deleteById(id);
			return true;
		}
	}

	@Override
	public Ticket updateTicket(Ticket ticket) {
		
		Ticket updatedTicket = ticketRepository.save(ticket);
		if(updatedTicket != null) {
			return ticket;
		}
		else
			return null;
	}

	@Override
	public Ticket getTicketById(UUID id) throws TicketNotFoundException {

		Optional<Ticket> ticket = ticketRepository.findById(id);
		if(!ticket.isPresent())
			throw new TicketNotFoundException("Ticket was not found");
		else
			return ticket.get();
	}

	@Override
	public Integer getAllTicketsBetweenDates(LocalDate fromDate, LocalDate toDate) {
		
		List<Ticket> tickets = ticketRepository.findAllByTicketBookedDateBetween(fromDate, toDate);
		
		return tickets.stream().collect(Collectors.summingInt(Ticket::getNoOfTickets));
	}
}
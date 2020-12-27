package com.isolve.adi.eventmanagement.ticketservice.controller;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isolve.adi.eventmanagement.ticketservice.exception.TicketDoesNotExistsException;
import com.isolve.adi.eventmanagement.ticketservice.exception.TicketNotCreatedException;
import com.isolve.adi.eventmanagement.ticketservice.exception.TicketNotFoundException;
import com.isolve.adi.eventmanagement.ticketservice.exception.TicketsNotAvailableException;
import com.isolve.adi.eventmanagement.ticketservice.model.Event;
import com.isolve.adi.eventmanagement.ticketservice.model.Ticket;
import com.isolve.adi.eventmanagement.ticketservice.proxy.EventServiceProxy;
import com.isolve.adi.eventmanagement.ticketservice.service.ESTicketService;
import com.isolve.adi.eventmanagement.ticketservice.service.TicketService;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {

	private TicketService ticketService;
	private EventServiceProxy eventServiceProxy;
	private ESTicketService esTicketService;
	private Event event = null;
	private Double ticketPrice;

	@Autowired
	public TicketController(TicketService ticketService, EventServiceProxy eventServiceProxy, ESTicketService esTicketService) {
		this.ticketService = ticketService;
		this.eventServiceProxy = eventServiceProxy;
		this.esTicketService = esTicketService;
	}

	@PostMapping
	public ResponseEntity<?> createTicket(@RequestBody Ticket ticket){

		try {
			ticket.setId(UUID.randomUUID().toString());
			event = eventServiceProxy.getEventById(ticket.getEvent().getId());
			boolean isTicketsAvailable = getAvailableTickets(event.getAvailableSeats(), ticket.getNoOfTickets());
			if(isTicketsAvailable) {
				ticket.setEvent(event);
				ticketPrice = event.getTicketPrice();
				ticket.setTicketPrice(ticketPrice);
				ticket.setTotalPrice((ticketPrice) * (ticket.getNoOfTickets()));
				return new ResponseEntity<>(ticketService.createTicket(ticket), HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>("Available tickets are " + event.getAvailableSeats(), HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (TicketNotCreatedException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Ticket was already exists", HttpStatus.CONFLICT);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getTicket(@PathVariable String id) {

		try {
			Ticket ticketById = esTicketService.getTicketById(id);
			if (ticketById != null) {
				return new ResponseEntity<>(ticketById, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Ticket was not exists", HttpStatus.NOT_FOUND);
			}
		} catch (TicketNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Ticket was not exists", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/all")
	private ResponseEntity<?> getAllTickets() {

		return new ResponseEntity<>(esTicketService.getAllTickets(), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> updateTicket(@RequestBody Ticket ticket) {

		event = eventServiceProxy.getEventById(ticket.getEvent().getId());
		boolean isTicketsAvailable = getAvailableTickets(event.getAvailableSeats(), ticket.getNoOfTickets());
		if(isTicketsAvailable) {
			ticket.setEvent(event);
			ticketPrice = event.getTicketPrice();
			ticket.setTicketPrice(ticketPrice);
			ticket.setTotalPrice((ticketPrice) * (ticket.getNoOfTickets()));
			Ticket updateTicket = ticketService.updateTicket(ticket);
			if (updateTicket != null)
				return new ResponseEntity<>("Ticket was updated successfully", HttpStatus.OK);
			else
				return new ResponseEntity<>("Ticket was not updated successfully", HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>("Available tickets are " + event.getAvailableSeats(), HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTicket(@PathVariable String id) {

		try {
			return new ResponseEntity<>(ticketService.deleteTicket(id), HttpStatus.OK);
		} catch (TicketDoesNotExistsException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Ticket was not exists", HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/from/{fromDate}/to/{toDate}")
	public ResponseEntity<?> getAllTicketsBetweenDates(@PathVariable String fromDate, @PathVariable String toDate){
		
		return new ResponseEntity<>(ticketService.getAllTicketsBetweenDates(LocalDate.parse(fromDate),LocalDate.parse(toDate)), HttpStatus.OK);
	}

	private boolean getAvailableTickets(Integer availableSeats, Integer noOfTickets){

		boolean isTicketsAvailable = true;

		if (availableSeats < noOfTickets)
			isTicketsAvailable = false;

		return isTicketsAvailable;
	}
}
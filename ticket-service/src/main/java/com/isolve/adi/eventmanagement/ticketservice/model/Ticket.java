package com.isolve.adi.eventmanagement.ticketservice.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Ticket {

	@Id
	private UUID id;
	private String ticketNumber;
	private Double totalPrice;
	private Event event;
	private LocalDate ticketBookedDate ;
	private Integer noOfTickets;
	private Double ticketPrice;
	
	public Ticket() {	}

	public Ticket(UUID id, String ticketNumber, Double totalPrice, Event event, LocalDate ticketBookedDate,
			Integer noOfTickets, Double ticketPrice) {
		this.id = id;
		this.ticketNumber = ticketNumber;
		this.totalPrice = totalPrice;
		this.event = event;
		this.ticketBookedDate = ticketBookedDate;
		this.noOfTickets = noOfTickets;
		this.ticketPrice = ticketPrice;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public LocalDate getTicketBookedDate() {
		return ticketBookedDate;
	}

	public void setTicketBookedDate(LocalDate ticketBookedDate) {
		this.ticketBookedDate = ticketBookedDate;
	}

	public Integer getNoOfTickets() {
		return noOfTickets;
	}

	public void setNoOfTickets(Integer noOfTickets) {
		this.noOfTickets = noOfTickets;
	}

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
}
package com.isolve.adi.eventmanagement.ticketservice.service;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isolve.adi.eventmanagement.ticketservice.config.ESConfiguration;
import com.isolve.adi.eventmanagement.ticketservice.exception.TicketDoesNotExistsException;
import com.isolve.adi.eventmanagement.ticketservice.exception.TicketNotCreatedException;
import com.isolve.adi.eventmanagement.ticketservice.exception.TicketNotFoundException;
import com.isolve.adi.eventmanagement.ticketservice.model.Ticket;
import com.isolve.adi.eventmanagement.ticketservice.repository.ESTicketRepository;

@Service
public class ESTicketServiceImpl implements ESTicketService{
	
	private final String INDEX = "ticket";
	private ESTicketRepository esTicketRepository;
	private ESConfiguration esConfiguration;
	private ObjectMapper objectMapper;
	
	@Autowired
	public ESTicketServiceImpl(ESTicketRepository esTicketRepository, ESConfiguration esConfiguration, ObjectMapper objectMapper) {
		this.esTicketRepository = esTicketRepository;
		this.esConfiguration = esConfiguration;
		this.objectMapper = objectMapper;
	}

	@Override
	public Ticket createTicket(Ticket ticket) throws TicketNotCreatedException {
		
		Ticket tkt = esTicketRepository.save(ticket);		
		return tkt;
	}

	@Override
	public List<Ticket> getAllTickets() {
		
		return esTicketRepository.findAll();
	}

	@Override
	public boolean deleteTicket(String id) throws TicketDoesNotExistsException {
		
		esTicketRepository.deleteById(id);
		return true;
	}

	@Override
	public Ticket updateTicket(Ticket ticket) {
		
		UpdateRequest updateRequest = new UpdateRequest(INDEX, ticket.getId());
		try {
			String writeValueAsString = objectMapper.writeValueAsString(ticket);
			updateRequest.doc(writeValueAsString, XContentType.JSON);
			esConfiguration.client().update(updateRequest, RequestOptions.DEFAULT);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ticket;
	}

	@Override
	public Ticket getTicketById(String id) throws TicketNotFoundException {
		
		GetResponse getResponse;
		Ticket event = null;
		try {
			getResponse = esConfiguration.client().get(new GetRequest(INDEX, id), RequestOptions.DEFAULT);
			event = objectMapper.readValue(getResponse.getSourceAsString(), Ticket.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return event;
	}

	/*
	@Override
	public Integer getAllTicketsBetweenDates(LocalDate fromDate, LocalDate toDate) {
		// TODO Auto-generated method stub
		return null;
	}
	*/
}
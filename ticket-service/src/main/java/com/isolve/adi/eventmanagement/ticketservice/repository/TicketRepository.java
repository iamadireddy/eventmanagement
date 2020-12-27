package com.isolve.adi.eventmanagement.ticketservice.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.isolve.adi.eventmanagement.ticketservice.model.Ticket;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String>{
	
	List<Ticket> findAllByTicketBookedDateBetween(LocalDate fromDate, LocalDate toDate);

}

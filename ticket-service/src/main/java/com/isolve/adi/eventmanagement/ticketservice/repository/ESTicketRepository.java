package com.isolve.adi.eventmanagement.ticketservice.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.isolve.adi.eventmanagement.ticketservice.model.Ticket;

@Repository
public interface ESTicketRepository extends ElasticsearchRepository<Ticket, String>{

	List<Ticket> findAll();
	void deleteById(String id);
}
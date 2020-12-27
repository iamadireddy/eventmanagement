package com.isolve.adi.eventmanagement.eventservice.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.isolve.adi.eventmanagement.eventservice.model.Event;

@Repository
public interface EventRepository extends MongoRepository<Event, String>{

}
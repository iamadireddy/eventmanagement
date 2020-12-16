package com.isolve.adi.eventmanagement.eventservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.isolve.adi.eventmanagement.eventservice.exception.EventDoesNotExistsException;
import com.isolve.adi.eventmanagement.eventservice.exception.EventNotCreatedException;
import com.isolve.adi.eventmanagement.eventservice.exception.EventNotFoundException;
import com.isolve.adi.eventmanagement.eventservice.model.Event;

@Service
public interface EventService {

	Event createEvent(Event event) throws EventNotCreatedException;

	List<Event> getAllEvents();
	
	boolean deleteEvent(UUID id) throws EventDoesNotExistsException;
	
	Event updateEvent(Event event);
	
	Event getEventById(UUID id) throws EventNotFoundException;
	
}
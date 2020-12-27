package com.isolve.adi.eventmanagement.eventservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.isolve.adi.eventmanagement.eventservice.exception.EventDoesNotExistsException;
import com.isolve.adi.eventmanagement.eventservice.exception.EventNotCreatedException;
import com.isolve.adi.eventmanagement.eventservice.exception.EventNotFoundException;
import com.isolve.adi.eventmanagement.eventservice.model.Event;

@Service
public interface ESEventService {

	Event createEvent(Event event) throws EventNotCreatedException;

	List<Event> getAllEvents();
	
	boolean deleteEvent(String id) throws EventDoesNotExistsException;
	
	Event updateEvent(Event event);
	
	Event getEventById(String id) throws EventNotFoundException;
	
}
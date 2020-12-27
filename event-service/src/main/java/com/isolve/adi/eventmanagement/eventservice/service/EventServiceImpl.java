package com.isolve.adi.eventmanagement.eventservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isolve.adi.eventmanagement.eventservice.exception.EventDoesNotExistsException;
import com.isolve.adi.eventmanagement.eventservice.exception.EventNotCreatedException;
import com.isolve.adi.eventmanagement.eventservice.exception.EventNotFoundException;
import com.isolve.adi.eventmanagement.eventservice.model.Event;
import com.isolve.adi.eventmanagement.eventservice.repository.EventRepository;

@Service
public class EventServiceImpl implements EventService{
	
	private EventRepository eventRepository; 
	private ESEventService esEventService;
	
	@Autowired
	public EventServiceImpl(EventRepository eventRepository, ESEventService esEventService) {
		
		this.eventRepository = eventRepository;
		this.esEventService = esEventService;
	}

	@Override
	public Event createEvent(Event event) throws EventNotCreatedException {
		
		if(eventRepository.findById(event.getId()).isPresent()) {
			throw new EventNotCreatedException("Event was already exists");
		}else {
			Event evnt = eventRepository.insert(event);
			esEventService.createEvent(event);
			return evnt;
		}
	}

	/*
	@Override
	public List<Event> getAllEvents() {
		return eventRepository.findAll();
	}
	*/

	@Override
	public boolean deleteEvent(String id) throws EventDoesNotExistsException {
		
		if(!eventRepository.findById(id).isPresent())
			throw new EventDoesNotExistsException("Event was not found");
		else {
			eventRepository.deleteById(id);
			esEventService.deleteEvent(id);
			return true;
		}
	}

	@Override
	public Event updateEvent(Event event) {
		
		Event updateEvent = eventRepository.save(event);
		if(updateEvent != null) {
			esEventService.updateEvent(event);
			return event;
		}
		else
			return null;
	}

	/*
	@Override
	public Event getEventById(String id) throws EventNotFoundException {
		
		Optional<Event> event = eventRepository.findById(id);
		if(!event.isPresent())
			throw new EventNotFoundException("Event was not found");
		else
			return event.get();
	}
	*/
}

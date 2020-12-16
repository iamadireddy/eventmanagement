package com.isolve.adi.eventmanagement.eventservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.isolve.adi.eventmanagement.eventservice.exception.EventDoesNotExistsException;
import com.isolve.adi.eventmanagement.eventservice.exception.EventNotCreatedException;
import com.isolve.adi.eventmanagement.eventservice.exception.EventNotFoundException;
import com.isolve.adi.eventmanagement.eventservice.model.Event;
import com.isolve.adi.eventmanagement.eventservice.repository.EventRepository;

@Service
public class EventServiceImpl implements EventService{
	
	private EventRepository eventRepository; 
	
	public EventServiceImpl(EventRepository eventRepository) {
		
		this.eventRepository = eventRepository;
	}

	@Override
	public Event createEvent(Event event) throws EventNotCreatedException {
		
		if(eventRepository.findById(event.getId()).isPresent()) {
			throw new EventNotCreatedException("Event was already exists");
		}else {
			Event evnt = eventRepository.insert(event);
			return evnt;
		}
	}

	@Override
	public List<Event> getAllEvents() {
		return eventRepository.findAll();
	}

	@Override
	public boolean deleteEvent(UUID id) throws EventDoesNotExistsException {
		
		if(!eventRepository.findById(id).isPresent())
			throw new EventDoesNotExistsException("Event was not found");
		else {
			eventRepository.deleteById(id);
			return true;
		}
	}

	@Override
	public Event updateEvent(Event event) {
		
		Event updateEvent = eventRepository.save(event);
		if(updateEvent != null) {
			return event;
		}
		else
			return null;
	}

	@Override
	public Event getEventById(UUID id) throws EventNotFoundException {
		
		Optional<Event> event = eventRepository.findById(id);
		if(!event.isPresent())
			throw new EventNotFoundException("Event was not found");
		else
			return event.get();
	}

}

package com.isolve.adi.eventmanagement.eventservice.controller;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

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

import com.isolve.adi.eventmanagement.eventservice.exception.EventDoesNotExistsException;
import com.isolve.adi.eventmanagement.eventservice.exception.EventNotCreatedException;
import com.isolve.adi.eventmanagement.eventservice.exception.EventNotFoundException;
import com.isolve.adi.eventmanagement.eventservice.model.Artists;
import com.isolve.adi.eventmanagement.eventservice.model.Event;
import com.isolve.adi.eventmanagement.eventservice.proxy.ArtistsServiceProxy;
import com.isolve.adi.eventmanagement.eventservice.proxy.EventCategoryServiceProxy;
import com.isolve.adi.eventmanagement.eventservice.service.ESEventService;
import com.isolve.adi.eventmanagement.eventservice.service.EventService;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {
	
	private EventService eventService;	
	//@Autowired
	private ArtistsServiceProxy artistsServiceProxy;
	//@Autowired
	private EventCategoryServiceProxy eventCategoryServiceProxy;
	private ESEventService esEventService;
	
	@Autowired
	public EventController(EventService eventService, ArtistsServiceProxy artistsServiceProxy, EventCategoryServiceProxy eventCategoryServiceProxy, ESEventService esEventService) {
		this.eventService = eventService;
		this.artistsServiceProxy = artistsServiceProxy;
		this.eventCategoryServiceProxy = eventCategoryServiceProxy;
		this.esEventService = esEventService;
	}
	
	
	@PostMapping
	public ResponseEntity<?> createEvent(@RequestBody Event event) {

		try {
			event.setId(UUID.randomUUID().toString());
			List<Artists> artists = artistsServiceProxy.getArtistsList();
			event.setArtistsList(artists);
			event.setEventCategory(eventCategoryServiceProxy.getEventCategory(event.getEventCategory().getId()));
			return new ResponseEntity<>(eventService.createEvent(event), HttpStatus.CREATED);
		} catch (EventNotCreatedException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Event was already exists", HttpStatus.CONFLICT);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getEvent(@PathVariable String id) {

		System.out.println("Inside getEvent");
		
		try {
			Event eventById = esEventService.getEventById(id);
			if (eventById != null) {
				return new ResponseEntity<>(eventById, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Event was not exists", HttpStatus.NOT_FOUND);
			}
		} catch (EventNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Event was not exists", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/all")
	private ResponseEntity<?> getAllEvents() {
		
		esEventService.getAllEvents().stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		return new ResponseEntity<>(esEventService.getAllEvents(), HttpStatus.OK);
	}
	
	@GetMapping("/allEventsByCategory")
	private ResponseEntity<?> getAllEventsByCategory() {

		return new ResponseEntity<>(esEventService.getAllEvents().stream().collect(Collectors.groupingBy(Event::getEventCategory, Collectors.counting())), HttpStatus.OK);
	}

	/*@GetMapping("/eventsByArtists")
	private ResponseEntity<?> getEventsByArtists() {

		return new ResponseEntity<>(eventService.getAllEvents().stream().collect(Collectors.groupingBy(Event::getArtistsList, Collectors.counting())), HttpStatus.OK);
	}*/
	
	@PutMapping
	public ResponseEntity<?> updateEvent(@RequestBody Event event) {

		Event updateEvent = eventService.updateEvent(event);
		if (updateEvent != null)
			return new ResponseEntity<>("Event was updated successfully", HttpStatus.OK);
		else
			return new ResponseEntity<>("Event was not updated successfully", HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEvent(@PathVariable String id) {

		try {
			return new ResponseEntity<>(eventService.deleteEvent(id), HttpStatus.OK);
		} catch (EventDoesNotExistsException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Event was not exists", HttpStatus.CONFLICT);
		}
	}
}
package com.isolve.adi.eventmanagement.eventcategoryservice.controller;

import java.util.UUID;

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

import com.isolve.adi.eventmanagement.eventcategoryservice.exception.EventCategoryDoesNotExistsException;
import com.isolve.adi.eventmanagement.eventcategoryservice.exception.EventCategoryNotCreatedException;
import com.isolve.adi.eventmanagement.eventcategoryservice.exception.EventCategoryNotFoundException;
import com.isolve.adi.eventmanagement.eventcategoryservice.model.EventCategory;
import com.isolve.adi.eventmanagement.eventcategoryservice.service.EventCategoryService;

@RestController
@RequestMapping("/api/v1/event-category")
public class EventCategoryController {

	private EventCategoryService eventCategoryService;

	@Autowired
	public EventCategoryController(EventCategoryService eventCategoryService) {
		this.eventCategoryService= eventCategoryService;
	}

	@PostMapping
	public ResponseEntity<?> createEventCategory(@RequestBody EventCategory eventCategor) {

		try {
			eventCategor.setId(UUID.randomUUID());
			return new ResponseEntity<>(eventCategoryService.createEventCategory(eventCategor), HttpStatus.CREATED);
		} catch (EventCategoryNotCreatedException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Event category was already exists", HttpStatus.CONFLICT);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getEventCategory(@PathVariable UUID id) {

		try {
			EventCategory eventCategoryById = eventCategoryService.getEventCategoryById(id);
			if (eventCategoryById != null) {
				return new ResponseEntity<>(eventCategoryById, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Event category was not exists", HttpStatus.NOT_FOUND);
			}
		} catch (EventCategoryNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Event category was not exists", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/all")
	private ResponseEntity<?> getAllEventCategories() {

		return new ResponseEntity<>(eventCategoryService.getAllEventCategories(), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> updateEventCategory(@RequestBody EventCategory eventCategory) {

		EventCategory updateEventCategory = eventCategoryService.updateEventCategory(eventCategory);
		if (updateEventCategory != null)
			return new ResponseEntity<>("Event category was updated successfully", HttpStatus.OK);
		else
			return new ResponseEntity<>("Event category was not updated successfully", HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEventCategory(@PathVariable UUID id) {

		try {
			return new ResponseEntity<>(eventCategoryService.deleteEventCategory(id), HttpStatus.OK);
		} catch (EventCategoryDoesNotExistsException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Event category was not exists", HttpStatus.CONFLICT);
		}
	}
}
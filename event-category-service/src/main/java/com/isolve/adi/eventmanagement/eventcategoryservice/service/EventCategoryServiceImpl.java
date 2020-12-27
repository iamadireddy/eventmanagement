package com.isolve.adi.eventmanagement.eventcategoryservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isolve.adi.eventmanagement.eventcategoryservice.exception.EventCategoryDoesNotExistsException;
import com.isolve.adi.eventmanagement.eventcategoryservice.exception.EventCategoryNotCreatedException;
import com.isolve.adi.eventmanagement.eventcategoryservice.exception.EventCategoryNotFoundException;
import com.isolve.adi.eventmanagement.eventcategoryservice.model.EventCategory;
import com.isolve.adi.eventmanagement.eventcategoryservice.repository.ESEventCategoryRepository;
import com.isolve.adi.eventmanagement.eventcategoryservice.repository.EventCategoryRepository;

@Service
public class EventCategoryServiceImpl implements EventCategoryService {
	
	private EventCategoryRepository eventCategoryRepository;
	private ESEventCategoryService esEventCategoryService; 
	
	@Autowired
	public EventCategoryServiceImpl(EventCategoryRepository eventCategoryRepository, ESEventCategoryService esEventCategoryService) {
		this.eventCategoryRepository = eventCategoryRepository;
		this.esEventCategoryService = esEventCategoryService;
	}

	@Override
	public EventCategory createEventCategory(EventCategory eventCategory) throws EventCategoryNotCreatedException {
		
		if(eventCategoryRepository.findById(eventCategory.getId()).isPresent()) {
			throw new EventCategoryNotCreatedException("Event category was already exists");
		}else {
			EventCategory eventCat = eventCategoryRepository.insert(eventCategory);
			esEventCategoryService.createEventCategory(eventCat);
			return eventCat;
		}
	}

	/*
	@Override
	public List<EventCategory> getAllEventCategories() {
		return eventCategoryRepository.findAll();
	}
	*/

	@Override
	public boolean deleteEventCategory(String id) throws EventCategoryDoesNotExistsException {
		
		if(!eventCategoryRepository.findById(id).isPresent())
			throw new EventCategoryDoesNotExistsException("Event category was not found");
		else {
			eventCategoryRepository.deleteById(id);
			esEventCategoryService.deleteEventCategory(id);
			return true;
		}
	}

	@Override
	public EventCategory updateEventCategory(EventCategory eventCategory) {
		
		EventCategory updateEventCategory = eventCategoryRepository.save(eventCategory);
		if(updateEventCategory != null) {
			esEventCategoryService.updateEventCategory(eventCategory);
			return eventCategory;
		}
		else
			return null;
	}

	@Override
	public EventCategory getEventCategoryById(String id) throws EventCategoryNotFoundException {
		
		Optional<EventCategory> eventCategory = eventCategoryRepository.findById(id);
		if(!eventCategory.isPresent())
			throw new EventCategoryNotFoundException("Event category was not found");
		else
			return eventCategory.get();
	}
}
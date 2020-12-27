package com.isolve.adi.eventmanagement.eventcategoryservice.service;

import java.util.List;

import com.isolve.adi.eventmanagement.eventcategoryservice.exception.EventCategoryDoesNotExistsException;
import com.isolve.adi.eventmanagement.eventcategoryservice.exception.EventCategoryNotCreatedException;
import com.isolve.adi.eventmanagement.eventcategoryservice.exception.EventCategoryNotFoundException;
import com.isolve.adi.eventmanagement.eventcategoryservice.model.EventCategory;

public interface ESEventCategoryService {

	EventCategory createEventCategory(EventCategory eventCategory) throws EventCategoryNotCreatedException;

	List<EventCategory> getAllEventCategories();
	
	boolean deleteEventCategory(String id) throws EventCategoryDoesNotExistsException;
	
	EventCategory updateEventCategory(EventCategory eventCategory);
	
	EventCategory getEventCategoryById(String id) throws EventCategoryNotFoundException;
}
package com.isolve.adi.eventmanagement.eventcategoryservice.service;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isolve.adi.eventmanagement.eventcategoryservice.config.ESConfiguration;
import com.isolve.adi.eventmanagement.eventcategoryservice.exception.EventCategoryDoesNotExistsException;
import com.isolve.adi.eventmanagement.eventcategoryservice.exception.EventCategoryNotCreatedException;
import com.isolve.adi.eventmanagement.eventcategoryservice.exception.EventCategoryNotFoundException;
import com.isolve.adi.eventmanagement.eventcategoryservice.model.EventCategory;
import com.isolve.adi.eventmanagement.eventcategoryservice.repository.ESEventCategoryRepository;

@Service
public class ESEventCategoryServiceImpl implements ESEventCategoryService {
	
	private final String INDEX = "eventcategory";
	private ESEventCategoryRepository esEventCategoryRepository;
	private ESConfiguration esConfiguration;
	private ObjectMapper objectMapper;
	
	public ESEventCategoryServiceImpl(ESEventCategoryRepository esEventCategoryRepository, ESConfiguration esConfiguration, ObjectMapper objectMapper) {
		this.esEventCategoryRepository = esEventCategoryRepository;
		this.esConfiguration = esConfiguration;
		this.objectMapper = objectMapper;
	}

	@Override
	public EventCategory createEventCategory(EventCategory eventCategory) throws EventCategoryNotCreatedException {
		
		//esConfiguration.elasticsearchTemplate().indexOps(EventCategory.class).create();
		
		EventCategory eCategory = esEventCategoryRepository.save(eventCategory);
		
		return eCategory;
	}

	@Override
	public List<EventCategory> getAllEventCategories() {
		
		return esEventCategoryRepository.findAll();
	}

	@Override
	public boolean deleteEventCategory(String id) throws EventCategoryDoesNotExistsException {
		
		esEventCategoryRepository.deleteById(id);
		return true;
	}

	@Override
	public EventCategory updateEventCategory(EventCategory eventCategory) {

		UpdateRequest updateRequest = new UpdateRequest(INDEX, eventCategory.getId());
		//EventCategory category = null;
		try {
			String writeValueAsString = objectMapper.writeValueAsString(eventCategory);
			updateRequest.doc(writeValueAsString, XContentType.JSON);
			 esConfiguration.client().update(updateRequest, RequestOptions.DEFAULT);
			//UpdateResponse updateResponse = esConfiguration.client().update(updateRequest, RequestOptions.DEFAULT);
			//String sourceAsString = updateResponse.getGetResult().sourceAsString();
			//category = objectMapper.readValue(sourceAsString, EventCategory.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return eventCategory;
	}

	@Override
	public EventCategory getEventCategoryById(String id) throws EventCategoryNotFoundException {
		GetResponse getResponse;
		EventCategory eventCategory = null;
		try {
			getResponse = esConfiguration.client().get(new GetRequest(INDEX, id), RequestOptions.DEFAULT);
			eventCategory = objectMapper.readValue(getResponse.getSourceAsString(), EventCategory.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return eventCategory;
	}

}

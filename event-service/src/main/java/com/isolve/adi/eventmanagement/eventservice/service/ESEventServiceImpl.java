package com.isolve.adi.eventmanagement.eventservice.service;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isolve.adi.eventmanagement.eventservice.config.ESConfiguration;
import com.isolve.adi.eventmanagement.eventservice.exception.EventDoesNotExistsException;
import com.isolve.adi.eventmanagement.eventservice.exception.EventNotCreatedException;
import com.isolve.adi.eventmanagement.eventservice.exception.EventNotFoundException;
import com.isolve.adi.eventmanagement.eventservice.model.Event;
import com.isolve.adi.eventmanagement.eventservice.repository.ESEventRepository;

@Service
public class ESEventServiceImpl implements ESEventService {
	
	private final String INDEX = "event";
	private ESEventRepository esEventRepository;
	private ESConfiguration esConfiguration;
	private ObjectMapper objectMapper;
	
	@Autowired
	public ESEventServiceImpl(ESEventRepository esEventRepository, ESConfiguration esConfiguration, ObjectMapper objectMapper) {
		this.esEventRepository = esEventRepository;
		this.esConfiguration = esConfiguration;
		this.objectMapper = objectMapper;
	}

	@Override
	public Event createEvent(Event event) throws EventNotCreatedException {
		
		Event evnt = esEventRepository.save(event);		
		return evnt;
	}

	@Override
	public List<Event> getAllEvents() {
		
		return esEventRepository.findAll();
	}

	@Override
	public boolean deleteEvent(String id) throws EventDoesNotExistsException {
		
		esEventRepository.deleteById(id);
		return true;
	}

	@Override
	public Event updateEvent(Event event) {
		
		UpdateRequest updateRequest = new UpdateRequest(INDEX, event.getId());
		try {
			String writeValueAsString = objectMapper.writeValueAsString(event);
			updateRequest.doc(writeValueAsString, XContentType.JSON);
			esConfiguration.client().update(updateRequest, RequestOptions.DEFAULT);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return event;
	}

	@Override
	public Event getEventById(String id) throws EventNotFoundException {
		
		GetResponse getResponse;
		Event event = null;
		try {
			getResponse = esConfiguration.client().get(new GetRequest(INDEX, id), RequestOptions.DEFAULT);
			event = objectMapper.readValue(getResponse.getSourceAsString(), Event.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return event;
	}
}
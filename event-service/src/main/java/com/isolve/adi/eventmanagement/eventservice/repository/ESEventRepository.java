package com.isolve.adi.eventmanagement.eventservice.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.isolve.adi.eventmanagement.eventservice.model.Event;

@Repository
public interface ESEventRepository extends ElasticsearchRepository<Event, String>{

	List<Event> findAll();
	void deleteById(String id);
}

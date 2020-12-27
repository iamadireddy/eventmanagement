package com.isolve.adi.eventmanagement.eventcategoryservice.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.isolve.adi.eventmanagement.eventcategoryservice.model.EventCategory;

@Repository
public interface ESEventCategoryRepository extends ElasticsearchRepository<EventCategory, String>{

	List<EventCategory> findAll();
	void deleteById(String id);
	
}

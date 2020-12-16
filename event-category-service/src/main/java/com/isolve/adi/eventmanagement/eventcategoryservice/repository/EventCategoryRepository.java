package com.isolve.adi.eventmanagement.eventcategoryservice.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.isolve.adi.eventmanagement.eventcategoryservice.model.EventCategory;

@Repository
public interface EventCategoryRepository extends MongoRepository<EventCategory, UUID>{

}

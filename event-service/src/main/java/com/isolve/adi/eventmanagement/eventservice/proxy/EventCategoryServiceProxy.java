package com.isolve.adi.eventmanagement.eventservice.proxy;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.isolve.adi.eventmanagement.eventservice.model.EventCategory;

@FeignClient(name="event-category-service", url="localhost:8765/event-category-service")
public interface EventCategoryServiceProxy {

	@GetMapping("/api/v1/event-category/{id}")
	public EventCategory getEventCategory(@PathVariable UUID id);
}

package com.isolve.adi.eventmanagement.ticketservice.proxy;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.isolve.adi.eventmanagement.ticketservice.model.EventCategory;

@FeignClient(name="event-category-service", url="localhost:8765")
public interface EventCategoryServiceProxy {

	@GetMapping("/event-category-service/api/v1/event-category/{id}")
	public EventCategory getEventCategory(@PathVariable UUID id);
}

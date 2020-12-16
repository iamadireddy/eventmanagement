package com.isolve.adi.eventmanagement.ticketservice.proxy;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.isolve.adi.eventmanagement.ticketservice.model.Event;

@FeignClient(name="event-service", url="localhost:8765/event-service")
public interface EventServiceProxy {

	@GetMapping("/api/v1/event/{id}")
	public Event getEventById(@PathVariable UUID id);
}

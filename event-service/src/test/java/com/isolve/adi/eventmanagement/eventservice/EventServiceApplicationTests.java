package com.isolve.adi.eventmanagement.eventservice;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isolve.adi.eventmanagement.eventservice.controller.EventController;
import com.isolve.adi.eventmanagement.eventservice.exception.EventDoesNotExistsException;
import com.isolve.adi.eventmanagement.eventservice.exception.EventNotCreatedException;
import com.isolve.adi.eventmanagement.eventservice.model.Artists;
import com.isolve.adi.eventmanagement.eventservice.model.Event;
import com.isolve.adi.eventmanagement.eventservice.model.EventCategory;
import com.isolve.adi.eventmanagement.eventservice.service.EventService;

@RunWith(SpringRunner.class)
@WebMvcTest
class EventserviceApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	private Event event;
	private Artists artists;
	private EventCategory eventCategory;
	@MockBean
	private EventService eventService;
	@InjectMocks
	private EventController eventController;
	private List<Event> events;
	private List<Artists> artistsList;
	private List<EventCategory> eventCategories;
	String eId = UUID.randomUUID().toString();

	@BeforeEach
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
		LocalDate starDate = LocalDate.now();
		LocalDate endDate = starDate.plusDays(5);
		
		artists = new Artists();
		artists.setId(UUID.randomUUID().toString());
		artists.setArtistName("Adi");
		artists.setDescription("Hello Adi");
		artists.setImage("Anil");
		
		artistsList = new ArrayList<Artists>();
		artistsList.add(artists);
		
		eventCategory = new EventCategory();
		eventCategory.setId(UUID.randomUUID().toString());
		eventCategory.setCategoryName("Music");
		eventCategory.setImage("Anil");
		
		eventCategories = new ArrayList<EventCategory>();
		eventCategories.add(eventCategory);
		
		event = new Event();
		event.setArtistsList(artistsList);
		event.setAvailableSeats(21);
		event.setDescription("Testing event");
		event.setEventCategory(eventCategory);
		event.setEventStartDate(starDate);
		event.setEventEndDate(endDate);
		event.setEventImage("Adi");
		event.setEventTitle("Sangeeth");
		event.setId(eId);
		event.setTicketPrice(100.00);
		event.setTotalSeats(50);
	}

	@Test
	public void createEventSuccess() throws Exception{
		
		when(eventService.createEvent(any())).thenReturn(event);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/event")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(event)))
					.andExpect(MockMvcResultMatchers.status().isCreated())
					.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void createEventFailure() throws Exception{
		
		when(eventService.createEvent(any())).thenThrow(EventNotCreatedException.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/event")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(event)))
					.andExpect(MockMvcResultMatchers.status().isConflict())
					.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void deleteEventSuccess() throws Exception{
		
		when(eventService.deleteEvent(eId)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/event/eId")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(event)))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void deleteEventFailure() throws Exception{
		
		when(eventService.deleteEvent(eId)).thenThrow(EventDoesNotExistsException.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/event/eId")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(event)))
					.andExpect(MockMvcResultMatchers.status().isNotFound())
					.andDo(MockMvcResultHandlers.print());
	}
	
	 private static String asJsonString(final Object obj) {
	        try {
	            return new ObjectMapper().writeValueAsString(obj);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }
}
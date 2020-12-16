package com.isolve.adi.eventmanagement.ticketservice;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
import com.isolve.adi.eventmanagement.ticketservice.controller.TicketController;
import com.isolve.adi.eventmanagement.ticketservice.exception.TicketDoesNotExistsException;
import com.isolve.adi.eventmanagement.ticketservice.exception.TicketNotCreatedException;
import com.isolve.adi.eventmanagement.ticketservice.model.Artists;
import com.isolve.adi.eventmanagement.ticketservice.model.Event;
import com.isolve.adi.eventmanagement.ticketservice.model.EventCategory;
import com.isolve.adi.eventmanagement.ticketservice.model.Ticket;
import com.isolve.adi.eventmanagement.ticketservice.service.TicketService;

@RunWith(SpringRunner.class)
@WebMvcTest
class TicketserviceApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	private Ticket ticket;
	private Event event;
	private Artists artists;
	private EventCategory eventCategory;
	@MockBean
	private TicketService ticketService;
	@InjectMocks
	private TicketController ticketController;
	private List<Ticket> tickets;
	private List<Event> events;
	private List<Artists> artistsList;
	private List<EventCategory> eventCategories;
	UUID tId = UUID.randomUUID();

	@BeforeEach
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
		
		LocalDate starDate = LocalDate.now();
		LocalDate endDate = starDate.plusDays(5);
		
		artists = new Artists();
		UUID aId = UUID.randomUUID();
		artists.setId(aId);
		artists.setArtistName("Adi");
		artists.setDescription("Hello Adi");
		artists.setImage("Anil");
		
		artistsList = new ArrayList<Artists>();
		artistsList.add(artists);
		
		eventCategory = new EventCategory();
		UUID eId = UUID.randomUUID();
		eventCategory.setId(eId);
		eventCategory.setCategoryName("Music");
		eventCategory.setImage("Anil");
		
		eventCategories = new ArrayList<EventCategory>();
		eventCategories.add(eventCategory);
		
		event = new Event();
		UUID evId = UUID.randomUUID();
		event.setArtistsList(artistsList);
		event.setAvailableSeats(21);
		event.setDescription("Testing event");
		event.setEventCategory(eventCategory);
		event.setEventStartDate(starDate);
		event.setEventEndDate(endDate);
		event.setEventImage("Adi");
		event.setEventTitle("Sangeeth");
		event.setId(evId);
		event.setTicketPrice(100.00);
		event.setTotalSeats(50);
		
		ticket = new Ticket();
		ticket.setEvent(event);
		ticket.setId(tId);
		ticket.setNoOfTickets(2);
		ticket.setTicketBookedDate(starDate.minusDays(3));
		ticket.setTicketNumber("a2z029");
		ticket.setTicketPrice(150.00);
		ticket.setTotalPrice(ticket.getNoOfTickets()*(ticket.getTicketPrice()));
	}

	@Test
	public void createTicketSuccess() throws Exception{
		
		when(ticketService.createTicket(any())).thenReturn(ticket);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/ticket")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(ticket)))
					.andExpect(MockMvcResultMatchers.status().isCreated())
					.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void createTicketFailure() throws Exception{
		
		when(ticketService.createTicket(any())).thenThrow(TicketNotCreatedException.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/ticket")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(ticket)))
					.andExpect(MockMvcResultMatchers.status().isConflict())
					.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void deleteTicketSuccess() throws Exception{
		
		when(ticketService.deleteTicket(tId)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/ticket/id")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(ticket)))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void deleteTicketFailure() throws Exception{
		
		when(ticketService.deleteTicket(tId)).thenThrow(TicketDoesNotExistsException.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/ticket/id")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(ticket)))
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
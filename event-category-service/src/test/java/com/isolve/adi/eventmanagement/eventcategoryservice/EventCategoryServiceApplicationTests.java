package com.isolve.adi.eventmanagement.eventcategoryservice;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
import com.isolve.adi.eventmanagement.eventcategoryservice.controller.EventCategoryController;
import com.isolve.adi.eventmanagement.eventcategoryservice.exception.EventCategoryDoesNotExistsException;
import com.isolve.adi.eventmanagement.eventcategoryservice.exception.EventCategoryNotCreatedException;
import com.isolve.adi.eventmanagement.eventcategoryservice.model.EventCategory;
import com.isolve.adi.eventmanagement.eventcategoryservice.service.EventCategoryService;

@RunWith(SpringRunner.class)
@WebMvcTest
class EventcategoryserviceApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	private EventCategory eventCategory;
	@MockBean
	private EventCategoryService eventCategoryService;
	@InjectMocks
	private EventCategoryController eventCategoryController;
	private List<EventCategory> eventCategories;
	UUID id = UUID.randomUUID();

	@BeforeEach
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(eventCategoryController).build();
		eventCategory = new EventCategory();
		
		eventCategory.setId(id);
		eventCategory.setCategoryName("Music");
		eventCategory.setImage("Anil");
		
		eventCategories = new ArrayList<EventCategory>();
		eventCategories.add(eventCategory);
		
	}

	@Test
	public void createEventCategorySuccess() throws Exception{
		
		when(eventCategoryService.createEventCategory(any())).thenReturn(eventCategory);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/eventCategory")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(eventCategory)))
					.andExpect(MockMvcResultMatchers.status().isCreated())
					.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void createEventCategoryFailure() throws Exception{
		
		when(eventCategoryService.createEventCategory(any())).thenThrow(EventCategoryNotCreatedException.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/eventCategory")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(eventCategory)))
					.andExpect(MockMvcResultMatchers.status().isConflict())
					.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void deleteEventCategorySuccess() throws Exception{
		
		when(eventCategoryService.deleteEventCategory(id)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/eventCategory/id")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(eventCategory)))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void deleteEventCategoryFailure() throws Exception{
		
		when(eventCategoryService.deleteEventCategory(id)).thenThrow(EventCategoryDoesNotExistsException.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/eventCategory/id")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(eventCategory)))
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
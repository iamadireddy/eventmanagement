package com.isolve.adi.eventmanagement.artistsservice;

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
import com.isolve.adi.eventmanagement.artistsservice.controller.ArtistsController;
import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsDoesNotExistsException;
import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsNotCreatedException;
import com.isolve.adi.eventmanagement.artistsservice.model.Artists;
import com.isolve.adi.eventmanagement.artistsservice.service.ArtistsService;
import com.isolve.adi.eventmanagement.artistsservice.service.ESArtistsService;

@RunWith(SpringRunner.class)
@WebMvcTest
class ArtistsserviceApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	private Artists artists;
	@MockBean
	private ArtistsService artistsService;
	private ESArtistsService esArtistsService;
	@InjectMocks
	private ArtistsController artistsController;
	private List<Artists> artistsList;
	String id = UUID.randomUUID().toString();

	@BeforeEach
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(artistsController).build();
		artists = new Artists();
		
		artists.setId(id);
		artists.setArtistName("Adi");
		artists.setDescription("Hello Adi");
		artists.setImage("Anil");
		
		artistsList = new ArrayList<Artists>();
		artistsList.add(artists);
		
	}

	@Test
	public void createArtistSuccess() throws Exception{
		
		when(artistsService.createArtists(any())).thenReturn(artists);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/artists")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(artists)))
					.andExpect(MockMvcResultMatchers.status().isCreated())
					.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void createArtistFailure() throws Exception{
		
		when(artistsService.createArtists(any())).thenThrow(ArtistsNotCreatedException.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/artists")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(artists)))
					.andExpect(MockMvcResultMatchers.status().isConflict())
					.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void deleteArtistSuccess() throws Exception{
		
		when(artistsService.deleteArtists(id)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/artists/id")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(artists)))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void deleteArtistFailure() throws Exception{
		
		when(artistsService.deleteArtists(id)).thenThrow(ArtistsDoesNotExistsException.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/artists/id")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(artists)))
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
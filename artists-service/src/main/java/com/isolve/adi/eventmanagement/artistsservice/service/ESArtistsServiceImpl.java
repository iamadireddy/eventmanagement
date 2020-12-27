package com.isolve.adi.eventmanagement.artistsservice.service;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isolve.adi.eventmanagement.artistsservice.es.config.ESConfiguration;
import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsDoesNotExistsException;
import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsNotCreatedException;
import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsNotFoundException;
import com.isolve.adi.eventmanagement.artistsservice.model.Artists;
import com.isolve.adi.eventmanagement.artistsservice.repository.ESArtistsReposiory;

@Service
public class ESArtistsServiceImpl implements ESArtistsService{
	
	private final String INDEX = "artists";
	private ESArtistsReposiory esArtistsRepository;
	private ESConfiguration esConfiguration;
	private ObjectMapper objectMapper;
	
	public ESArtistsServiceImpl(ESArtistsReposiory esArtistsRepository, ESConfiguration esConfiguration, ObjectMapper objectMapper) {
		this.esArtistsRepository = esArtistsRepository;
		this.esConfiguration = esConfiguration;
		this.objectMapper = objectMapper;
	}

	@Override
	public Artists createArtists(Artists artist) throws ArtistsNotCreatedException {
		
		Artists artist1 = esArtistsRepository.save(artist);
		
		return artist1;
	}

	@Override
	public List<Artists> getAllArtists() {
		
		return esArtistsRepository.findAll();
	}

	@Override
	public boolean deleteArtists(String id) throws ArtistsDoesNotExistsException {
		
		esArtistsRepository.deleteById(id);
		return true;
	}

	@Override
	public Artists updateArtists(Artists artists) {

		UpdateRequest updateRequest = new UpdateRequest(INDEX, artists.getId());
		try {
			String writeValueAsString = objectMapper.writeValueAsString(artists);
			updateRequest.doc(writeValueAsString, XContentType.JSON);
			 esConfiguration.client().update(updateRequest, RequestOptions.DEFAULT);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return artists;
	}

	@Override
	public Artists getArtistsById(String id) throws ArtistsNotFoundException {
		GetResponse getResponse;
		Artists artists = null;
		try {
			getResponse = esConfiguration.client().get(new GetRequest(INDEX, id), RequestOptions.DEFAULT);
			artists = objectMapper.readValue(getResponse.getSourceAsString(), Artists.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return artists;
	}

}
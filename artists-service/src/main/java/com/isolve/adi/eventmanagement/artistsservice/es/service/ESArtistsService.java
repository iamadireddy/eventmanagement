package com.isolve.adi.eventmanagement.artistsservice.es.service;

import java.util.List;
import java.util.Map;

import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsDoesNotExistsException;
import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsNotCreatedException;
import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsNotFoundException;
import com.isolve.adi.eventmanagement.artistsservice.model.Artists;

public interface ESArtistsService {

	Artists createArtists(Artists artists) throws ArtistsNotCreatedException;

	List<Artists> getAllArtists();
	
	boolean deleteArtists(String id) throws ArtistsDoesNotExistsException;
	
	Map<String, Object> updateArtists(Artists artists);
	
	Map<String, Object> getArtistsById(String id) throws ArtistsNotFoundException;
}

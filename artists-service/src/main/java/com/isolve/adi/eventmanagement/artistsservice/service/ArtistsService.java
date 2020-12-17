package com.isolve.adi.eventmanagement.artistsservice.service;

import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsDoesNotExistsException;
import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsNotCreatedException;
import com.isolve.adi.eventmanagement.artistsservice.model.Artists;

public interface ArtistsService {
	
	Artists createArtists(Artists artists) throws ArtistsNotCreatedException;

	//List<Artists> getAllArtists();
	
	boolean deleteArtists(String id) throws ArtistsDoesNotExistsException;
	
	Artists updateArtists(Artists artists);
	
	//Artists getArtistsById(String id) throws ArtistsNotFoundException;
}
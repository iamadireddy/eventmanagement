package com.isolve.adi.eventmanagement.artistsservice.service;

import java.util.List;
import java.util.UUID;

import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsDoesNotExistsException;
import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsNotCreatedException;
import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsNotFoundException;
import com.isolve.adi.eventmanagement.artistsservice.model.Artists;

public interface ArtistsService {
	
	Artists createArtists(Artists artists) throws ArtistsNotCreatedException;

	List<Artists> getAllArtists();
	
	boolean deleteArtists(UUID id) throws ArtistsDoesNotExistsException;
	
	Artists updateArtists(Artists artists);
	
	Artists getArtistsById(UUID id) throws ArtistsNotFoundException;
}
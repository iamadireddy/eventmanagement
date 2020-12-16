package com.isolve.adi.eventmanagement.artistsservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsDoesNotExistsException;
import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsNotCreatedException;
import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsNotFoundException;
import com.isolve.adi.eventmanagement.artistsservice.model.Artists;
import com.isolve.adi.eventmanagement.artistsservice.repository.ArtistsRepository;

@Service
public class ArtistsServiceImpl implements ArtistsService {
	
	private ArtistsRepository artistsRepository;
	
	@Autowired
	public ArtistsServiceImpl(ArtistsRepository artistsRepository) {
		this.artistsRepository = artistsRepository;
	}

	@Override
	public Artists createArtists(Artists artists) throws ArtistsNotCreatedException {

		//elasticsearchOperations.indexOps(Artists.class).create();
		
		if(artistsRepository.findById(artists.getId()).isPresent()) {
			throw new ArtistsNotCreatedException("Artis was already exists");
		}else {
			Artists artist = artistsRepository.insert(artists);
			return artist;
		}
	}

	@Override
	public List<Artists> getAllArtists() {
		
		return artistsRepository.findAll();
	}

	@Override
	public boolean deleteArtists(UUID id) throws ArtistsDoesNotExistsException {

		if(!artistsRepository.findById(id).isPresent())
			throw new ArtistsDoesNotExistsException("Artist was not found");
		else {
			artistsRepository.deleteById(id);
			return true;
		}
	}

	@Override
	public Artists updateArtists(Artists artists) {

		Artists updateArtist = artistsRepository.save(artists);
		if(updateArtist != null) {
			return artists;
		}
		else
			return null;
	}

	@Override
	public Artists getArtistsById(UUID id) throws ArtistsNotFoundException {
		
		Optional<Artists> artists = artistsRepository.findById(id);
		if(!artists.isPresent())
			throw new ArtistsNotFoundException("Artist was not found");
		else
			return artists.get();
	}
}
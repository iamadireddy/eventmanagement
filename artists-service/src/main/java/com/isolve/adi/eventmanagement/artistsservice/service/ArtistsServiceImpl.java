package com.isolve.adi.eventmanagement.artistsservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsDoesNotExistsException;
import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsNotCreatedException;
import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsNotFoundException;
import com.isolve.adi.eventmanagement.artistsservice.model.Artists;
import com.isolve.adi.eventmanagement.artistsservice.repository.ArtistsRepository;

@Service
@Transactional
public class ArtistsServiceImpl implements ArtistsService {

	private ArtistsRepository artistsRepository;
	private ESArtistsService eSArtistsService;

	@Autowired
	public ArtistsServiceImpl(ArtistsRepository artistsRepository, ESArtistsService eSArtistsService) {
		this.artistsRepository = artistsRepository;
		this.eSArtistsService = eSArtistsService;
	}

	@Override
	public Artists createArtists(Artists artists) throws ArtistsNotCreatedException {
		
		if (artistsRepository.findById(artists.getId().toString()).isPresent()) {
			throw new ArtistsNotCreatedException("Artis was already exists");
		} else {
			Artists artist = artistsRepository.insert(artists);
			eSArtistsService.createArtists(artists);
			return artist;
		}
	}

	/*@Override
	public List<Artists> getAllArtists() {

		return artistsRepository.findAll();
	}*/

	@Override
	public boolean deleteArtists(String id) throws ArtistsDoesNotExistsException {
		
		System.out.println("Inside Delete");

		if (!artistsRepository.findById(id).isPresent())
			throw new ArtistsDoesNotExistsException("Artist was not found");
		else {
			artistsRepository.deleteById(id);
			eSArtistsService.deleteArtists(id);
			return true;
		}
	}

	@Override
	public Artists updateArtists(Artists artists) {

		Artists updateArtist = artistsRepository.save(artists);
		if (updateArtist != null) {
			eSArtistsService.updateArtists(artists);
			return artists;
		} else
			return null;
	}

	/*@Override
	public Artists getArtistsById(String id) throws ArtistsNotFoundException {

		Optional<Artists> artists = artistsRepository.findById(id);
		if (!artists.isPresent())
			throw new ArtistsNotFoundException("Artist was not found");
		else
			return artists.get();
	}*/
}
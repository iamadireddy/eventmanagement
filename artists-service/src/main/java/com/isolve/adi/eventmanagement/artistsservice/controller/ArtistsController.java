package com.isolve.adi.eventmanagement.artistsservice.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsDoesNotExistsException;
import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsNotCreatedException;
import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsNotFoundException;
import com.isolve.adi.eventmanagement.artistsservice.model.Artists;
import com.isolve.adi.eventmanagement.artistsservice.service.ArtistsService;

@RestController
@RequestMapping("/api/v1/artists")
public class ArtistsController {
	
	private ArtistsService artistsService;
	
	public ArtistsController(ArtistsService artistsService) {
		this.artistsService = artistsService;
	}

	@PostMapping
	public ResponseEntity<?> createArtist(@RequestBody Artists artists){
		
		System.out.println("Artist name " + artists.getArtistName());
		
		try {
			artists.setId(UUID.randomUUID());
			return new ResponseEntity<>(artistsService.createArtists(artists), HttpStatus.CREATED);
		} catch (ArtistsNotCreatedException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Artist was already exists", HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getArtist(@PathVariable UUID id){
		
		try {
			Artists artistsById = artistsService.getArtistsById(id);
			if(artistsById != null) {
				return new ResponseEntity<>(artistsById, HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Artist was not exists", HttpStatus.NOT_FOUND);
			}
		} catch (ArtistsNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Artist was not exists", HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/all")
	private ResponseEntity<?> getAllArtists(){
		
		return new ResponseEntity<>(artistsService.getAllArtists(), HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<?> updateArtist(@RequestBody Artists artists){
		
		Artists updateArtists = artistsService.updateArtists(artists);
		if(updateArtists != null)
			return new ResponseEntity<>("Artist was updated successfully", HttpStatus.OK);
		else
			return new ResponseEntity<>("Artist was not updated successfully", HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteArtist(@PathVariable UUID id){
		
		try {
			return new ResponseEntity<>(artistsService.deleteArtists(id), HttpStatus.OK);
		} catch (ArtistsDoesNotExistsException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Artist was not exists", HttpStatus.CONFLICT);
		}
	}
}

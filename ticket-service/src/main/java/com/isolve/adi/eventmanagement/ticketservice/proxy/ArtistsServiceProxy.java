package com.isolve.adi.eventmanagement.ticketservice.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.isolve.adi.eventmanagement.ticketservice.model.Artists;

@FeignClient(name = "artists-service", url = "localhost:8765")
public interface ArtistsServiceProxy {

	@GetMapping("/artists-service/api/v1/artists/all")
	public List<Artists> getArtistsList();
}

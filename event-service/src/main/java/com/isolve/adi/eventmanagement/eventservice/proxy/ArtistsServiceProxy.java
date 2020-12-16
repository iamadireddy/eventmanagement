package com.isolve.adi.eventmanagement.eventservice.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.isolve.adi.eventmanagement.eventservice.model.Artists;

@FeignClient(name = "artists-service", url = "localhost:8765/artists-service")
public interface ArtistsServiceProxy {

	@GetMapping("/api/v1/artists/all")
	public List<Artists> getArtistsList();
}

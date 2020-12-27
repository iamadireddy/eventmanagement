package com.isolve.adi.eventmanagement.artistsservice.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.isolve.adi.eventmanagement.artistsservice.model.Artists;

@Repository
public interface ESArtistsReposiory extends ElasticsearchRepository<Artists, String>{

	List<Artists> findAll();
	void deleteById(String id);
}

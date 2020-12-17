package com.isolve.adi.eventmanagement.artistsservice.es.repository;

import java.util.UUID;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Repository;

import com.isolve.adi.eventmanagement.artistsservice.model.Artists;

@Repository
//@EnableElasticsearchRepositories
public interface ESArtistsReposiory extends ElasticsearchRepository<Artists, UUID>{

}

package com.isolve.adi.eventmanagement.artistsservice.es;

import java.util.UUID;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.isolve.adi.eventmanagement.artistsservice.model.Artists;

@Repository
public interface ESArtistsReposiory extends ElasticsearchRepository<Artists, UUID>{

}

package com.isolve.adi.eventmanagement.artistsservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.isolve.adi.eventmanagement.artistsservice.model.Artists;

@Repository
public interface ArtistsRepository extends MongoRepository<Artists, String>{

}

package com.isolve.adi.eventmanagement.artistsservice.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.isolve.adi.eventmanagement.artistsservice.model.Artists;

@EnableMongoRepositories
@Repository
public interface ArtistsRepository extends MongoRepository<Artists, UUID>{

}

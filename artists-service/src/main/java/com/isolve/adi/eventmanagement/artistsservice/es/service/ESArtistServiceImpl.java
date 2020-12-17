package com.isolve.adi.eventmanagement.artistsservice.es.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isolve.adi.eventmanagement.artistsservice.es.repository.ESArtistsReposiory;
import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsDoesNotExistsException;
import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsNotCreatedException;
import com.isolve.adi.eventmanagement.artistsservice.exception.ArtistsNotFoundException;
import com.isolve.adi.eventmanagement.artistsservice.model.Artists;

@Service
public class ESArtistServiceImpl implements ESArtistsService{
	
	//private final String INDEX = "artistInfo";

	private ESArtistsReposiory esArtistsReposiory;
    private RestHighLevelClient restHighLevelClient;
    private ObjectMapper objectMapper;

    @Autowired
    public ESArtistServiceImpl(ESArtistsReposiory esArtistsReposiory, ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
    	this.esArtistsReposiory = esArtistsReposiory;
    	this.objectMapper = objectMapper;
        this.restHighLevelClient = restHighLevelClient;
    }

	@Override
	public Artists createArtists(Artists artist) throws ArtistsNotCreatedException {
		
		 Map<String, Object> dataMap = objectMapper.convertValue(artist, Map.class);
	        IndexRequest indexRequest = new IndexRequest()
	                						.source(dataMap);
	        try {
	            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);//If nothing need to customize then we can use RequestOptions.DEFAULT
	        } catch (java.io.IOException ex){
	            ex.getLocalizedMessage();
	        }
	        return artist;
	}

	@Override
	public List<Artists> getAllArtists() {
		
		List<Artists> artists = new ArrayList<>();		
		Iterable<Artists> findAll = esArtistsReposiory.findAll();
		findAll.forEach(artists::add);
		
		return artists;
	}

	@Override
	public boolean deleteArtists(String id) throws ArtistsDoesNotExistsException {
		boolean isArtistDeleted = false;
		DeleteRequest deleteRequest = new DeleteRequest(id);
        try {
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            if(deleteResponse != null)
            	isArtistDeleted = true;
        } catch (java.io.IOException e){
            e.getLocalizedMessage();
        }
        
        return isArtistDeleted;
	}

	@Override
	public Map<String, Object> updateArtists(Artists artist) {
	
		UpdateRequest updateRequest = new UpdateRequest()
                						.fetchSource(true);// Fetch Object after its update
        Map<String, Object> error = new HashMap<>();
        error.put("Error", "Unable to update book");
        try {
            String artistJson = objectMapper.writeValueAsString(artist);
            updateRequest.doc(artistJson, XContentType.JSON);
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            Map<String, Object> sourceAsMap = updateResponse.getGetResult().sourceAsMap();
            return sourceAsMap;
        }catch (JsonProcessingException e){
            e.getMessage();
        } catch (java.io.IOException e){
            e.getLocalizedMessage();
        }
        return error;
	}

	@Override
	public Map<String, Object> getArtistsById(String id) throws ArtistsNotFoundException {
		
		 GetRequest getRequest = new GetRequest(id);
	        GetResponse getResponse = null;
	        try {
	            getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
	        } catch (java.io.IOException e){
	            e.getLocalizedMessage();
	        }
	        Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
	        return sourceAsMap;
	}

}

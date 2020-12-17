package com.isolve.adi.eventmanagement.artistsservice.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
//import org.springframework.data.mongodb.core.mapping.Document;

@org.springframework.data.mongodb.core.mapping.Document

//@Document(indexName = "artists-service", type = "artists")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Artists {
	
	@Id
	private String id;
	private String artistName;
	private String image;
	private String description;
	
	public Artists() {	}
	
	public Artists(String id,String artistName, String image, String description) {
		this.id = id;
		this.artistName = artistName;
		this.image = image;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Artists [id=" + id + ", artistName=" + artistName + ", image=" + image + ", description=" + description
				+ "]";
	}
}
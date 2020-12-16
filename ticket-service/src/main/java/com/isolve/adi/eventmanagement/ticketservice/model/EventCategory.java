package com.isolve.adi.eventmanagement.ticketservice.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class EventCategory {

	@Id
	private UUID id;
	private String categoryName;
	private String image;
	
	public EventCategory() {	}

	public EventCategory(UUID id, String categoryName, String image) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.image = image;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "EventCategory [id=" + id + ", categoryName=" + categoryName + ", image=" + image + "]";
	}
}
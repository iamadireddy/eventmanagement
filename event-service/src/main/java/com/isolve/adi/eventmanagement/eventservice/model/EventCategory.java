package com.isolve.adi.eventmanagement.eventservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Document(indexName = "eventcategory")
public class EventCategory {

	@Id
	private String id;
	private String categoryName;
	private String image;
	
	public EventCategory() {	}

	public EventCategory(String id, String categoryName, String image) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
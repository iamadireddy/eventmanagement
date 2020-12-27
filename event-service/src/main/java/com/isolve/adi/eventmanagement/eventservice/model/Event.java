package com.isolve.adi.eventmanagement.eventservice.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Document(indexName = "event")
public class Event {

	@Id
	private String id;
	private String eventTitle;
	@JsonDeserialize(using = LocalDateDeserializer.class)  
	@JsonSerialize(using = LocalDateSerializer.class)  
	private LocalDate eventStartDate;
	@JsonDeserialize(using = LocalDateDeserializer.class)  
	@JsonSerialize(using = LocalDateSerializer.class)  
	private LocalDate eventEndDate;
	private String description;
	private String eventImage;
	private List<Artists>artistsList;
	private EventCategory eventCategory;
	private Double ticketPrice;
	private Integer totalSeats;
	private Integer availableSeats;
	
	public Event() {	}

	public Event(String id, String eventTitle, LocalDate eventStartDate, LocalDate eventEndDate, String description,
			String eventImage, List<Artists> artistsList, EventCategory eventCategory, Double ticketPrice,
			Integer totalSeats, Integer availableSeats) {
		this.id = id;
		this.eventTitle = eventTitle;
		this.eventStartDate = eventStartDate;
		this.eventEndDate = eventEndDate;
		this.description = description;
		this.eventImage = eventImage;
		this.artistsList = artistsList;
		this.eventCategory = eventCategory;
		this.ticketPrice = ticketPrice;
		this.totalSeats = totalSeats;
		this.availableSeats = availableSeats;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public LocalDate getEventStartDate() {
		return eventStartDate;
	}

	public void setEventStartDate(LocalDate eventStartDate) {
		this.eventStartDate = eventStartDate;
	}

	public LocalDate getEventEndDate() {
		return eventEndDate;
	}

	public void setEventEndDate(LocalDate eventEndDate) {
		this.eventEndDate = eventEndDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEventImage() {
		return eventImage;
	}

	public void setEventImage(String eventImage) {
		this.eventImage = eventImage;
	}

	public List<Artists> getArtistsList() {
		return artistsList;
	}

	public void setArtistsList(List<Artists> artistsList) {
		this.artistsList = artistsList;
	}

	public EventCategory getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(EventCategory eventCategory) {
		this.eventCategory = eventCategory;
	}

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public Integer getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(Integer totalSeats) {
		this.totalSeats = totalSeats;
	}

	public Integer getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(Integer availableSeats) {
		this.availableSeats = availableSeats;
	}	
}
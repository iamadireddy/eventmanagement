package com.isolve.adi.eventmanagement.eventcategoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EventCategoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventCategoryServiceApplication.class, args);
	}

}

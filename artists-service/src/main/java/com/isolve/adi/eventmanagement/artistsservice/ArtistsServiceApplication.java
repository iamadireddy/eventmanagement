package com.isolve.adi.eventmanagement.artistsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ArtistsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArtistsServiceApplication.class, args);
	}

}

package com.isolve.adi.eventmanagement.artistsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableAutoConfiguration
@ComponentScan("{com.isolve.adi.eventmanagement.artistsservice}")
public class ArtistsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArtistsServiceApplication.class, args);
	}

}

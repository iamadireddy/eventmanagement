package com.isolve.adi.eventmanagement.artistsservice.es.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories("com.isolve.adi.eventmanagement.artistsservice.es")
public class ESConfiguration {

	@Bean
	public RestHighLevelClient client() {
		
		ClientConfiguration clientConfiguration = ClientConfiguration.builder()
													.connectedTo("localhost:9200")
													.build();
		
		return RestClients.create(clientConfiguration).rest();
	}
	
	@Bean
	public ElasticsearchOperations elasticsearchOperations() {
		return new ElasticsearchRestTemplate(client());
	}
}

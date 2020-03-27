package com.msa.config.resttemplate;

import java.time.Duration;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

	private final RestTemplateBuilder restTemplateBuilder;

	@Bean
	public RestTemplate reviewTemplate(@Value("${int.review-api.url}") String reviewAPI) {
		return restTemplateBuilder.rootUri(reviewAPI)
//	        .additionalInterceptors(new RestTemplateClientHttpRequestInterceptor())
	        .errorHandler(new RestTemplateErrorHandler())
	        .setConnectTimeout(Duration.ofMinutes(3))
	        .build();
	}

	@Bean
	public RestTemplate productTemplate(@Value("${int.product-api.url}") String productAPI) {
		return restTemplateBuilder.rootUri(productAPI)
//	        .additionalInterceptors(new RestTemplateClientHttpRequestInterceptor())
	        .errorHandler(new RestTemplateErrorHandler())
	        .setConnectTimeout(Duration.ofMinutes(3))
	        .build();
	}

}
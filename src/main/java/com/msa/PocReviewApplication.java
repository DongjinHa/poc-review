package com.msa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PocReviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocReviewApplication.class, args);
	}
	
/*	
	@Bean
	public RestTemplate localRestTemplate(RestTemplateBuilder restTemplateBuilder) {
		//rootUri 설정. 실제 사용시에는 /로 시작하는 나머지 주소를 Uri로 설정하면됩니다.
		return restTemplateBuilder.rootUri("http://localhost:9091/review").build();
	}
*/
	
	@Bean
	public WebClientCustomizer webClientCustomizer() {	//webClient 사용을 위한 baseUrl설정
		return webClientBuilder -> webClientBuilder.baseUrl("http://localhost:9091/review");
	}

}

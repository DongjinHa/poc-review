package com.msa.config.resttemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;

@Slf4j
public class RestTemplateClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

	@NonNull
	@Override
	public ClientHttpResponse intercept(@NonNull final HttpRequest request,
										@NonNull final byte[] body,
										@NonNull final ClientHttpRequestExecution execution) throws IOException {
		final ClientHttpResponse response = execution.execute(request, body);

		loggingRequest(request, body);
		loggingResponse(response);
		return execution.execute(request, body);
	}
  
	private void loggingRequest(final HttpRequest request, byte[] body) {
		log.debug("========Request=======");
	    log.debug("Headers: {}", request.getHeaders());
	    log.debug("Request Method: {}", request.getMethod());
	    log.debug("Request URI: {}", request.getURI());
	    log.debug("Request body: {}",
	    			body.length == 0 ? null : new String(body, StandardCharsets.UTF_8));
	}

	private void loggingResponse(ClientHttpResponse response) throws IOException {
	    final String body = getBody(response);
	
	    log.debug("=======Response=======");
	    log.debug("Headers: {}", response.getHeaders());
	    log.debug("Response Status : {}", response.getRawStatusCode());
	    log.debug("Request body: {}", body);
	}

	private String getBody(@NonNull final ClientHttpResponse response) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(response.getBody()))) {
			return br.readLine();
		}
	}

}
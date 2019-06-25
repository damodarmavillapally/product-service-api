package com.epam.java.training.configuration;

import org.springframework.context.annotation.Bean;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.ErrorDecoder;



public class ProductReviewClientConfig {
	 
	  @Bean
	  public RequestInterceptor interceptor() {
		  
	    return (RequestTemplate template) -> template.header("API_KEY", "12345");
	  }
	 
	  @Bean
	  public ErrorDecoder errorDecoder() {
	    final ErrorDecoder defaultErrorDecoder = new ErrorDecoder.Default();
	 
	    // TODO: Extract this to an inner class
	    return (String methodKey, Response response) -> {
	      if (response.status() == 403) {
	        return new Exception(response.reason());
	      }
	      return defaultErrorDecoder.decode(methodKey, response);
	    };
	  }
}
package com.springboot.microservice.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
 
@RestController
public class CircuitBreakerController {
	
	private Logger logger =LoggerFactory.getLogger(CircuitBreakerController.class);
	
	@GetMapping(path="/sample-api")
	//@Retry(name="sample-api" , fallbackMethod="hardcodedResponse")
	
	@CircuitBreaker(name="sample-api" , fallbackMethod="hardcodedResponse") 
	
	//Rate limiting - want to have 100 req. in 10s then use it
	//@RateLimiter(name="default")
	
	@Bulkhead(name="default")
	public String sampleApi()
	{
		
		logger.info("sample api call received ");
		//ResponseEntity<String> responseEntity = new RestTemplate().getForEntity("http://localhost:8080/this-will-not-work",String.class);
	
		//return responseEntity.getBody();
		return "sample-api";
		 
	}
	
	public String hardcodedResponse (Exception e)
	{
		return "Fallback";
	}

}

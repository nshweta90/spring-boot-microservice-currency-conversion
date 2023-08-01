package com.springboot.microservice.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


 
@RestController
public class CurrencyConversionController {

	@Autowired
	CurrencyExchangeProxy proxy;
	
	@GetMapping(path="/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion retrieveCurrencyConversion(@PathVariable String from,@PathVariable String to ,@PathVariable BigDecimal quantity) {
			 
		HashMap<String,String> uriVariables=new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		//calling currency exchange service internally to get the response
		ResponseEntity<CurrencyConversion> resposeEntiry = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class,uriVariables);
		CurrencyConversion responseBody = resposeEntiry.getBody();
		return new CurrencyConversion(
				responseBody.getId(),
				from,
				to,
				quantity,
				responseBody.getConversionMultiple(),
				quantity.multiply(responseBody.getConversionMultiple()),
				responseBody.getEnvironment());
		
	}
	
	
	@GetMapping(path="/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion retrieveCurrencyConversionFeign(@PathVariable String from,@PathVariable String to ,@PathVariable BigDecimal quantity) {
			 
		 CurrencyConversion responseBody =  proxy.retrieveCurrencyExchangeRate(from, to);
		return new CurrencyConversion(
				responseBody.getId(),
				from,
				to,
				quantity,
				responseBody.getConversionMultiple(),
				quantity.multiply(responseBody.getConversionMultiple()),
				responseBody.getEnvironment() + " feign");
		
	}
}

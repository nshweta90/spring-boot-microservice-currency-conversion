package com.springboot.microservice.currencyexchangeservice;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController { 
	
	@Autowired
	private CurrencyExchangeRepository repository;
	
	@Autowired
	private Environment environment;
	
	
	@GetMapping(path="/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveCurrencyExchangeRate(@PathVariable String from,@PathVariable String to) {
	
		CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
				//new CurrencyExchange(1000l,from,to,BigDecimal.valueOf(50));
		
		if(currencyExchange == null)
		{
			throw new RuntimeException("Unable to get value from  "+from+" to "+to);
		}
		
		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);
		
		return currencyExchange;
		
	}
}

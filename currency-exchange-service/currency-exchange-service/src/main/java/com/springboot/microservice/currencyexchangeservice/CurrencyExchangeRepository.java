package com.springboot.microservice.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;

 
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {

	
	public CurrencyExchange findByFromAndTo(String from,String to);
}

package com.springboot.microservice.apigateway;

import java.util.function.Function;

import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApirGatewayConfiguration {
     
	
	@Bean
	public RouteLocator getRout(RouteLocatorBuilder builder) {
		
		 
		
		Function<PredicateSpec, Buildable<Route>> routfunction = 
				p -> p.path("/get")
				.filters(f->f.addRequestHeader("MyHeader", "123333").addRequestParameter("param", "11")) 
				.uri("http://httpbin.org:80"); 
				
				
		return builder.routes() 
				.route(routfunction)
				.route(p -> p.path("/currency-exchange/**").uri("lb://currency-exchange-service"))
				.route(p -> p.path("/currency-conversion-feign/**").uri("lb://currency-conversion-service"))
				.route(p->p.path("/currency-conversion-new/**").filters(f->f.rewritePath("/currency-conversion-new/(?<segment>.*)", "/currency-conversion-feign/${segment}")).uri("lb://currency-conversion-service"))
				.build();
		
	}
}

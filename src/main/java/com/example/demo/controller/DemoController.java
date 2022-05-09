package com.example.demo.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.custom_annotations.LoggingTrack;
import com.example.demo.entity.Customer;
import com.example.demo.services.DemoService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class DemoController {

	@Autowired
	DemoService demoService;
	
	  @Autowired
	  org.springframework.cache.CacheManager cacheManager;

//	@LoggingTrack
	@GetMapping("connect")
	public String connect() {
//		int j = 1/0;
		System.out.println("Hii, welcome to New Project...");
		return "Welcome Milind";
	}

	@PostMapping("saveCustomer")
	public Customer saveCustomer(@RequestBody Customer customer) {
		return this.demoService.save(customer);
	}

	/**
	 * Calling this API with url : http://localhost:8081/SHI/connectWebServices
	 * @return updateStatus
	 * @throws URISyntaxException
	 */
	@GetMapping("connectWebServices")
	public String demo() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> map = new HashMap<>();
		map.put("username", "milind");
		map.put("passcode", "emkj@1993");
		Map<String, String> param = new HashMap<>();
		param.put("id", "ET007");
		param.put("name", "Milind Kailas Jaid");
		
		// REST Call with RestTemplate 1
		Object object = restTemplate.postForObject("http://localhost:8082/connectWS2/{id}/{name}?facility=CT Scan", map,
				Object.class, param);
		System.out.println("1 >>" + object);
		
		String webUrl = "http://localhost:8082/update/{id}";
		// REST Call with RestTemplate 2
		restTemplate.put(webUrl, map, param);
		System.out.println("Success call 2");
		
		// REST Call with RestTemplate 3
		HttpEntity<Map<String,String>> requestEntity = new HttpEntity<Map<String,String>>(map, new HttpHeaders());
		ResponseEntity<String> responseEntity = restTemplate.exchange(webUrl, HttpMethod.PUT, requestEntity, String.class,param);
		System.out.println("Update call with update flag "+responseEntity.getBody());
		
		//REST call with WebClient
		//Use Flux when response will emit 0 to N element
		Flux<String> tweetFlux = WebClient.builder().baseUrl("http://localhost:8082").build()
			      .put()
			      .uri(uriBuilder -> uriBuilder
			    		  .path("/update/{id}")
			    		  .build(param))
			      .bodyValue(map)
			      .retrieve()
			      .bodyToFlux(String.class);
		
		//	Use Mono when response will emit 1 element only	
	/**	Mono<String> tweetMono = WebClient.builder().baseUrl("http://localhost:8082").build()
	      .put()
	      .uri(uriBuilder -> uriBuilder
	    		  .path("/update/{id}")
	    		  .build(param))
	      .bodyValue(map)
	      .retrieve()
	      .bodyToMono(String.class);*/

		tweetFlux.subscribe(tweet -> System.out.println("Response with WebClient >>> "+tweet.toString()));
			    System.out.println("Exiting NON-BLOCKING Controller!");
		
		return "Successfully Connected with Web Service 2";
	}
	
	@PostMapping("createCustomer")
	public Customer createCustomer(@RequestBody Customer customer) {
		return demoService.save(customer);
		
	}
	
	@PostMapping("mapConnect")
	public String mapDemo(@RequestBody Map<String,String> customer) {
		System.out.println("Customer Details in Map DS >>> "+customer);
		if(customer instanceof HashMap<?, ?>) {
			System.out.println("HashMap");
		}
		return "Success";
		
	}
	
	 @GetMapping(path = "/square/{number}")
	    public String getSquare(@PathVariable Long number) {
	       System.out.println("call numberService to square {}"+number);
	       org.springframework.cache.Cache ch = cacheManager.getCache("squareCache");
	       System.out.println("Available Cache >>> "+ ch);
	        return String.format("{\"square\": %s}", demoService.square(number));
	    }
	
	
}

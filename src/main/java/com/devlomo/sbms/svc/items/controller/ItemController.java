package com.devlomo.sbms.svc.items.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devlomo.sbms.svc.items.service.ItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;

import com.devlomo.sbms.svc.items.model.Item;
import com.devlomo.sbms.svc.items.model.Product;
import com.devlomo.sbms.svc.items.model.Response;

@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {
	
	@Autowired
	private CircuitBreakerFactory cbFactory;

	@Autowired
	private ItemService itemService;
	
	@GetMapping("/find")
	public ResponseEntity<Object> getAll(@RequestHeader(name="token-request") String token){
		log.info("find all items");
		log.warn("request token: {}", token);
		try {
			List<Item> items = itemService.findAll();
			return Response.generate("find all", HttpStatus.OK, items);
		} catch (Exception e) {
			log.warn("error while retrieving all products, error: {}", e.getMessage());
			log.error("error: {}", e);
			e.printStackTrace();
			return Response.generate("error", HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@GetMapping("/find/detail")
	public ResponseEntity<Object> getDetail(@RequestParam Long id, 
											@RequestParam Integer amount){
		log.info("find item by id: {}, amount: {}", id, amount);
		try {
			return cbFactory.create("items")
					.run(() -> {
						Item item = itemService.findById(id, amount);
						return Response.generate("find all", HttpStatus.OK, item);
					}, e -> alternativeMethod(id, amount, e));
		} catch (Exception e) {
			log.warn("error while retrieving item by id:{}", id);
			log.error("error: {}", e);
			return Response.generate("error", HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@CircuitBreaker(name = "items", fallbackMethod = "alternativeMethod")
	@GetMapping("/find2/detail")
	public ResponseEntity<Object> getDetail2(@RequestParam Long id, 
											@RequestParam Integer amount){
		log.info("find item by id: {}, amount: {}", id, amount);
		try {
			Item item = itemService.findById(id, amount);
			return Response.generate("find all", HttpStatus.OK, item);
		} catch (Exception e) {
			log.warn("error while retrieving item by id:{}", id);
			log.error("error: {}", e);
			return Response.generate("error", HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@TimeLimiter(name = "items")
	@GetMapping("/find3/detail")
	public ResponseEntity<Object> getDetail3(@RequestParam Long id, 
											@RequestParam Integer amount){
		log.info("find item by id: {}, amount: {}", id, amount);
		try {
			Item item = itemService.findById(id, amount);
			return Response.generate("find all", HttpStatus.OK, item);
		} catch (Exception e) {
			log.warn("error while retrieving item by id:{}", id);
			log.error("error: {}", e);
			return Response.generate("error", HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	public ResponseEntity<Object> alternativeMethod(Long id, Integer amount, Throwable e) {
		log.error("error from ms: {}", e.getMessage());
		Item item = new Item();
		Product product = new Product();
		
		item.setAmount(amount);
		product.setId(id);
		product.setName("alternative value");
		product.setPrice(0.0d);
		item.setProduct(product);
		
		return Response.generate("find all", HttpStatus.OK, item);
	}
	
}

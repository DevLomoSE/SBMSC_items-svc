package com.devlomo.sbms.svc.items.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.devlomo.sbms.svc.items.model.Item;
import com.devlomo.sbms.svc.items.model.Product;
import com.devlomo.sbms.svc.items.model.RestResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private RestTemplate restClient;

	@Override
	public List<Item> findAll() {
		log.info("retrieving products from the ms products using restclient");
		
		RestResponse response = restClient.getForObject("http://lomopc.com:8001/sbms/products", 
														RestResponse.class);
		List<Product> products = Arrays.asList(response.getData());
		
		return products.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer amount) {
		log.info("retrieving product from the ms products");
		
		Map<String, Long> pathVariables = new HashMap<String, Long>();
		pathVariables.put("id", id);
		
		RestResponse response = restClient
									.getForObject("http://lomopc.com:8001/sbms/products/find?id={id}", 
												 RestResponse.class, pathVariables);
		
		Product product = new Product();
		for(Product p: response.getData()) {
			product.setId(p.getId());
			product.setName(p.getName());
			product.setPrice(p.getPrice());
			product.setCreatedAt(p.getCreatedAt());	
		}
		
		return new Item(product, amount);
	}

}

package com.devlomo.sbms.svc.items.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.devlomo.sbms.svc.items.config.client.ProductRestClient;
import com.devlomo.sbms.svc.items.model.Item;
import com.devlomo.sbms.svc.items.model.Product;
import com.devlomo.sbms.svc.items.model.RestResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Primary
public class ItemServiceFeingImpl implements ItemService {
	
	@Autowired
	private ProductRestClient productRestClient;

	@Override
	public List<Item> findAll() {
		log.info("retrieving products from the ms products using feign");
		
		RestResponse response = productRestClient.findAll();
											
		List<Product> products = Arrays.asList(response.getData());
		
		return products.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer amount) {
		log.info("retrieving product from the ms products using feign");
		
		RestResponse response = productRestClient.findById(id);
		
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

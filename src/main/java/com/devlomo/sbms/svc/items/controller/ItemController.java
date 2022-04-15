package com.devlomo.sbms.svc.items.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devlomo.sbms.svc.items.service.ItemService;

import lombok.extern.slf4j.Slf4j;

import com.devlomo.sbms.svc.items.model.Item;
import com.devlomo.sbms.svc.items.model.Response;

@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@GetMapping("/find")
	public ResponseEntity<Object> getAll(){
		log.info("find all items");
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
			Item item = itemService.findById(id, amount);
			return Response.generate("find all", HttpStatus.OK, item);
		} catch (Exception e) {
			log.warn("error while retrieving item by id:{}", id);
			log.error("error: {}", e);
			return Response.generate("error", HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
}

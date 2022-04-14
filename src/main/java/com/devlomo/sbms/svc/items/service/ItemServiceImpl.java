package com.devlomo.sbms.svc.items.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.devlomo.sbms.svc.items.model.Item;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private RestTemplate restClient;

	@Override
	public List<Item> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item findById(Long id, Integer amount) {
		// TODO Auto-generated method stub
		return null;
	}

}

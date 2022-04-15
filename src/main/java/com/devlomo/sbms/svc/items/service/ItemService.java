package com.devlomo.sbms.svc.items.service;

import java.util.List;

import com.devlomo.sbms.svc.items.model.Item;

public interface ItemService {
	
	public List<Item> findAll();
	
	public Item findById(Long id, Integer amount);

}

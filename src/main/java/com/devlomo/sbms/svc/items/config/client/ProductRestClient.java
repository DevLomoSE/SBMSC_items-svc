package com.devlomo.sbms.svc.items.config.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.devlomo.sbms.svc.items.model.Item;
import com.devlomo.sbms.svc.items.model.RestResponse;

@FeignClient(name = "ms-products-svc", url = "${sbms.api.products.endpoint}${sbms.api.products.resource}")
public interface ProductRestClient {
	
	@GetMapping
	public RestResponse findAll();
	
	@GetMapping("/find")
	public RestResponse findById(@RequestParam Long id);

}

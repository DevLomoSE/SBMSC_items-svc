package com.devlomo.sbms.svc.items.config.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.devlomo.sbms.svc.items.model.RestResponse;

@FeignClient("ms-products-svc")
public interface ProductRestClient {
	
	@GetMapping("${sbms.api.products.resource}")
	public RestResponse findAll();
	
	@GetMapping("${sbms.api.products.resource}/find")
	public RestResponse findById(@RequestParam Long id);

}

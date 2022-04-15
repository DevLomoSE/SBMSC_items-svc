package com.devlomo.sbms.svc.items.model;

import java.util.Arrays;

public class RestResponse {
	
	private Product[] data;
	private String message;
	private Integer status;
	
	public RestResponse() {
		super();
	}

	public Product[] getData() {
		return data;
	}

	public void setData(Product[] data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "RestResponse [ data=" + Arrays.toString(data) + ", message=" + message + ", status=" + status + " ]";
	}

}

package com.codechronicle.model;

import java.util.List;

public class LicenseQueryResponse {
	private String message;
	private List<LicenseQueryResponseItem> responseItems;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<LicenseQueryResponseItem> getResponseItems() {
		return responseItems;
	}
	public void setResponseItems(List<LicenseQueryResponseItem> responseItems) {
		this.responseItems = responseItems;
	}
	
	
}

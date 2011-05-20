package com.codechronicle.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This entity describes a specific instance of a license. For example, "Apache 2". For simplicity, we do
 * not incorporate pseudonyms (since there isn't really a standard for how different projects refer to the
 * same license. This may be a future enhancement.
 * 
 * @author SRoy
 *
 */
@Entity
public class License {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	
	private String name;
	private String url;
	
	public License(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return name + " [" + url + "]";
	}
	
}

package com.codechronicle.dto;

import java.util.ArrayList;
import java.util.List;

import com.codechronicle.model.LicensePermission;
import com.codechronicle.model.LicensePolicy;

public class LicensePolicyDTO extends BaseDTO {

	private String id;
	
	private String name;
	
	public LicensePolicyDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public LicensePolicyDTO(LicensePolicy lp) {
		copyProperties(lp, this);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

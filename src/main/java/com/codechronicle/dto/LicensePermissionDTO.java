package com.codechronicle.dto;

import com.codechronicle.model.LicensePermission;


public class LicensePermissionDTO extends BaseDTO {
	
	private String id;

	private String policyId;
	
	private String licenseId;
	
	private boolean approved;
	
	public LicensePermissionDTO() {
	}
	
	public LicensePermissionDTO(LicensePermission lp) {
		copyProperties(lp, this);
		if (lp.getLicense() != null) {
			licenseId = lp.getLicense().getId();
		}
		
		if (lp.getPolicy() != null) {
			policyId = lp.getPolicy().getId();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	
	
}

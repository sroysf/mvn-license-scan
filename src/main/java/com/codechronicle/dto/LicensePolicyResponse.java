package com.codechronicle.dto;

import java.util.List;

import com.codechronicle.model.LicensePolicy;

public class LicensePolicyResponse extends RestResponse {
	private List<LicensePolicy> policies;

	public List<LicensePolicy> getPolicies() {
		return policies;
	}

	public void setPolicies(List<LicensePolicy> policies) {
		this.policies = policies;
	}
}

package com.codechronicle.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * This class ties together a license policy with a particular permission for a given license. 
 * "Based on the MyCompanyOpenSourcePolicy, I am allowed to use software licensed as Apache 2"
 * 
 * @author SRoy
 *
 */
@Entity
public class LicensePermission {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	@ManyToOne
	private LicensePolicy policy;
	
	@ManyToOne
	private License license;
	
	private boolean approved = false;
	
	public LicensePermission() {
	}

	public LicensePermission(LicensePolicy policy, License license, boolean approved) {
		super();

		if (policy == null) {
			throw new AssertionError("Policy cannot be null");
		}
		
		if (license == null) {
			throw new AssertionError("License cannot be null");
		}
		
		this.policy = policy;
		this.license = license;
		this.approved = approved;
	}
	
	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return policy + " -> " + license + " -> | Approved = " + approved;
	}

	public LicensePolicy getPolicy() {
		return policy;
	}

	public void setPolicy(LicensePolicy policy) {
		this.policy = policy;
	}

	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}
}

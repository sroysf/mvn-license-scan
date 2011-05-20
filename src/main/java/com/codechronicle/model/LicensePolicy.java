package com.codechronicle.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * This entity defines a particular license policy. It groups together a set of license permissions. 
 * For example, "MyCompany has a license permission that says we can use Apache 2, and another one that 
 * says we must not use GPL-v3"
 * 
 * @author SRoy
 *
 */
@Entity
public class LicensePolicy {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	
	private String name;
	
	@OneToMany(mappedBy="policy")
	private List<LicensePermission> licensePermissions;
	
	public LicensePolicy(String name) {
		super();
		this.name = name;
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

	@Override
	public String toString() {
		return name;
	}
	
}

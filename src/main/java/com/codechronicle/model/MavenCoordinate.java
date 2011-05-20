package com.codechronicle.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.StringUtils;

/**
 * This class describes a specific maven coordinate. This should constitute the bulk of the data stored
 * in the license database. 
 * 
 * @author javacloud
 *
 */
@Entity
public class MavenCoordinate {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	
	private String groupId;
	private String artifactId;
	private String version;
	private String licenseInfoSource;

	@ManyToOne
	private License license;
	
	public MavenCoordinate () {
		
	}
	
	public MavenCoordinate(String groupId, String artifactId, String version) {
		super();

		if (StringUtils.isBlank(groupId)) {
			throw new AssertionError("Group Id cannot be blank");
		}
		
		if (StringUtils.isBlank(artifactId)) {
			throw new AssertionError("Artifact Id cannot be blank");
		}
		
		if (StringUtils.isBlank(version)) {
			throw new AssertionError("Version cannot be blank");
		}
		
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
	}
	
	public String getLicenseInfoSource() {
		return licenseInfoSource;
	}

	public void setLicenseInfoSource(String licenseInfoSource) {
		this.licenseInfoSource = licenseInfoSource;
	}

	public String getId() {
		return id;
	}

	public String getGroupId() {
		return groupId;
	}
	public String getArtifactId() {
		return artifactId;
	}
	public String getVersion() {
		return version;
	}
	
	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
	}

	@Override
	public String toString() {
		return getKey();
	}
	
	public String getKey() {
		return "[" + groupId + ":" + artifactId + ":" + version + "]";
	}
}

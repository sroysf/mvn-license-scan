package com.codechronicle.dto;

import com.codechronicle.model.MavenCoordinate;

public class MavenCoordinateDTO extends BaseDTO {

	private String id;
	
	private String groupId;
	private String artifactId;
	private String version;
	private String licenseInfoSource;
	
	private String licenseId;
	
	public MavenCoordinateDTO() {
	}
	
	public MavenCoordinateDTO(MavenCoordinate mvnCoord) {
		BeanMapperUtil.copyProperties(mvnCoord, this);
		if (mvnCoord.getLicense() != null) {
			this.licenseId = mvnCoord.getLicense().getId();
		}
	}
	
	public MavenCoordinate toModelObject() {
		// How will this be implemented/
		return null;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLicenseInfoSource() {
		return licenseInfoSource;
	}

	public void setLicenseInfoSource(String licenseInfoSource) {
		this.licenseInfoSource = licenseInfoSource;
	}

	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}
}

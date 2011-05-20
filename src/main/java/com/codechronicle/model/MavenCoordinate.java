package com.codechronicle.model;

import org.apache.commons.lang.StringUtils;

public class MavenCoordinate {
	
	String groupId;
	String artifactId;
	String version;
	
	
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
	
	public String getGroupId() {
		return groupId;
	}
	public String getArtifactId() {
		return artifactId;
	}
	public String getVersion() {
		return version;
	}
	
	@Override
	public String toString() {
		return groupId + ":" + artifactId + ":" + version;
	}
}

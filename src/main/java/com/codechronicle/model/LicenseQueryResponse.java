package com.codechronicle.model;

public class LicenseQueryResponse {
	
	private MavenCoordinate mavenCoordinate;
	private boolean approved;
	
	public MavenCoordinate getMavenCoordinate() {
		return mavenCoordinate;
	}
	public void setMavenCoordinate(MavenCoordinate mavenCoordinate) {
		this.mavenCoordinate = mavenCoordinate;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	@Override
	public String toString() {
		return mavenCoordinate + " (" + mavenCoordinate.getLicense() + ", " + mavenCoordinate.getLicenseInfoSource() + ") : " + approved;
	}
}

package com.codechronicle.dto;

import java.util.List;

import com.codechronicle.model.LicensePolicy;
import com.codechronicle.model.MavenCoordinate;

public class MavenCoordinateResponse extends RestResponse {
	private List<MavenCoordinate> artifacts;

	public List<MavenCoordinate> getArtifacts() {
		return artifacts;
	}

	public void setArtifacts(List<MavenCoordinate> artifacts) {
		this.artifacts = artifacts;
	}

	
}

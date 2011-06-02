package com.codechronicle.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codechronicle.dto.MavenCoordinateDTO;
import com.codechronicle.model.License;
import com.codechronicle.model.MavenCoordinate;
import com.codechronicle.service.LicenseService;

@Controller
@RequestMapping(value="/rest")
public class RestController {
	
	@Inject
	LicenseService licenseService;
	
	@RequestMapping(method=RequestMethod.GET, value="/artifact")
	public @ResponseBody Collection<MavenCoordinateDTO> getAllArtifacts() {
		List<MavenCoordinate> coords = licenseService.findAllMavenCoordinates();
		
		Collection<MavenCoordinateDTO> artifactList = new ArrayList<MavenCoordinateDTO>();
		for (MavenCoordinate mavenCoordinate : coords) {
			MavenCoordinateDTO dto = new MavenCoordinateDTO(mavenCoordinate);
			artifactList.add(dto);
		}
		
		return artifactList;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/license")
	public @ResponseBody Collection<License> getAllLicenses() {
		List<License> licenses = licenseService.findAllLicenses();
		
		// No DTO mapping needed, just serialize license directly.
		
		return licenses;
	}
}

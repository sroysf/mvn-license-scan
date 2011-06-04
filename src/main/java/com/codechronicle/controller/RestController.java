package com.codechronicle.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codechronicle.dto.BaseDTO;
import com.codechronicle.dto.BeanMapperUtil;
import com.codechronicle.dto.LicenseDTO;
import com.codechronicle.dto.LicensePermissionDTO;
import com.codechronicle.dto.LicensePolicyDTO;
import com.codechronicle.dto.MavenCoordinateDTO;
import com.codechronicle.dtomapper.BeanUtilsDTOMapper;
import com.codechronicle.dtomapper.DTOMapper;
import com.codechronicle.model.License;
import com.codechronicle.model.LicensePermission;
import com.codechronicle.model.LicensePolicy;
import com.codechronicle.model.MavenCoordinate;
import com.codechronicle.service.LicenseService;

@Controller
@RequestMapping(value="/rest")
public class RestController {
	
	private static final Logger logger = LoggerFactory.getLogger(EntityController.class);
	
	private DTOMapper dtoMapper = new BeanUtilsDTOMapper("com.codechronicle");
	
	@Inject
	LicenseService licenseService;

	//************************************
	// Various finder methods - GET
	
	/**
	 * Get all known artifacts.
	 * Example : /rest/artifact
	 */
	@RequestMapping(method=RequestMethod.GET, value="/artifact")
	public @ResponseBody Collection<MavenCoordinateDTO> getAllArtifacts() {
		
		logger.info("//GET /rest/artifact");
		
		List<MavenCoordinate> coords = licenseService.findAllMavenCoordinates();
		
		Collection<MavenCoordinateDTO> artifactList = new ArrayList<MavenCoordinateDTO>();
		for (MavenCoordinate mavenCoordinate : coords) {
			MavenCoordinateDTO dto = new MavenCoordinateDTO(mavenCoordinate);
			artifactList.add(dto);
		}
		
		return artifactList;
	}
	
	/**
	 * Get all known licenses
	 * Example : /rest/license
	 */
	@RequestMapping(method=RequestMethod.GET, value="/license")
	public @ResponseBody List<Map<String,Object>> getAllLicenses() {
		
		logger.info("//GET /rest/license");
		
		List<License> licenses = licenseService.findAllLicenses();
		
		List<Map<String,Object>> dtoResponse = dtoMapper.fromModelCollection(licenses);
		
		return dtoResponse;
	}
	
	/**
	 * Get all known policies. This only returns policy information, not relational data about permissions
	 * Example : /rest/policy
	 */
	@RequestMapping(method=RequestMethod.GET, value="/policy")
	public @ResponseBody List<LicensePolicyDTO> getAllLicensePolicies() {
		
		logger.info("//GET /rest/policy");
		
		List<LicensePolicy> policies = licenseService.findLicensePolicies();
		List<LicensePolicyDTO> dtos = BeanMapperUtil.createDTOList(LicensePolicyDTO.class, policies);
		
		return dtos;
	}
	
	/**
	 * Get all permissions that have been explicitly set for a given policy.
	 * Example : /rest/permissions/a0Dx00000008mJdEAI
	 * 
	 * @param policyId
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value="/permissions/{policyId}")
	public @ResponseBody List<LicensePermissionDTO> getAllPermissionsForPolicy(@PathVariable(value="policyId") String policyId) {
		
		logger.info("//GET /rest/permissions/" + policyId);
		
		List<LicensePermission> permissions = licenseService.findLicensePermissions(policyId);
		List<LicensePermissionDTO> dtoList = BeanMapperUtil.createDTOList(LicensePermissionDTO.class, permissions);
		
		return dtoList;
	}
	
	//************************************
	// Create Methods - POST
	
	/**
	 * Create new license
	 * Example : /rest/license
	 */
	@RequestMapping(method=RequestMethod.POST, value="/license")
	public @ResponseBody Map<String,Object> createLicense(@RequestBody Map<String,String> reqDTO) {
		
		logger.info("//POST /rest/license");
		logger.info("REQ received : " + reqDTO);
		
		LicenseDTO responseDTO = saveLicense(reqDTO);
		Map<String,Object> response = dtoMapper.fromModel(responseDTO);
		response.put("success", "true");
		
		return response;
	}
	
	/**
	 * Create a new artifact
	 * Example : /rest/license
	 */
	@RequestMapping(method=RequestMethod.POST, value="/artifact")
	public @ResponseBody MavenCoordinateDTO createArtifact(@RequestBody MavenCoordinateDTO reqDTO) {
		
		logger.info("//POST /rest/license");
		logger.info("REQ received : " + reqDTO);
		
		MavenCoordinateDTO responseDTO = saveArtifact(reqDTO);
		
		return responseDTO;
	}
	
	
	//************************************
	// Update Methods - PUT
	
	/**
	 * Update license
	 * Example : /rest/license
	 */
	@RequestMapping(method=RequestMethod.PUT, value="/license/{licenseId}")
	public @ResponseBody LicenseDTO updateLicense(@RequestBody LicenseDTO reqDTO) {
		
		logger.info("//PUT /rest/license");
		logger.info("REQ received : " + reqDTO);
		
		LicenseDTO responseDTO = saveLicense(reqDTO);
		
		return responseDTO;
	}

	//************************************
	// Common helper methods
	
	private LicenseDTO saveLicense(LicenseDTO reqDTO) {
		License license = new License(reqDTO);
		license = licenseService.addOrUpdateLicense(license);
		
		LicenseDTO responseDTO = new LicenseDTO(license);
		logger.info("RESPONSE : " + responseDTO);
		return responseDTO;
	}
	
	private MavenCoordinateDTO saveArtifact(MavenCoordinateDTO reqDTO) {
		MavenCoordinate mvnCoord = new MavenCoordinate(reqDTO);
		mvnCoord = licenseService.addOrUpdateMavenCoordinate(mvnCoord);
		
		MavenCoordinateDTO responseDTO = new MavenCoordinateDTO(mvnCoord);
		logger.info("RESPONSE : " + responseDTO);
		return responseDTO;
	}
}

package com.codechronicle.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codechronicle.dto.BeanMapperUtil;
import com.codechronicle.dto.LicensePermissionDTO;
import com.codechronicle.dto.LicensePolicyDTO;
import com.codechronicle.dto.MavenCoordinateDTO;
import com.codechronicle.dtomapper.DTOMapper;
import com.codechronicle.dtomapper.DataTransferObject;
import com.codechronicle.dtomapper.ModelEntityResolver;
import com.codechronicle.dtomapper.impl.BeanUtilsDTOMapper;
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
	
	@PersistenceContext
	EntityManager em;

	//************************************
	// Various finder methods - GET
	
	private <T> List<DataTransferObject> getAllEntities(Class<T> entityClass, boolean... includeCollections) {
		List<T> allEntities = licenseService.findAll(entityClass);
		
		boolean includeCollectionsParam = true;
		if (includeCollections.length > 0) {
			includeCollectionsParam = includeCollections[0];
		}
		List<DataTransferObject> entityDTOList = dtoMapper.fromModelCollection(allEntities, includeCollectionsParam);
		
		return entityDTOList;
	}
	
	/**
	 * Get all known artifacts.
	 * Example : /rest/artifact
	 */
	@RequestMapping(method=RequestMethod.GET, value="/artifact")
	public @ResponseBody List<DataTransferObject> getAllArtifacts() {
		
		logger.info("//GET /rest/artifact");
		return getAllEntities(MavenCoordinate.class);
	}
	
	/**
	 * Get all known licenses
	 * Example : /rest/license
	 */
	@RequestMapping(method=RequestMethod.GET, value="/license")
	public @ResponseBody List<DataTransferObject> getAllLicenses() {
		
		logger.info("//GET /rest/license");
		return getAllEntities(License.class);
	}
	
	/**
	 * Get all known policies. This only returns policy information, not relational data about permissions
	 * Example : /rest/policy
	 */
	@RequestMapping(method=RequestMethod.GET, value="/policy")
	public @ResponseBody List<DataTransferObject> getAllLicensePolicies() {
		
		logger.info("//GET /rest/policy");
		
		// Here, we turn off collections because we don't want to eagerly fetch all the permissions
		// unnecessarily just to list the set of policies.
		// If you do want the collections returned, make this method transactional (in order to preserve
		// EntityManager context and avoid lazy initialization exceptions) and then remove the second
		// parameter to getAllEntities - or set it to true.
		return getAllEntities(LicensePolicy.class, false);
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
	public @ResponseBody DataTransferObject createLicense(@RequestBody DataTransferObject reqDTO) {
		
		logger.info("//POST /rest/license");
		logger.info("REQ received : " + reqDTO);
		
		DataTransferObject responseDTO = saveLicense(reqDTO);
		responseDTO.put("success", "true");
		
		return responseDTO;
	}
	
	/**
	 * Create a new artifact
	 * Example : /rest/license
	 */
	@RequestMapping(method=RequestMethod.POST, value="/artifact")
	public @ResponseBody DataTransferObject createArtifact(@RequestBody DataTransferObject reqDTO) {
		
		logger.info("//POST /rest/artifact");
		logger.info("REQ received : " + reqDTO);
		
		DataTransferObject responseDTO = saveArtifact(reqDTO);
		responseDTO.put("success", "true");
		
		logger.info("Response artifact DTO : " + responseDTO);
		
		return responseDTO;
	}
	
	
	//************************************
	// Update Methods - PUT
	
	/**
	 * Update license
	 * Example : /rest/license/12345
	 */
	@RequestMapping(method=RequestMethod.PUT, value="/license/{licenseId}")
	public @ResponseBody DataTransferObject updateLicense(@RequestBody DataTransferObject reqDTO) {
		
		logger.info("//PUT /rest/license");
		logger.info("REQ received : " + reqDTO);
		
		DataTransferObject responseDTO = saveLicense(reqDTO);
		responseDTO.put("success", "true");
		
		return responseDTO;
	}
	
	/**
	 * Update artifact
	 * Example : /rest/artifact/12345
	 */
	@RequestMapping(method=RequestMethod.PUT, value="/artifact/{artifactId}")
	public @ResponseBody DataTransferObject updateArtifact(@RequestBody DataTransferObject reqDTO) {
		
		logger.info("//POST /rest/artifact");
		logger.info("REQ received : " + reqDTO);
		
		DataTransferObject responseDTO = saveArtifact(reqDTO);
		responseDTO.put("success", "true");
		
		logger.info("Response artifact DTO : " + responseDTO);
		
		return responseDTO;
	}

	//************************************
	// Common helper methods
	
	private DataTransferObject saveLicense(DataTransferObject reqDTO) {
		
		// TODO : Now we need to go from DTO -> Real Object.
		License license = dtoMapper.toModel(License.class, reqDTO);
		//license = licenseService.addOrUpdateLicense(license);
		license = licenseService.addOrUpdateEntity(license);
		
		DataTransferObject responseDTO = dtoMapper.fromModel(license, true);
		logger.info("RESPONSE : " + responseDTO);
		return responseDTO;
	}
	
	private DataTransferObject saveArtifact(DataTransferObject reqDTO) {
		
		MavenCoordinate mvnCoord = dtoMapper.toModel(MavenCoordinate.class, reqDTO, new ModelEntityResolver<License>() {
			@Override
			public License findEntity(Object id) {
				License foundLicense = licenseService.findById(License.class, id);
				return foundLicense;
			}
		});
		
		mvnCoord = licenseService.addOrUpdateEntity(mvnCoord);
		DataTransferObject responseDTO = dtoMapper.fromModel(mvnCoord, true); 
		
		return responseDTO;
	}
}

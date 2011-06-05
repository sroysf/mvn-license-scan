package com.codechronicle.dto;

import javax.inject.Inject;
import javax.jdo.annotations.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.codechronicle.controller.RestController;
import com.codechronicle.dtomapper.DTOMapper;
import com.codechronicle.dtomapper.DataTransferObject;
import com.codechronicle.dtomapper.ModelEntityResolver;
import com.codechronicle.dtomapper.impl.BeanUtilsDTOMapper;
import com.codechronicle.model.License;
import com.codechronicle.model.MavenCoordinate;
import com.codechronicle.service.LicenseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/app-context.xml"})
public class SerializationTest {
	
	@Inject
	private RestController rc;

	@Inject
	private LicenseService licenseService;
	
	@PersistenceContext
	EntityManager em;
	
	@Test
	public void testMapping() {
		DataTransferObject dto = new DataTransferObject();
		dto.put("id", "");
		dto.put("licenseId", "a0Ex0000000CefyEAC");
		dto.put("groupId", "com.codechronicle");
		dto.put("artifactId", "dto-mapper-util");
		dto.put("licenseInfoSource", "0.1-SNAPSHOT");
		dto.put("version", "Manual");
		
		DTOMapper dtoMapper = new BeanUtilsDTOMapper("com.codechronicle");
		MavenCoordinate mvnCoord = dtoMapper.toModel(MavenCoordinate.class, dto, new ModelEntityResolver<License>() {
			@Override
			public License findEntity(Object id) {
				License foundLicense = licenseService.findLicenseById((String)id);
				return foundLicense;
			}
		});
		
		System.out.println(mvnCoord + " -> " + mvnCoord.getId());
		mvnCoord = licenseService.addOrUpdateMavenCoordinate(mvnCoord);
		System.out.println(mvnCoord + " -> " + mvnCoord.getId());
	}
}

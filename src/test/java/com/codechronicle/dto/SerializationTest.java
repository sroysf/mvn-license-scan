package com.codechronicle.dto;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.codechronicle.controller.RestController;
import com.codechronicle.dtomapper.DTOMapper;
import com.codechronicle.dtomapper.DataTransferObject;
import com.codechronicle.dtomapper.impl.BeanUtilsDTOMapper;
import com.codechronicle.model.LicensePermission;
import com.codechronicle.model.LicensePolicy;
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
	public void testLicensePerms() {
		List<LicensePermission> perms = licenseService.findAll(LicensePermission.class);
		System.out.println(perms);
		
		DTOMapper mapper = new BeanUtilsDTOMapper("com.codechronicle");
		List<DataTransferObject> dtoList = mapper.fromModelCollection(perms, true);
		System.out.println(dtoList);
	}
	
	@Test
	public void testLicensePolicies() {
		List<LicensePolicy> lps = licenseService.findAll(LicensePolicy.class);
		System.out.println(lps);
		
		DTOMapper mapper = new BeanUtilsDTOMapper("com.codechronicle");
		List<DataTransferObject> dtoList = mapper.fromModelCollection(lps, true);
		System.out.println("DTO list : " + dtoList);
	}
}

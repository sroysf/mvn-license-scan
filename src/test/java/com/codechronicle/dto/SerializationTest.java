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
import com.codechronicle.model.License;
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
	public void testBeanMapping() {
		
		String id = "a0Ex0000000CeiPEAS";
		
		LicenseDTO dto = new LicenseDTO();
		dto.setId(id);
		dto.setName("TESTING WITHOUT MERGE");
		dto.setUrl("http://some.bogus.url");
		
		License detachedLicense = new License(dto);
		
		License mergedEntity = licenseService.addOrUpdateLicense(detachedLicense);
		
		Assert.assertEquals(id, mergedEntity.getId());
	}
	
	@Transactional
	public License saveDetachedEntity(License detachedEntity) {
		License mergedEntity = em.merge(detachedEntity);
		return mergedEntity;
	}
}

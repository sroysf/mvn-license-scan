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
		
		/*LicenseDTO dto = new LicenseDTO();
		String id = "a0Ex0000000CeiPEAS"; 
		dto.setId(id);
		dto.setName("MAKING A MODIFICATION");
		dto.setUrl("http://some.bogus.url");
		
		License license = new License(dto);
		license = licenseService.addOrUpdateLicense(license);
		
		System.out.println("Sent in ID  : " + id);
		System.out.println("Got back ID : " + license.getId());*/
		
		License existingLicense = em.find(License.class, "a0Ex0000000CeiPEAS");
		System.out.println("Here are the existing entity values: ");
		System.out.println(existingLicense.getId());
		System.out.println(existingLicense.getName());
		System.out.println(existingLicense.getUrl());
		
		License detachedEntity = new License("Some new name", "and a new url");
		detachedEntity.setId(existingLicense.getId());
		
		License mergedEntity = saveDetachedEntity(detachedEntity);
		System.out.println("Here are the merged entity values: ");
		System.out.println(mergedEntity.getId());
		System.out.println(mergedEntity.getName());
		System.out.println(mergedEntity.getUrl());
		
		String expectedId = existingLicense.getId();
		Assert.assertEquals(expectedId, mergedEntity.getId());
	}
	
	@Transactional
	public License saveDetachedEntity(License detachedEntity) {
		License mergedEntity = em.merge(detachedEntity);
		return mergedEntity;
	}
}

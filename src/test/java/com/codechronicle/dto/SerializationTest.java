package com.codechronicle.dto;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.codechronicle.controller.RestController;
import com.codechronicle.service.LicenseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/app-context.xml"})
public class SerializationTest {
	
	@Inject
	private RestController rc;

	@Inject
	private LicenseService licenseService;
	
	@Test
	public void testBeanMapping() {
		
		Object obj = rc.getAllPermissionsForPolicy("a0Dx00000008mJdEAI");
		System.out.println(obj);
	}
}

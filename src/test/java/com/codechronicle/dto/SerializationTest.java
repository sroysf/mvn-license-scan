package com.codechronicle.dto;

import java.util.Collection;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.codechronicle.controller.RestController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/app-context.xml"})
public class SerializationTest {
	
	@Inject
	private RestController rc;

	@Test
	public void testBeanMapping() {
		System.out.println("Testing bean mapping");
		Collection<MavenCoordinateDTO> results = rc.getAllArtifacts();
		System.out.println(results);
	}
}

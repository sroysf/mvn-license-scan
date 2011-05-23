package com.codechronicle.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.codechronicle.service.LicenseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/app-context.xml"})
public class ModelTest {

	@Resource
	LicenseService licenseService;
	
	@PersistenceContext
	EntityManager em;
	
	//@Before
	public void cleanDB() {
		em.createQuery("delete from MavenCoordinate m").executeUpdate();
		em.createQuery("delete from License l").executeUpdate();		
		em.createQuery("delete from LicensePolicy lp").executeUpdate();
		em.createQuery("delete from LicensePermission lp").executeUpdate();
	}
	
	// This is just an example of an integration test using Spring.
	//@Test
	public void crudTest() {
		
		// Licenses
		License apache2 = new License("Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0.html");
		apache2 = licenseService.addOrUpdateLicense(apache2);
		
		License lgpl3 = new License("GNU Lesser General Public License", "http://www.gnu.org/licenses/lgpl.txt");
		lgpl3 = licenseService.addOrUpdateLicense(lgpl3);
		
		
		// Maven Coordinates
		MavenCoordinate httpClient = new MavenCoordinate("org.apache.httpcomponents", "httpclient", "4.1.1");
		httpClient.setLicense(apache2);
		httpClient = licenseService.addOrUpdateMavenCoordinate(httpClient);
		
		MavenCoordinate spring = new MavenCoordinate("org.springframework", "spring-webmvc", "3.0.5.RELEASE");
		spring.setLicense(apache2);
		spring = licenseService.addOrUpdateMavenCoordinate(spring);
		
		MavenCoordinate checkstyle = new MavenCoordinate("checkstyle", "checkstyle", "5.0");
		checkstyle.setLicense(lgpl3);
		checkstyle = licenseService.addOrUpdateMavenCoordinate(checkstyle);
		
		// Policies
		LicensePolicy policy = new LicensePolicy("SFDCOpenSource");
		policy = licenseService.addOrUpdateLicensePolicy(policy);
		
		LicensePermission p1 = new LicensePermission(policy, apache2, true);
		LicensePermission p2 = new LicensePermission(policy, lgpl3, false);
		
		licenseService.addOrUpdateLicensePermission(p1);
		licenseService.addOrUpdateLicensePermission(p2);
		
	}
	
	@Test
	public void queryTest() {
		List<LicensePolicy> policies = em.createQuery("select l from LicensePolicy l where name = 'SFDCOpenSource'", LicensePolicy.class).getResultList();
		Assert.assertEquals(1, policies.size());
		
		LicensePolicy policy = policies.get(0);
		
		MavenCoordinate httpClient = new MavenCoordinate("org.apache.httpcomponents", "httpclient", "4.1.1");
		MavenCoordinate spring = new MavenCoordinate("org.springframework", "spring-webmvc", "3.0.5.RELEASE");
		MavenCoordinate cio = new MavenCoordinate("org.apache.commons", "commons-io", "1.3.2");
		MavenCoordinate beanshell = new MavenCoordinate("org.beanshell", "beanshell", "2.0b4");
		MavenCoordinate hibernateParent = new MavenCoordinate("org.hibernate", "hibernate-parent", "3.5.6-Final");
		
		List<MavenCoordinate> searchCoords = new ArrayList<MavenCoordinate>();
		searchCoords.add(httpClient);
		searchCoords.add(spring);
		searchCoords.add(cio);
		searchCoords.add(beanshell);
		searchCoords.add(hibernateParent);
		
		List<LicenseQueryResponse> response = licenseService.getAuthorizationInfo(searchCoords, policy);
		for (LicenseQueryResponse licenseQueryResponse : response) {
			System.out.println("****** " + licenseQueryResponse);
		}
	}
}

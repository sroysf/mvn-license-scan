package com.codechronicle.resolvers;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import com.codechronicle.model.License;
import com.codechronicle.model.MavenCoordinate;


public class MVNCentralResolverTest {

	@Test
	public void testExtractLicenseFound() throws Exception {
		
		String fileName = Thread.currentThread().getContextClassLoader().getResource("pom-with-license.xml").getFile();
		List<License> licenses = extractLicenses(fileName);
		
		Assert.assertNotNull(licenses);
		Assert.assertEquals(1, licenses.size());
		
		License license = licenses.get(0);
		Assert.assertEquals("Common Public License Version 1.0", license.getName());
		Assert.assertEquals("http://www.opensource.org/licenses/cpl1.0.txt", license.getUrl());
	}
	
	@Test
	public void testExtractMultiLicensesFound() throws Exception {
		
		String fileName = Thread.currentThread().getContextClassLoader().getResource("pom-with-multi-license.xml").getFile();
		List<License> licenses = extractLicenses(fileName);
		
		Assert.assertNotNull(licenses);
		Assert.assertEquals(2, licenses.size());
		
		License l1 = licenses.get(0);
		Assert.assertEquals("Common Public License Version 1.0", l1.getName());
		Assert.assertEquals("http://www.opensource.org/licenses/cpl1.0.txt", l1.getUrl());
		
		License l2 = licenses.get(1);
		Assert.assertEquals("GNU LESSER GENERAL PUBLIC LICENSE", l2.getName());
		Assert.assertEquals("http://www.gnu.org/licenses/lgpl.txt", l2.getUrl());
	}
	
	@Test
	public void testExtractNoLicensesFound() throws Exception {
		
		String fileName = Thread.currentThread().getContextClassLoader().getResource("pom-with-no-license.xml").getFile();
		List<License> licenses = extractLicenses(fileName);
		
		Assert.assertNull(licenses);
	}
	
	@Test
	public void testSearchURLGenerator() throws Exception {
		MavenCoordinate mvc = new MavenCoordinate("org.beanshell", "beanshell", "2.0b4");
		MVNCentralLicenseResolver resolver = new MVNCentralLicenseResolver();
		String url = resolver.generateUrl(mvc);
		
		Assert.assertEquals("http://search.maven.org/remotecontent?filepath=org/beanshell/beanshell/2.0b4/beanshell-2.0b4.pom", url);
	}

	private List<License> extractLicenses(String fileName)
			throws FileNotFoundException, Exception {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName));
		MVNCentralLicenseResolver resolver = new MVNCentralLicenseResolver();
		List<License> licenses = null;
		
		try {
			licenses = resolver.extractLicenses(bis);
		} finally {
			IOUtils.closeQuietly(bis);
		}
		return licenses;
	}
}

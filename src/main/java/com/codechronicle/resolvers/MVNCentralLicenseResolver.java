package com.codechronicle.resolvers;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codechronicle.model.License;
import com.codechronicle.model.MavenCoordinate;

/**
 * Use this class to extract license information directly from the POM files found on 
 * Maven Central's web interface. Example search URL : 
 * 
 * http://search.maven.org/remotecontent?filepath=org/datanucleus/datanucleus-jpa/2.1.8/datanucleus-jpa-2.1.8.pom
 * 
 * @author javacloud
 *
 */
public class MVNCentralLicenseResolver implements LicenseResolver {

	private HttpClient httpclient;
	private static Logger log = LoggerFactory.getLogger(MVNCentralLicenseResolver.class);
	
	public MVNCentralLicenseResolver() {
		super();
		this.httpclient = new DefaultHttpClient();
	}

	@Override
	public synchronized List<License> getLicenses(MavenCoordinate mvnCoordinate) {
		
		String searchUrl = generateUrl(mvnCoordinate);
		HttpGet httpget = new HttpGet(searchUrl);
		BufferedInputStream bis = null;
		
		try {
			HttpResponse response = httpclient.execute(httpget);
			bis = new BufferedInputStream(response.getEntity().getContent());
			
			List<License> licenses = extractLicenses(bis);
			if (licenses == null) {
				log.info("Unable to extract license for " + mvnCoordinate + " from " + searchUrl);
			} 
			
			return licenses;
			
		} catch (Throwable e) {
			log.warn("Error trying to extract license for : " + mvnCoordinate, e);
		} finally {
			IOUtils.closeQuietly(bis);
		}
		
		return null;
	}
	
	@Override
	public String getInformationSourceDescription() {
		return "Maven central, from project POM file";
	}
	
	protected List<License> extractLicenses(InputStream is) throws Exception {
		
		SAXBuilder sb = new SAXBuilder(false);
		
		Document doc = sb.build(is);
		
		Element root = doc.getRootElement();
		Namespace ns = root.getNamespace();
		
		Element licensesListElement = root.getChild("licenses", ns);
		
		if (licensesListElement == null) {
			return null;
		} else {
			List<License> extractedLicenses = new ArrayList<License>();
			
			List<Element> licenses = licensesListElement.getChildren();
			for (Element license : licenses) {
				String name = license.getChildText("name", ns);
				String url = license.getChildText("url", ns);
				
				License l = new License(name, url);
				extractedLicenses.add(l);
			}
			
			return extractedLicenses;
		}			
	}

	protected String generateUrl(MavenCoordinate mvnCoordinate) {
		
		String filePath = mvnCoordinate.getGroupId() + "/" + mvnCoordinate.getArtifactId();
		
		filePath = filePath.replaceAll("\\.", "/") + "/" + mvnCoordinate.getVersion() + "/"
			+ mvnCoordinate.getArtifactId() + "-" + mvnCoordinate.getVersion() + ".pom";
		
		return "http://search.maven.org/remotecontent?filepath=" + filePath;
	}
}

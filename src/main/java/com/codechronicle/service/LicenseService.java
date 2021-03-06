package com.codechronicle.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codechronicle.model.License;
import com.codechronicle.model.LicensePermission;
import com.codechronicle.model.LicensePolicy;
import com.codechronicle.model.LicenseQueryResponseItem;
import com.codechronicle.model.MavenCoordinate;
import com.codechronicle.resolvers.LicenseResolver;
import com.codechronicle.resolvers.MVNCentralLicenseResolver;

@Service
public class LicenseService extends EntityService {

	private static final Logger log = LoggerFactory.getLogger(LicenseService.class);
	
	@PersistenceContext
	EntityManager em;

	private List<LicenseResolver> licenseResolvers;
	
	public LicenseService() {
		// TODO : Pull this out so that it is injected by spring
		licenseResolvers = new ArrayList<LicenseResolver>();
		licenseResolvers.add(new MVNCentralLicenseResolver());
	}

	@Transactional
	public LicensePolicy findPolicy(String name) {
		List<LicensePolicy> results = em.createQuery("Select p from LicensePolicy p where name = :name",LicensePolicy.class).setParameter("name", name).getResultList();
		if (results.size() == 0) {
			return null;
		} else {
			return (results.get(0));
		}
	}
	
	public List<LicenseQueryResponseItem> getAuthorizationInfo(List<MavenCoordinate> mvnCoords, LicensePolicy policy) {
		
		// The scale is small enough such that the most efficient thing to do is to just 
		// load everything into memory and then process them.
		
		// Get all the known maven coordinates
		TypedQuery<MavenCoordinate> mcQuery = em.createQuery("Select m from MavenCoordinate m", MavenCoordinate.class);
		List<MavenCoordinate> knownMavenCoordinates = mcQuery.getResultList();
		Map<String,MavenCoordinate> knownCoordMap = new HashMap<String, MavenCoordinate>();
		for (MavenCoordinate knownMavenCoordinate : knownMavenCoordinates) {
			knownCoordMap.put(knownMavenCoordinate.getKey(), knownMavenCoordinate);
		}
		
		// For each of the given maven coordinates, try to find it in the known list, or resolve it
		List<MavenCoordinate> resolvedCoordList = new ArrayList<MavenCoordinate>();
		for (MavenCoordinate mavenCoordinate : mvnCoords) {
			MavenCoordinate resolvedCoord = knownCoordMap.get(mavenCoordinate.getKey());
			
			if (resolvedCoord == null) {
				// Try to resolve the unknown one using our list of resolvers
				log.info(mavenCoordinate + " not found in license cache. Using registered resolvers to search for license.");
				resolvedCoord = resolveLicense(mavenCoordinate);
			}
			
			resolvedCoordList.add(resolvedCoord);
		}
		
		// Get all the license perms for the given policy and put them in a map.
		TypedQuery<LicensePermission> lpQuery = em.createQuery("Select l from LicensePermission l where policy = :policy", LicensePermission.class);
		lpQuery.setParameter("policy", policy);
		List<LicensePermission> licensePermissions = lpQuery.getResultList();
		Map<String, LicensePermission> licensePermissionMap = new HashMap<String, LicensePermission>();
		for (LicensePermission licensePermission : licensePermissions) {
			licensePermissionMap.put(licensePermission.getLicense().getId(), licensePermission);
		}
		
		// Now lookup the permissions based on the matching license.
		
		List<LicenseQueryResponseItem> queryResponse = new ArrayList<LicenseQueryResponseItem>();
		for (MavenCoordinate mc : resolvedCoordList) {
			LicenseQueryResponseItem responseItem = new LicenseQueryResponseItem();
			
			License license = mc.getLicense();
			responseItem.setMavenCoordinate(mc);
			
			responseItem.setApproved(false); // default
			
			if (license != null) {
				LicensePermission lp = licensePermissionMap.get(license.getId());
				if (lp != null) {
					responseItem.setApproved(lp.isApproved());
				} 
			}
			
			queryResponse.add(responseItem);
		}
		
		return queryResponse;
		
	}

	private MavenCoordinate resolveLicense(MavenCoordinate mavenCoordinate) {
		
		List<License> licenses = null;
		
		for (LicenseResolver resolver : licenseResolvers) {
			licenses = resolver.getLicenses(mavenCoordinate);
			if (licenses != null) {
				// Find or add the licenses
				// TODO : Change this to accomodate multiple licenses for a project.
				License license = licenses.get(0);
				
				// If we already have this license, use that.
				License existingLicense = findLicenseByNameAndURL(license.getName(), license.getUrl());
				if (existingLicense != null) {
					log.info("License was found in database, updating maven coordinate reference : " + existingLicense);
					license = existingLicense;
				} else {
					log.info("Updating database with new license : " + license);
					addOrUpdateEntity(license);
				}
				
				// Map them into the maven coordinate
				mavenCoordinate.setLicense(license);
				mavenCoordinate.setLicenseInfoSource(resolver.getInformationSourceDescription());
				
				// Save the license, and the new maven coordinate
				addOrUpdateEntity(license);
				break;
			}
		}
		
		if (licenses == null) {
			log.info("Unable to determine license for : " + mavenCoordinate);
			mavenCoordinate.setLicense(null);
		}

		addOrUpdateEntity(mavenCoordinate);
		
		return mavenCoordinate;
	}
	
	// Transactional required to avoid lazy loading exception
	@Transactional
	public List<LicensePermission> findLicensePermissions(String policyId) {
		
		
		// This is how you might normally do it, but there currently seems to be a bug that results
		// in all the License and Artifact references being null.
		//LicensePolicy policy = findById(LicensePolicy.class, policyId);
		//List<LicensePermission> perms = policy.getLicensePermissions();
		
		// Here is the workaround:
		
		Query query = em.createQuery("SELECT lp FROM LicensePermission lp WHERE policy.id = ?1").setParameter(1, policyId);
		List<LicensePermission> perms = query.getResultList();
		
		return perms;
	}
	
	public License findLicenseByNameAndURL(String licenseName, String licenseURL) {
		
		log.info("Searching for existing license by name and URL : " + licenseName + " url = " + licenseURL);
		
		TypedQuery<License> query = em.createQuery("Select l from License l where name = :name AND url = :url", License.class);
		query.setParameter("name", licenseName.toUpperCase());
		query.setParameter("url", licenseURL.toUpperCase());
		
		List<License> licenses = query.getResultList();
		if (licenses.size() > 0) {
			log.info("Found 1, licenses result set size is : " + licenses.size());
			return licenses.get(0);
		} else {
			log.info("Unable to find matching license");
			return null;
		}
	}
}

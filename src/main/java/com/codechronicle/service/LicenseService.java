package com.codechronicle.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
public class LicenseService {

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
	public License addOrUpdateLicense(License license) {
		
		if (license.getId() != null) {
			em.merge(license);
		} else {
			em.persist(license);
		}
		
		log.info("Saved license : " + license);
		return license;
	}
	
	@Transactional
	public MavenCoordinate addOrUpdateMavenCoordinate(MavenCoordinate mvnCoord) {
		if (mvnCoord.getId() != null) {
			em.merge(mvnCoord);
		} else {
			em.persist(mvnCoord);
		}
		
		log.info("Saved MVN Coordinate : " + mvnCoord);
		return mvnCoord;
	}
	
	@Transactional
	public LicensePolicy addOrUpdateLicensePolicy(LicensePolicy policy) {
		if (policy.getId() != null) {
			em.merge(policy);
		} else {
			em.persist(policy);
		}
		
		log.info("Saved license policy : " + policy);
		return policy;
	}
	
	@Transactional
	public LicensePermission addOrUpdateLicensePermission(LicensePermission licensePermission) {
		
		if (licensePermission.getId() != null) {
			em.merge(licensePermission);
		} else {
			em.persist(licensePermission);
		}
		
		log.info("Saved license permission : " + licensePermission);
		return licensePermission;
	}
	
	@Transactional
	public List<LicensePermission> addOrUpdateLicensePermissionList(List<LicensePermission> licensePermissions) {
		
		if (licensePermissions == null) {
			return null;
		}
		
		for (LicensePermission licensePermission : licensePermissions) {
			if (licensePermission.getId() != null) {
				em.merge(licensePermission);
			} else {
				em.persist(licensePermission);
			}
		}
		
		log.info("Saved all license permissions : " + licensePermissions);
		return licensePermissions;
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
					addOrUpdateLicense(license);
				}
				
				// Map them into the maven coordinate
				mavenCoordinate.setLicense(license);
				mavenCoordinate.setLicenseInfoSource(resolver.getInformationSourceDescription());
				
				// Save the license, and the new maven coordinate
				addOrUpdateLicense(license);
				break;
			}
		}
		
		if (licenses == null) {
			log.info("Unable to determine license for : " + mavenCoordinate);
			mavenCoordinate.setLicense(null);
		}

		addOrUpdateMavenCoordinate(mavenCoordinate);
		
		return mavenCoordinate;
	}
	
	public License findLicenseById(String licenseId) {
		TypedQuery<License> query = em.createQuery("Select l from License l where id = :id", License.class);
		query.setParameter("id", licenseId);
		List<License> licenses = query.getResultList();
		if (licenses.size() > 0) {
			log.info("Found 1, licenses result set size is : " + licenses.size());
			return licenses.get(0);
		} else {
			log.info("Unable to find matching license");
			return null;
		}
	}
	
	public List<LicensePolicy> findLicensePolicies() {
		TypedQuery<LicensePolicy> query = em.createQuery("Select l from LicensePolicy l", LicensePolicy.class);
		List<LicensePolicy> policies = query.getResultList();
		return policies;
	}
	
	public List<LicensePermission> findLicensePermissions(String policyId) {
		TypedQuery<LicensePermission> query = em.createQuery("Select l from LicensePermission l where license.id = ?1", LicensePermission.class);
		query.setParameter(1, policyId);
		List<LicensePermission> permissions = query.getResultList();
		return permissions;
	}
	
	public List<MavenCoordinate> findAllMavenCoordinates() {
		TypedQuery<MavenCoordinate> query = em.createQuery("Select l from MavenCoordinate l", MavenCoordinate.class);
		List<MavenCoordinate> artifacts = query.getResultList();
		return artifacts;
	}
	
	public List<License> findAllLicenses() {
		TypedQuery<License> query = em.createQuery("Select l from License l", License.class);
		List<License> licenses = query.getResultList();
		return licenses;
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

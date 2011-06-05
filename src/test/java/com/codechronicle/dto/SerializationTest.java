package com.codechronicle.dto;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.codechronicle.controller.RestController;
import com.codechronicle.dtomapper.DTOMapper;
import com.codechronicle.dtomapper.DataTransferObject;
import com.codechronicle.dtomapper.impl.BeanUtilsDTOMapper;
import com.codechronicle.model.License;
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
	@Transactional
	public void manyToOneLazyLoadingTest() {

		String policyId = "a0Dx00000008mJdEAI";
		
		LicensePolicy policy = em.find(LicensePolicy.class, policyId);
		List<LicensePermission> perms = policy.getLicensePermissions();

		List<String> permIds = new ArrayList<String>();
		
		// Notice that in this case, the related ManyToOne referenced objects are null, even though we are still in the same transaction
		// Also note that setting fetch type to EAGER on the @ManyToOne relationship has no effect. The objects are still null.
		System.out.println("Directly accessing the collection results in null ManyToOne referenced objects, even though we're still in a transaction.");
		for (LicensePermission licensePermission : perms) {
			System.out.println(licensePermission.getId() + " -> " + licensePermission.getLicense() + " -> " + licensePermission.getPolicy());
			permIds.add(licensePermission.getId());
		}
		
		// Now try loading the same LicensePermission objects directly. Note that this time the @ManyToOne properties are populated just fine.
		// I would have expected this in the situation above as well.
		Query query = em.createQuery("SELECT lp FROM LicensePermission lp WHERE lp.id IN ?1");
		query.setParameter(1, permIds);
		List<LicensePermission> directLoadedPerms = query.getResultList();
		for (LicensePermission dlp : directLoadedPerms) {
			System.out.println(dlp.getId() + " -> " + dlp.getLicense() + " -> " + dlp.getPolicy());
		}
	}
}

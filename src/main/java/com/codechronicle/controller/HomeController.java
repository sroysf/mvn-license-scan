package com.codechronicle.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codechronicle.dto.BeanMapperUtil;
import com.codechronicle.model.License;
import com.codechronicle.model.LicensePolicy;
import com.codechronicle.model.LicenseQueryResponse;
import com.codechronicle.model.LicenseQueryResponseItem;
import com.codechronicle.model.MavenCoordinate;
import com.codechronicle.service.LicenseService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	@Inject
	LicenseService licenseService;

	
	@RequestMapping(method=RequestMethod.GET, value="/jsonTest")
	public @ResponseBody Map<String,String> jsonTest() {
		Map<String,String> jsonMap = new HashMap<String, String>();
		
		License license  = licenseService.findById(License.class, "a0Ex0000000Cefy");
		log.info("Found license : " + license);
		
		MavenCoordinate mvn = new MavenCoordinate("com.saptarshi", "dtomapper", "1.0-SNAPSHOT");
		mvn.setLicense(license);
		
		Map desc = null;
		
		try {
			desc = BeanUtils.describe(mvn);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return desc;
	}
	
	
	/**
	 * 
	 * 
	 * @param policy
	 * @param mvncoords
	 * @param model
	 * @return
	 * 
	 * Sample URI : /auths/SFDCOpenSource/org.beanshell:beanshell:2.0b4,org.hibernate:hibernate-parent:3.5.6-Final,org.apache.commons:commons-io:1.3.2
	 */
	@RequestMapping(method=RequestMethod.POST, value="/licenseQuery")
	public @ResponseBody LicenseQueryResponse getLicenseAuthorizations (@RequestParam(value="policy") String policy, 
			@RequestParam(value="mvncoords") String mvncoords, Model model) {
		
		LicensePolicy lp = licenseService.findPolicy(policy);
		LicenseQueryResponse lqr = new LicenseQueryResponse();
		if (lp == null) {
			lqr.setMessage("License policy " + "[" + policy + "] not found.");
		} else {
			
			List<MavenCoordinate> searchCoords = new ArrayList<MavenCoordinate>();
			String[] splitCoords = mvncoords.split(",");
			for (String sp : splitCoords) {
				String[] varsplit = sp.split(":");
				MavenCoordinate mc = new MavenCoordinate(varsplit[0], varsplit[1], varsplit[2]);
				searchCoords.add(mc);
			}
			
			System.out.println(searchCoords);
			
			List<LicenseQueryResponseItem> responseItems = licenseService.getAuthorizationInfo(searchCoords, lp);
			//List<LicenseQueryResponseItem> responseItems = new ArrayList<LicenseQueryResponseItem>();
			lqr.setResponseItems(responseItems);
			lqr.setMessage("OK");
		}
		
		return lqr;
	}

}


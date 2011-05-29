package com.codechronicle.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.codechronicle.dto.LicensePolicyResponse;
import com.codechronicle.model.License;
import com.codechronicle.model.LicensePermission;
import com.codechronicle.model.LicensePolicy;
import com.codechronicle.model.LicenseQueryResponse;
import com.codechronicle.model.LicenseQueryResponseItem;
import com.codechronicle.model.MavenCoordinate;
import com.codechronicle.service.EntityService;
import com.codechronicle.service.LicenseService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	@Inject
	LicenseService licenseService;
	
	@RequestMapping(method=RequestMethod.GET, value="/license_policy")
	public @ResponseBody LicensePolicyResponse getAllLicensePolicies() {
		LicensePolicyResponse response = new LicensePolicyResponse();
		response.setSuccess(true);
		response.setPolicies(licenseService.findLicensePolicies());
		
		return response;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/createPerm")
	public ModelAndView createLicensePermission(ModelAndView mv, HttpServletRequest request) {
		mv.setViewName("createLicensePerm");
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/licenseQuery")
	public ModelAndView queryLicensePermissions(ModelAndView mv, HttpServletRequest request) {
		mv.setViewName("queryLicensePerms");
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/licensePermission")
	public @ResponseBody LicensePermission createLicensePermission(@RequestParam(value="policy") String policy,
			@RequestParam(value="licenseId") String licenseId,
			@RequestParam(value="approved") String approved) {
		System.out.println("Checking post of coordinates for license policy : " + policy);
		System.out.println(licenseId);
		
		LicensePolicy licensePolicy = licenseService.findPolicy(policy);
		if (licensePolicy == null) {
			log.warn("Unable to find license policy : " + policy);
			return null;
		}
		
		License license = licenseService.findLicenseById(licenseId);
		if (license == null) {
			log.warn("Unable to find license with id : " + licenseId);
			return null;
		}
		
		LicensePermission lp = new LicensePermission(licensePolicy, license, Boolean.valueOf(approved));
		licenseService.addOrUpdateLicensePermission(lp);
		return lp;
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


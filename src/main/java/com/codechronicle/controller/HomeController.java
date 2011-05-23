package com.codechronicle.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codechronicle.model.License;
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

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Inject
	LicenseService licenseService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home() {
	    	logger.info("requesting home");
		return "home";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{policy}/{mvncoords}")
	public @ResponseBody LicenseQueryResponse getLicenseAuthorizations (@PathVariable(value="policy") String policy, 
			@PathVariable(value="mvncoords") String mvncoords, Model model) {
		
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
			
			List<LicenseQueryResponseItem> responseItems = licenseService.getAuthorizationInfo(searchCoords, lp);
			lqr.setResponseItems(responseItems);
			lqr.setMessage("OK");
		}
		
		return lqr;
	}

}


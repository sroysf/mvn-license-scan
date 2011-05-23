package com.codechronicle.controller;

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
import com.codechronicle.service.EntityService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Inject
	EntityService entityService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home() {
	    	logger.info("requesting home");
		return "home";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{policy}/{mvncoord}")
	public @ResponseBody License getBook (@PathVariable(value="policy") String policy, @PathVariable(value="mvncoord") String mvncoord, Model model) {
		License l = new License(policy, mvncoord);
		return l;
	}

}


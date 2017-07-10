package com.RuleEngine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.RuleEngine.service.PreloadService;
import com.RuleEngine.service.RuleService;
import com.RuleEngine.service.Sm_linksService;
import com.RuleEngine.service.Sm_nodesService;
import com.RuleEngine.service.ValidateService;

@Controller
@RequestMapping(value="/cleanup")
public class cleanupController {
	
	@Autowired
	private PreloadService preloadService;
	
	@Autowired
	private RuleService ruleService;
	
	@Autowired 
	private Sm_linksService sm_linksService;
	
	@Autowired
	private Sm_nodesService sm_nodesService;
	
	@Autowired
	private ValidateService validateService;

	@RequestMapping(value="/Cleanup")
	public ModelAndView Cleanup() {
		preloadService.cleanup();
		ruleService.cleanup();
		sm_linksService.cleanup();
		sm_nodesService.cleanup();
		validateService.cleanup();
		
		ModelAndView modelAndView = new ModelAndView("Cleanup");
		return modelAndView;
	}
}

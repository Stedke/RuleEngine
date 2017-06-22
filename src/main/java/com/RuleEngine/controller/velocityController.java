package com.RuleEngine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.RuleEngine.service.RuleService;
import com.RuleEngine.service.ValidateService;

@Controller
@RequestMapping(value="/velocity")
public class velocityController {
	
	@Autowired
	private RuleService RuleService;
	@Autowired
	private ValidateService ValidateService;
	
	@RequestMapping(value="/Velocity")
	public ModelAndView Velocity() {
		ModelAndView modelAndView = new ModelAndView("Velocity");
		
		RuleService.transferData(ValidateService.transferData());
		RuleService.fireRules();
		
		//show velocity
		
		return modelAndView;
	}
}
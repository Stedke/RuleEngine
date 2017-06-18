package com.RuleEngine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.RuleEngine.service.RuleService;
import com.RuleEngine.service.PreloadService;

@Controller
@RequestMapping(value="/velocity")
public class velocityController {
	
	@Autowired
	private RuleService RuleService;
	@Autowired
	private PreloadService PreloadService;
	
	@RequestMapping(value="/Velocity")
	public ModelAndView Velocity() {
		ModelAndView modelAndView = new ModelAndView("Velocity");
		
		RuleService.transferData(PreloadService.transferData());
		
		return modelAndView;
	}
}

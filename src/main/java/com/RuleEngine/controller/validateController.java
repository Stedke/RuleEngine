package com.RuleEngine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.RuleEngine.service.ValidateService;
import com.RuleEngine.service.PreloadService;

@Controller
@RequestMapping(value="/validate")
public class validateController {
	
	@Autowired
	private ValidateService ValidateService;
	@Autowired
	private PreloadService PreloadService;
	
	@RequestMapping(value="/Validate")
	public ModelAndView Validate() {
		ModelAndView modelAndView = new ModelAndView("Validate");
		
		ValidateService.setData(PreloadService.transferData());
		ValidateService.fireValidateRules();
		
		modelAndView.addObject("sm_dictionary", ValidateService.getMissingRuleData().getSm_dictionary());
		
		return modelAndView;
	}
}

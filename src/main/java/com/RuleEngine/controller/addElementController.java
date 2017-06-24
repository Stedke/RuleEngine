package com.RuleEngine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.RuleEngine.service.ValidateService;

@Controller
@RequestMapping(value="/addElement")
public class addElementController {
	
	@Autowired
	ValidateService ValidateService;
	
    public ModelAndView addElement() {
    	ModelAndView modelAndView = new ModelAndView("addElement");
    	modelAndView.addObject("missing_dictionary", ValidateService.getMissingRuleData().getSm_dictionary());
        return modelAndView;
    }
}

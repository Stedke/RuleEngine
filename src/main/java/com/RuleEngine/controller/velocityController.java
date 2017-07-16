package com.RuleEngine.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.RuleEngine.model.sm_linkAreasData;
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
		
		Boolean isSuccessful = new Boolean(false);
		List<sm_linkAreasData> sm_linkAreas = new ArrayList<sm_linkAreasData>();
		
		isSuccessful=ValidateService.checkIfDataOk();
		
		if(isSuccessful){
			RuleService.transferData(ValidateService.transferData());
			RuleService.updateAllNodesImpacts();
			RuleService.divideSm_link();
			RuleService.fireRules();
			
			sm_linkAreas = RuleService.getSm_linkAreas();
		}

		modelAndView.addObject("sm_linkAreas", sm_linkAreas);
		modelAndView.addObject("isSuccessful", isSuccessful);
		
		return modelAndView;
	}
}

package com.RuleEngine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.RuleEngine.model.sm_nodes;
import com.RuleEngine.service.Sm_nodesService;
import com.RuleEngine.service.TestRuleService;

@Controller
@RequestMapping(value="/sm_nodes")
public class sm_nodesController {
	
	@Autowired
	private Sm_nodesService Sm_nodesService;
	@Autowired
	private TestRuleService TestRuleService;

	@RequestMapping(value="/list")
	public ModelAndView listOfsm_nodes() {
		ModelAndView modelAndView = new ModelAndView("list-of-sm_nodes");
		
		List<sm_nodes> sm_nodes = Sm_nodesService.getSm_nodes();
		TestRuleService.setSm_nodes(sm_nodes);
		TestRuleService.testDrools();
		modelAndView.addObject("sm_nodes", sm_nodes);
		return modelAndView;
	}
}

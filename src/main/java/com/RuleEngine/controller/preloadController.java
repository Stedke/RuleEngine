package com.RuleEngine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.RuleEngine.model.sm_dictionary;
import com.RuleEngine.model.sm_link_properties;
import com.RuleEngine.model.sm_segment_properties;
import com.RuleEngine.service.PreloadService;
import com.RuleEngine.service.Sm_linksService;

@Controller
@RequestMapping(value="/preload")
public class preloadController {

	@Autowired
	private PreloadService preloadService;
	
	@Autowired
	private Sm_linksService sm_linksService;
	
	@RequestMapping(value="/Preload")
	public ModelAndView Validate() {
		ModelAndView modelAndView = new ModelAndView("Preload");
		
		preloadService.preloadData(sm_linksService.getChosenSm_linkId().longValue());
		List<sm_link_properties> sm_link_properties = preloadService.getSm_link_properties();
		List<sm_segment_properties> sm_segment_properties = preloadService.getSm_segment_properties();
		List<sm_dictionary> sm_dictionary = preloadService.getSm_dictionary();
		modelAndView.addObject("sm_link_properties", sm_link_properties);
		modelAndView.addObject("sm_segment_properties", sm_segment_properties);
		modelAndView.addObject("sm_dictionary", sm_dictionary);
		return modelAndView;
	}
}

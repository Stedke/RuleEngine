package com.RuleEngine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.RuleEngine.model.linksArea;

@Controller
@RequestMapping(value="/sm_links")
public class listOfLinksController {
	
	   @RequestMapping(value = "/listOfLinks", method = RequestMethod.GET)
	   public ModelAndView listOfLinks() {
	      return new ModelAndView("listOfLinks", "command", new linksArea());
	   }
	   @RequestMapping(value = "/addlinksArea", method = RequestMethod.POST)
	      public String addlinksArea(@ModelAttribute("SpringWeb")linksArea links_Area,
	          ModelMap model) {
	      model.addAttribute("latitude", links_Area.getLatitude());
	      model.addAttribute("longitude", links_Area.getLongitude());
	      
	      return "result";
	   }
}

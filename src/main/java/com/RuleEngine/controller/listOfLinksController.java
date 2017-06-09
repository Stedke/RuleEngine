package com.RuleEngine.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.RuleEngine.model.linksArea;
import com.RuleEngine.service.Sm_linksService;

@Controller
@RequestMapping(value="/sm_links")
public class listOfLinksController {
	
	@Autowired
	private Sm_linksService Sm_linksService;
	
    @RequestMapping(value = "/listOfLinks", method = RequestMethod.GET)
    public ModelAndView listOfLinks() {
       return new ModelAndView("listOfLinks", "command", new linksArea());
    }
    @RequestMapping(value = "/addlinksArea", method = RequestMethod.POST)
       public String addlinksArea(@ModelAttribute("links_Area")linksArea links_Area,
           ModelMap model) {
       model.addAttribute("leftUpper_latitude", links_Area.getLeftUpper_latitude());
       model.addAttribute("leftUpper_longitude", links_Area.getLeftUpper_longitude());
       model.addAttribute("leftLower_latitude", links_Area.getLeftLower_latitude());
       model.addAttribute("leftLower_longitude", links_Area.getLeftLower_longitude());
       model.addAttribute("rightUpper_latitude", links_Area.getRightUpper_latitude());
       model.addAttribute("rightUpper_longitude", links_Area.getRightUpper_longitude());
       model.addAttribute("rightLower_latitude", links_Area.getRightLower_latitude());
       model.addAttribute("rightLower_longitude", links_Area.getRightLower_longitude());
	   
       ArrayList<String> linksArea = new ArrayList<String>();
       linksArea.add(links_Area.getLeftUpper_latitude());
       linksArea.add(links_Area.getLeftUpper_longitude());
       linksArea.add(links_Area.getLeftLower_latitude());
       linksArea.add(links_Area.getLeftLower_longitude());
       linksArea.add(links_Area.getRightUpper_latitude());
   	   linksArea.add(links_Area.getRightUpper_longitude());
       linksArea.add(links_Area.getRightLower_latitude());
       linksArea.add(links_Area.getRightLower_longitude());
       
       Sm_linksService.getSm_links(linksArea);
       
       return "result";
    }
}

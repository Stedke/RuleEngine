package com.RuleEngine.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.RuleEngine.model.chosenSm_link;
import com.RuleEngine.model.sm_links;
import com.RuleEngine.service.Sm_linksService;

@Controller
@RequestMapping(value="/chooseLink")
public class chooseLinkController {
	
	@Autowired
	private Sm_linksService Sm_linksService;
	
    @RequestMapping(value = "/viewLinks", method = RequestMethod.GET)
    public ModelAndView viewLinks() {
    	chosenSm_link chosenSmLink = new chosenSm_link();
    	chosenSmLink.setAllSm_Links(Sm_linksService.getChosenSm_Links());
        return new ModelAndView("viewLinks", "command", chosenSmLink);
    }
    @RequestMapping(value = "/chooseSm_Link", method = RequestMethod.POST)
       public String chooseSm_Link(@ModelAttribute("chosenSmLink")chosenSm_link chosenSmlink,
           ModelMap model) {
       model.addAttribute("index", chosenSmlink.getChosenSm_Link());
       model.addAttribute("links", chosenSmlink.getAllSm_Links());
       
       chosenSmlink.setAllSm_Links(Sm_linksService.getChosenSm_Links());
       
       return "result2";
    }
	
}

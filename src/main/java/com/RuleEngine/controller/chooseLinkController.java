package com.RuleEngine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.RuleEngine.model.chosenSm_link;
import com.RuleEngine.service.Sm_linksService;

@Controller
@RequestMapping(value="/chooseLink")
public class chooseLinkController {
	
	@Autowired
	private Sm_linksService Sm_linksService;
	
    @RequestMapping(value = "/viewLinks", method = RequestMethod.GET)
    public ModelAndView viewLinks() { 	
    	chosenSm_link chosenSmLink = new chosenSm_link();
    	ModelAndView modelAndView = new ModelAndView("viewLinks", "command", chosenSmLink);
    	modelAndView.addObject("sm_links", Sm_linksService.getChosenSm_Links());
        return modelAndView;
    }
    @RequestMapping(value = "/chooseSm_Link", method = RequestMethod.POST)
       public String chooseSm_Link(@ModelAttribute("chosenSmLink")chosenSm_link chosenSmlink,
           ModelMap model) {
       model.addAttribute("chosenSm_LinkId", chosenSmlink.getChosenSm_LinkId());
       
       Sm_linksService.setChosenSm_linkId(chosenSmlink.getChosenSm_LinkId());
       
       return "result2";
    }
	
}
